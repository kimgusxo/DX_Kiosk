package com.example.dx_kiosk.entity.mealkit.repository.custom;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class CustomMealKitRepositoryImpl implements CustomMealKitRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public Long decrementMealKitCount(String mealKitId) {
        String key = "mealkit:" + mealKitId + ":count";
        Long currentCount = redisTemplate.opsForValue().decrement(key);

        if (currentCount != null && currentCount < 0) {
            redisTemplate.opsForValue().increment(key);
            throw new IllegalStateException("재고가 부족합니다.");
        }

        return currentCount;
    }

}
