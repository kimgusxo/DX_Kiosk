package com.example.dx_kiosk.entity.mealkit.service;

import com.example.dx_kiosk.entity.mealkit.domain.MealKit;
import com.example.dx_kiosk.entity.mealkit.domain.dto.MealKitDTO;
import com.example.dx_kiosk.entity.mealkit.domain.dto.MealKitDetailDTO;
import com.example.dx_kiosk.entity.mealkit.repository.MealKitRepository;
import com.example.dx_kiosk.exception.FirebaseException;
import com.example.dx_kiosk.exception.ListEmptyException;
import com.example.dx_kiosk.exception.ObjectEmptyException;
import com.google.cloud.firestore.Firestore;
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
    private final Firestore db;

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
    public MealKitDetailDTO getMealKitByMealKitId(Long mealKitId, Long storeId) {
        Optional<MealKit> result =
                mealKitRepository.findMealKitByMealKitId(mealKitId);

        if (result.isEmpty()) {
            throw new ObjectEmptyException();
        }

        MealKit mealKit = result.get();

        try {
            // Firestore에서 store_id를 필터링하여 문서 가져오기
            var querySnapshot = db.collection("stores_meal_kits_count")
                    .whereEqualTo("store_id", storeId) // store_id를 기준으로 필터링
                    .get()
                    .get(); // 비동기 작업 대기

            if (querySnapshot.isEmpty()) {
                throw new ObjectEmptyException();
            }

            // 첫 번째 문서 ID 추출
            String documentId = querySnapshot.getDocuments().get(0).getId();

            // 하위 컬렉션 meal_kits_count 접근
            var mealKitCollection = db.collection("stores_meal_kits_count")
                    .document(documentId)
                    .collection("meal_kits_count")
                    .whereEqualTo("meal_kit_id", mealKitId)
                    .get()
                    .get();

            if (mealKitCollection.isEmpty()) {
                throw new ObjectEmptyException();
            }

            // 첫 번째 하위 컬렉션 데이터 가져오기
            var mealKitSnapshot = mealKitCollection.getDocuments().get(0);
            Integer count = ((Number) mealKitSnapshot.get("meal_kit_count")).intValue();

            // DTO 생성 및 반환
            return new MealKitDetailDTO(
                    mealKit.getMealKitId(),
                    storeId,
                    mealKit.getMealKitName(),
                    mealKit.getMealKitPrice(),
                    count,
                    mealKit.getMealKitClassification(),
                    mealKit.getMealKitFoodClassification(),
                    mealKit.getMealKitCompanyName(),
                    mealKit.getMealKitDescription(),
                    mealKit.getMealKitUrl()
            );

        } catch (Exception e) {
            throw new FirebaseException();
        }
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
