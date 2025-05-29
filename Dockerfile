FROM openjdk:17
COPY target/MyFavoritesSubscriptions-*.jar MyFavoritesSubscriptions.jar
ENTRYPOINT ["java", "-jar", "/MyFavoritesSubscriptions.jar"]