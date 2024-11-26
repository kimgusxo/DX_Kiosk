package com.example.dx_kiosk.entity.mealkit.service;

import com.example.dx_kiosk.entity.mealkit.domain.MealKit;
import com.example.dx_kiosk.entity.mealkit.domain.dto.MealKitDTO;
import com.example.dx_kiosk.entity.mealkit.domain.dto.MealKitDetailDTO;
import com.example.dx_kiosk.entity.mealkit.repository.MealKitRepository;
import com.example.dx_kiosk.exception.ListEmptyException;
import com.example.dx_kiosk.exception.ObjectEmptyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MealKitService {

    private final MealKitRepository mealKitRepository;

    @Transactional
    public List<MealKitDTO> getMealKitList() {
        List<MealKit> results =
                mealKitRepository.findAll();

        if (results.isEmpty()) {
            throw new ListEmptyException();
        }

        List<MealKitDTO> resultsDTO = new ArrayList<>();

        results.forEach(r -> {
            resultsDTO.add(MealKitDTO.from(r));
        });

        return resultsDTO;
    }

    @Transactional
    public MealKitDetailDTO getMealKitByMealKitId(Long mealKitId) {
        Optional<MealKit> result =
                mealKitRepository.findMealKitByMealKitId(mealKitId);

        if (result.isEmpty()) {
            throw new ObjectEmptyException();
        }

        // 파이어베이스에서 storeId와 count 채워서 상세페이지 보여주기

        return null;
    }

    @Transactional
    public List<MealKitDTO> getMealKitListByMealKitClassification(String mealKitClassification) {
        List<MealKit> results =
                mealKitRepository.findMealKitListByMealKitClassification(mealKitClassification);

        if (results.isEmpty()) {
            throw new ListEmptyException();
        }

        List<MealKitDTO> resultsDTO = new ArrayList<>();

        results.forEach(r -> {
            resultsDTO.add(MealKitDTO.from(r));
        });

        return resultsDTO;

    }
}
