package com.example.myfavoritessubscriptions.dto;

import java.util.Set;

/**
 * Ответ с данными пользователя и его подписками.
 */
public record UserResponse(
        Long id,
        String name,
        String email,
        Set<SubscriptionResponse> subscriptions
) {
}
