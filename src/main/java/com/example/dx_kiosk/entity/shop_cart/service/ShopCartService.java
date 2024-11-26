package com.example.dx_kiosk.entity.shop_cart.service;

import com.example.dx_kiosk.entity.laundry_supplies.domain.dto.LaundrySuppliesDTO;
import com.example.dx_kiosk.entity.laundry_supplies.repository.LaundrySuppliesRepository;
import com.example.dx_kiosk.entity.mealkit.domain.dto.MealKitDTO;
import com.example.dx_kiosk.entity.mealkit.repository.MealKitRepository;
import com.example.dx_kiosk.entity.shop_cart.domain.ShopCartDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopCartService {

    private final MealKitRepository mealKitRepository;
    private final LaundrySuppliesRepository laundrySuppliesRepository;

    @Transactional
    public Void buy(ShopCartDTO shopCart) {
        List<MealKitDTO> mealKitDTOList = shopCart.getMealKitsDTO();
        List<LaundrySuppliesDTO> laundrySuppliesDTOList = shopCart.getLaundrySuppliesDTO();

        mealKitDTOList.forEach(mk -> {
            mealKitRepository.decrementMealKitCount(mk.mealKitId());
        });

        laundrySuppliesDTOList.forEach(ls -> {
            laundrySuppliesRepository.decrementLaundrySuppliesCount(ls.laundrySuppliesId());
        });

        // 파이어베이스 연동 부분

    }
}
