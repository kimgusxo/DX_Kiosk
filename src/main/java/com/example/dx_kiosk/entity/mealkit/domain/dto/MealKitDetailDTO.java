package com.example.dx_kiosk.entity.mealkit.domain.dto;

public record MealKitDetailDTO(
        Long mealKitId,
        Long storeId,
        String mealKitName,
        Integer mealKitPrice,
        Integer mealKitCount,
        String mealKitClassification,
        String mealKitFoodClassification,
        String mealKitCompanyName,
        String mealKitDescription,
        String mealKitUrl
) {
}
