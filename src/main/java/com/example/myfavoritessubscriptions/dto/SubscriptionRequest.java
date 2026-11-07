package com.example.myfavoritessubscriptions.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Запрос на добавление или удаление подписки пользователя.
 *
 * <p>Существующая подписка определяется по {@code subscriptionName},
 * отсутствующая — создаётся при добавлении.</p>
 */
public record SubscriptionRequest(
        @NotBlank(message = "Не указанно имя подписки")
        @Size(min = 2, max = 50, message = "Имя подписки должно быть от 2 до 50 символов")
        String subscriptionName
) {
}
