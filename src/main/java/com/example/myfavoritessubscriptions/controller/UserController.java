package com.example.myfavoritessubscriptions.controller;

import com.example.myfavoritessubscriptions.dto.SubscriptionRequest;
import com.example.myfavoritessubscriptions.dto.SubscriptionResponse;
import com.example.myfavoritessubscriptions.dto.UserCreateRequest;
import com.example.myfavoritessubscriptions.dto.UserResponse;
import com.example.myfavoritessubscriptions.dto.UserUpdateRequest;
import com.example.myfavoritessubscriptions.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/users")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserController {
    UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(request));
    }

    @PostMapping("/{id}/subscriptions")
    public ResponseEntity<Void> addSubscriptionToUser(@PathVariable Long id,
                                                      @Valid @RequestBody SubscriptionRequest request) {
        userService.addSubscriptionToUser(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @GetMapping("/{id}/subscriptions")
    public ResponseEntity<Set<SubscriptionResponse>> getSubscriptions(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getSubscriptionsForUser(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateRequest request) {
        userService.updateUser(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/subscriptions")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Long id,
                                                   @Valid @RequestBody SubscriptionRequest request) {
        userService.removeSubscriptionFromUser(id, request);
        return ResponseEntity.noContent().build();
    }
}
