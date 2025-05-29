package com.example.myfavoritessubscriptions.repository;

import com.example.myfavoritessubscriptions.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query("SELECT sub FROM Subscription sub JOIN sub.users users GROUP BY sub.id ORDER BY COUNT(users) DESC")
    List<Subscription> findTopPopularSubscriptions();
}