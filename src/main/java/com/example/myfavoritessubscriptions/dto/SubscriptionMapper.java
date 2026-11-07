package com.example.myfavoritessubscriptions.dto;

import com.example.myfavoritessubscriptions.entity.Subscription;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Преобразование сущностей в DTO ответов.
 */
public final class SubscriptionMapper {

    private static final Comparator<SubscriptionResponse> BY_NAME =
            Comparator.comparing(SubscriptionResponse::subscriptionName, String.CASE_INSENSITIVE_ORDER);

    private SubscriptionMapper() {
    }

    public static SubscriptionResponse toResponse(Subscription subscription) {
        return new SubscriptionResponse(subscription.getId(), subscription.getSubscriptionName());
    }

    public static List<SubscriptionResponse> toSortedResponses(List<Subscription> subscriptions) {
        return subscriptions.stream()
                .map(SubscriptionMapper::toResponse)
                .sorted(BY_NAME)
                .toList();
    }

    /**
     * Подписки в детерминированном порядке, чтобы ответ API был стабильным между вызовами.
     */
    public static Set<SubscriptionResponse> toSortedResponseSet(Set<Subscription> subscriptions) {
        return subscriptions.stream()
                .map(SubscriptionMapper::toResponse)
                .sorted(BY_NAME)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
