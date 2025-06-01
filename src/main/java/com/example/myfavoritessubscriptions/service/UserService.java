package com.example.myfavoritessubscriptions.service;

import com.example.myfavoritessubscriptions.entity.Subscription;
import com.example.myfavoritessubscriptions.entity.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    User createUser(User user);

    User getUser(Long userId);

    void updateUser(Long userId, User user);

    void deleteUser(Long userId);

    void addSubscriptionToUser(Long userId, Subscription subscription);

    void removeSubscriptionFromUser(Long userId, Subscription subscription);

    Set<Subscription> getSubscriptionsForUser(Long userId);
}
