#!/bin/sh

# Проверка переменных окружения
if [ -z "${ELASTIC_USERNAME}" ] || [ -z "${ELASTIC_PASSWORD}" ]; then
  echo "Ошибка: Не заданы переменные ELASTIC_USERNAME и/или ELASTIC_PASSWORD"
  exit 1
fi

ES_HOST="elasticsearch"
ES_PORT="9200"
ES_URL="http://${ES_HOST}:${ES_PORT}/_cluster/health"

# Ожидание green-статуса кластера (до 2 минут)
i=0
while true; do
  STATUS=$(curl -u "${ELASTIC_USERNAME}:${ELASTIC_PASSWORD}" -s "${ES_URL}" | grep -o '"status":"[a-z]*"' | cut -d':' -f2 | tr -d '"')
  if [ "$STATUS" = "green" ]; then
    break
  fi
  i=$((i+1))
  if [ $i -gt 60 ]; then
    echo "Ошибка: Кластер не стал green за 2 минуты"
    exit 2
  fi
  sleep 2
done

# Создание пользователя Kibana
curl -u "${ELASTIC_USERNAME}:${ELASTIC_PASSWORD}" -X POST "http://${ES_HOST}:${ES_PORT}/_security/user/kibana_user" \
  -H "Content-Type: application/json" \
  -d '{
    "password" : "kibana_password",
    "roles" : [ "kibana_user" ],
    "full_name" : "Kibana User"
  }'