package com.example.myfavoritessubscriptions.service;

import com.example.myfavoritessubscriptions.entity.Subscription;

import java.util.List;

public interface SubscriptionService {
    List<Subscription> getTopPopularSubscriptions(int limit);
}
