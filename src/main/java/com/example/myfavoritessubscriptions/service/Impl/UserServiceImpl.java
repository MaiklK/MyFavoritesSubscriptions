package com.example.myfavoritessubscriptions.service.Impl;

import com.example.myfavoritessubscriptions.dto.SubscriptionMapper;
import com.example.myfavoritessubscriptions.dto.SubscriptionRequest;
import com.example.myfavoritessubscriptions.dto.SubscriptionResponse;
import com.example.myfavoritessubscriptions.dto.UserCreateRequest;
import com.example.myfavoritessubscriptions.dto.UserResponse;
import com.example.myfavoritessubscriptions.dto.UserUpdateRequest;
import com.example.myfavoritessubscriptions.entity.Subscription;
import com.example.myfavoritessubscriptions.entity.User;
import com.example.myfavoritessubscriptions.exception.SubscriptionNotFoundException;
import com.example.myfavoritessubscriptions.exception.UserNotFoundException;
import com.example.myfavoritessubscriptions.repository.SubscriptionRepository;
import com.example.myfavoritessubscriptions.repository.UserRepository;
import com.example.myfavoritessubscriptions.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.Set;

@Slf4j
@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {
    static final String USER_NOT_FOUND_MESSAGE_PREFIX = "Не найден пользователь с id: ";
    static final String SUBSCRIPTION_NOT_FOUND_MESSAGE_PREFIX = "Не найдена подписка: ";

    UserRepository userRepository;
    SubscriptionRepository subscriptionRepository;

    @Override
    public UserResponse createUser(UserCreateRequest request) {
        var user = User.builder()
                .name(request.name())
                .email(request.email())
                .subscriptions(new LinkedHashSet<>())
                .build();
        var createdUser = userRepository.save(user);
        log.info("Пользователь создан: {}", createdUser);
        return toResponse(createdUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUser(Long userId) {
        return toResponse(findUserById(userId));
    }

    @Override
    public void updateUser(Long userId, UserUpdateRequest request) {
        var user = findUserById(userId);
        user.setName(request.name());
        user.setEmail(request.email());
        userRepository.save(user);
        log.info("Пользователь обновлен: {}", user);
    }

    @Override
    public void deleteUser(Long userId) {
        var user = findUserById(userId);
        userRepository.delete(user);
        log.info("Пользователь удален: {}", user);
    }

    @Override
    public void addSubscriptionToUser(Long userId, SubscriptionRequest request) {
        var user = findUserById(userId);
        var subscription = findOrCreateSubscription(request.subscriptionName());
        var subscriptions = user.getSubscriptions();
        if (subscriptions == null) {
            subscriptions = new LinkedHashSet<>();
            user.setSubscriptions(subscriptions);
        }
        if (!subscriptions.add(subscription)) {
            log.info("Пользователю {} уже добавлена подписка на {}", user, subscription);
            return;
        }
        userRepository.save(user);
        log.info("Пользователю {} добавлена подписка на {}", user, subscription);
    }

    @Override
    public void removeSubscriptionFromUser(Long userId, SubscriptionRequest request) {
        var user = findUserById(userId);
        var subscription = subscriptionRepository
                .findBySubscriptionNameIgnoreCase(request.subscriptionName())
                .orElseThrow(() -> new SubscriptionNotFoundException(
                        SUBSCRIPTION_NOT_FOUND_MESSAGE_PREFIX + request.subscriptionName()));
        var subscriptions = user.getSubscriptions();
        if (subscriptions == null || !subscriptions.remove(subscription)) {
            throw new SubscriptionNotFoundException(
                    SUBSCRIPTION_NOT_FOUND_MESSAGE_PREFIX + request.subscriptionName());
        }
        userRepository.save(user);
        log.info("Пользователю {} удалена подписка на {}", user, subscription);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<SubscriptionResponse> getSubscriptionsForUser(Long userId) {
        var user = findUserById(userId);
        var subscriptions = user.getSubscriptions();
        if (subscriptions == null || subscriptions.isEmpty()) {
            log.warn("У пользователя {} не найдено ни одной подписки", user);
            return Set.of();
        }
        return SubscriptionMapper.toSortedResponseSet(subscriptions);
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE_PREFIX + userId));
    }

    private Subscription findOrCreateSubscription(String subscriptionName) {
        return subscriptionRepository.findBySubscriptionNameIgnoreCase(subscriptionName)
                .orElseGet(() -> Subscription.builder()
                        .subscriptionName(subscriptionName)
                        .build());
    }

    private UserResponse toResponse(User user) {
        var subscriptions = user.getSubscriptions() == null
                ? Set.<SubscriptionResponse>of()
                : SubscriptionMapper.toSortedResponseSet(user.getSubscriptions());
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), subscriptions);
    }
}
