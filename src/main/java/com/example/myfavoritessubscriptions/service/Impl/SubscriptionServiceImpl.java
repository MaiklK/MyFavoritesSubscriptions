package com.example.myfavoritessubscriptions.service.Impl;

import com.example.myfavoritessubscriptions.dto.SubscriptionMapper;
import com.example.myfavoritessubscriptions.dto.SubscriptionResponse;
import com.example.myfavoritessubscriptions.repository.SubscriptionRepository;
import com.example.myfavoritessubscriptions.service.SubscriptionService;
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
public class SubscriptionServiceImpl implements SubscriptionService {
    SubscriptionRepository subscriptionRepository;

    @Override
    public List<SubscriptionResponse> getTopPopularSubscriptions(int limit) {
        if (limit <= 0) {
            return List.of();
        }
        return SubscriptionMapper.toSortedResponses(
                subscriptionRepository.findTopPopularSubscriptions().stream()
                        .limit(limit)
                        .toList());
    }
}
