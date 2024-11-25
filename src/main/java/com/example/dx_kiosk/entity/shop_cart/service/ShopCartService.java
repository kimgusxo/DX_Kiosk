package com.example.dx_kiosk.entity.shop_cart.service;

import com.example.dx_kiosk.entity.laundry_supplies.repository.LaundrySuppliesRepository;
import com.example.dx_kiosk.entity.mealkit.repository.MealKitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopCartService {

    private final MealKitRepository mealKitRepository;
    private final LaundrySuppliesRepository laundrySuppliesRepository;



}
