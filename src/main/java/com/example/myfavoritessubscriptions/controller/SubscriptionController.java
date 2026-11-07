package com.example.myfavoritessubscriptions.controller;

import com.example.myfavoritessubscriptions.dto.SubscriptionResponse;
import com.example.myfavoritessubscriptions.service.SubscriptionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subscriptions")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SubscriptionController {
    SubscriptionService subscriptionService;

    @GetMapping("/top/{limit}")
    public ResponseEntity<List<SubscriptionResponse>> getTopPopularSubscriptions(@PathVariable int limit) {
        return ResponseEntity.ok(subscriptionService.getTopPopularSubscriptions(limit));
    }
}
