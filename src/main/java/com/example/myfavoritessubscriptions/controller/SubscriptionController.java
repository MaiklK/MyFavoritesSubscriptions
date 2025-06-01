package com.example.myfavoritessubscriptions.controller;

import com.example.myfavoritessubscriptions.entity.Subscription;
import com.example.myfavoritessubscriptions.service.Impl.SubscriptionServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/subscriptions")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SubscriptionController {
    SubscriptionServiceImpl subscriptionServiceImpl;

    @GetMapping("top/{limit})")
    public ResponseEntity<List<Subscription>> addSubscription(@PathVariable int limit) {
        var subscriptions = subscriptionServiceImpl.getTopPopularSubscriptions(limit);
        return ResponseEntity.ok(subscriptions);
    }
}
