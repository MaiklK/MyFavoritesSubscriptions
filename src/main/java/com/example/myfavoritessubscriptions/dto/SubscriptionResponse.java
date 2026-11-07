package com.example.myfavoritessubscriptions.dto;

/**
 * Ответ с данными подписки.
 */
public record SubscriptionResponse(
        Long id,
        String subscriptionName
) {
}
