package com.example.dx_kiosk.mealkit.service;

import com.example.dx_kiosk.mealkit.domain.dto.MealKitDTO;
import com.example.dx_kiosk.mealkit.repository.MealKitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MealKitService {

    private final MealKitRepository mealKitRepository;

    @Transactional
    public List<MealKitDTO> getMealKitList() {

    }

    @Transactional
    public MealKitDTO getMealKitByMealKitId(String mealKitId) {
    }

    @Transactional
    public List<MealKitDTO> getMealKitListByMealKitClassification(String mealKitClassification) {
    }
}
