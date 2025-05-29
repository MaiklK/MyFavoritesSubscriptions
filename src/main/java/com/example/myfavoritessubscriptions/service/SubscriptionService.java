package com.example.myfavoritessubscriptions.service;

import com.example.myfavoritessubscriptions.entity.Subscription;
import com.example.myfavoritessubscriptions.repository.SubscriptionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SubscriptionService {
    SubscriptionRepository subscriptionRepository;

    public List<Subscription> getTopPopularSubscriptions(int limit) {
        List<Subscription> subscriptions = subscriptionRepository.findTopPopularSubscriptions();
        return subscriptions.size() > 3 ? subscriptions.subList(0, limit) : subscriptions;
    }
}
