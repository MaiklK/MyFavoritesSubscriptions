package com.example.myfavoritessubscriptions.controller;

import com.example.myfavoritessubscriptions.entity.Subscription;
import com.example.myfavoritessubscriptions.entity.User;
import com.example.myfavoritessubscriptions.service.Impl.UserServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private UserServiceImpl userServiceImpl;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        var createdUser = userServiceImpl.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PostMapping("{id}/subscriptions")
    public ResponseEntity<Void> addSubscriptionToUser(@PathVariable Long id, @RequestBody Subscription subscription) {
        userServiceImpl.addSubscriptionToUser(id, subscription);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        var user = userServiceImpl.getUser(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("{id}/subscriptions")
    public ResponseEntity<Set<Subscription>> getSubscriptions(@PathVariable Long id) {
        var subscriptions = userServiceImpl.getSubscriptionsForUser(id);
        return ResponseEntity.ok(subscriptions);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateUser(@PathVariable Long id, @RequestBody User user) {
        userServiceImpl.updateUser(id, user);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userServiceImpl.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}/subscriptions")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Long id, @RequestBody Subscription subscription) {
        userServiceImpl.removeSubscriptionFromUser(id, subscription);
        return ResponseEntity.noContent().build();
    }
}
