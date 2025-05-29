package com.example.myfavoritessubscriptions.service;

import com.example.myfavoritessubscriptions.entity.Subscription;
import com.example.myfavoritessubscriptions.entity.User;
import com.example.myfavoritessubscriptions.exception.UserNotFoundException;
import com.example.myfavoritessubscriptions.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {
    UserRepository userRepository;

    String USER_NOT_FOUND_MESSAGE = "Не найден пользователь с id: ";

    public User createUser(User user) {
        var createUser = userRepository.save(user);
        log.info("Пользователь создан: {}", user);
        return createUser;
    }

    public User getUser(Long userId) {
        return findUserById(userId);
    }

    public void updateUser(Long userId, User user) {
        findUserById(userId);
        user.setId(userId);
        userRepository.save(user);
        log.info("Пользователь обновлен: {}", user);
    }

    public void deleteUser(Long userId) {
        var user = findUserById(userId);
        userRepository.deleteById(userId);
        log.info("Пользователь удален: {}", user);
    }

    public void addSubscriptionToUser(Long userId, Subscription subscription) {
        var user = findUserById(userId);
        user.getSubscriptions().add(subscription);
        userRepository.save(user);
        log.info("Пользователю {} добавлена подписка на {}", user, subscription);
    }

    public void removeSubscriptionFromUser(Long userId, Subscription subscription) {
        var user = findUserById(userId);
        user.getSubscriptions().remove(subscription);
        userRepository.save(user);
        log.info("Пользователю {} удалена подписка на {}", user, subscription);
    }

    public Set<Subscription> getSubscriptionsForUser(Long userId) {
        var user = findUserById(userId);
        var subscriptions = user.getSubscriptions();
        if (subscriptions.isEmpty()) {
            log.warn("У пользователя {} не найдено ни одной подиски", user);
            return new HashSet<>();
        }
        return subscriptions;
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error(USER_NOT_FOUND_MESSAGE + "{}", userId);
                    return new UserNotFoundException(USER_NOT_FOUND_MESSAGE + userId);
                });
    }
}
