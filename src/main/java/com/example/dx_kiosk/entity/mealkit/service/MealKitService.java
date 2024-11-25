package com.example.dx_kiosk.entity.mealkit.service;

import com.example.dx_kiosk.entity.mealkit.domain.MealKit;
import com.example.dx_kiosk.entity.mealkit.domain.dto.MealKitDTO;
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
    public MealKitDTO getMealKitByMealKitId(String mealKitId) {
        Optional<MealKit> result =
                mealKitRepository.findById(mealKitId);

        if (result.isEmpty()) {
            throw new ObjectEmptyException();
        }

        return MealKitDTO.from(result.get());
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
