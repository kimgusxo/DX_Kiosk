package com.example.dx_kiosk.entity.laundry_supplies.repository.custom;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomLaundrySuppliesRepositoryImpl implements CustomLaundrySuppliesRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public Long decrementLaundrySuppliesCount(String mealKitId) {
        String key = "laundrySupplies:" + mealKitId + ":count";
        Long currentCount = redisTemplate.opsForValue().decrement(key);

        if (currentCount != null && currentCount < 0) {
            redisTemplate.opsForValue().increment(key);
            throw new IllegalStateException("재고가 부족합니다.");
        }

        return currentCount;
    }

}
