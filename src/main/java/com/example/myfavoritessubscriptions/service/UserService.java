package com.example.myfavoritessubscriptions.service;

import com.example.myfavoritessubscriptions.dto.SubscriptionRequest;
import com.example.myfavoritessubscriptions.dto.SubscriptionResponse;
import com.example.myfavoritessubscriptions.dto.UserCreateRequest;
import com.example.myfavoritessubscriptions.dto.UserResponse;
import com.example.myfavoritessubscriptions.dto.UserUpdateRequest;

import java.util.Set;

public interface UserService {
    UserResponse createUser(UserCreateRequest request);

    UserResponse getUser(Long userId);

    void updateUser(Long userId, UserUpdateRequest request);

    void deleteUser(Long userId);

    void addSubscriptionToUser(Long userId, SubscriptionRequest request);

    void removeSubscriptionFromUser(Long userId, SubscriptionRequest request);

    Set<SubscriptionResponse> getSubscriptionsForUser(Long userId);
}
