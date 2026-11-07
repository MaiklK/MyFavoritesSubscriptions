package com.example.myfavoritessubscriptions.service;

import com.example.myfavoritessubscriptions.dto.SubscriptionResponse;

import java.util.List;

public interface SubscriptionService {
    List<SubscriptionResponse> getTopPopularSubscriptions(int limit);
}
