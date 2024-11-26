package com.example.dx_kiosk.entity.mealkit.domain.dto;

import com.example.dx_kiosk.entity.mealkit.domain.MealKit;

public record MealKitDTO(
        String mealKitId,
        String mealKitName,
        Integer mealKitPrice,
        String mealKitClassification,
        String mealKitFoodClassification,
        String mealKitCompanyName,
        String mealKitDescription,
        String mealKitUrl
) {
    public static MealKitDTO from(MealKit mealKit) {
        return new MealKitDTO(
                mealKit.getMealKitId(),
                mealKit.getMealKitName(),
                mealKit.getMealKitPrice(),
                mealKit.getMealKitClassification(),
                mealKit.getMealKitFoodClassification(),
                mealKit.getMealKitCompanyName(),
                mealKit.getMealKitDescription(),
                mealKit.getMealKitUrl()
        );
    }
}
