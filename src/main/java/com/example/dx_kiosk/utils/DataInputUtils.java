package com.example.dx_kiosk.utils;

import com.example.dx_kiosk.entity.laundry_supplies.repository.LaundrySuppliesRepository;
import com.example.dx_kiosk.entity.laundry_ticket.repository.LaundryTicketRepository;
import com.example.dx_kiosk.entity.mealkit.repository.MealKitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.data.redis.core.RedisTemplate;

@Service
@RequiredArgsConstructor
public class DataInputUtils {

    private final MealKitRepository mealKitRepository;
    private final LaundrySuppliesRepository laundrySuppliesRepository;
    private final LaundryTicketRepository laundryTicketRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    private String filePath = "";

    public void loadCsvToRedis(String filePath, String keyPrefix) {
    }
}