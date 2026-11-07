package com.example.myfavoritessubscriptions.repository;

import com.example.myfavoritessubscriptions.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findBySubscriptionNameIgnoreCase(String subscriptionName);

    @Query("""
            SELECT sub FROM Subscription sub
            WHERE sub.id IN (
                SELECT s.id FROM Subscription s LEFT JOIN s.users u
                GROUP BY s.id, s.subscriptionName
                ORDER BY COUNT(u) DESC, s.subscriptionName ASC
            )
            """)
    List<Subscription> findTopPopularSubscriptions();
}
