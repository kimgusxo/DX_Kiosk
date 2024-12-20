package com.example.dx_kiosk.entity.laundry_supplies.service;

import com.example.dx_kiosk.entity.laundry_supplies.domain.LaundrySupplies;
import com.example.dx_kiosk.entity.laundry_supplies.domain.dto.LaundrySuppliesDTO;
import com.example.dx_kiosk.entity.laundry_supplies.domain.dto.LaundrySuppliesDetailDTO;
import com.example.dx_kiosk.entity.laundry_supplies.repository.LaundrySuppliesRepository;
import com.example.dx_kiosk.exception.FirebaseException;
import com.example.dx_kiosk.exception.ListEmptyException;
import com.example.dx_kiosk.exception.ObjectEmptyException;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LaundrySuppliesService {

    private final LaundrySuppliesRepository laundrySuppliesRepository;
    private final Firestore db;

    @Transactional
    public List<LaundrySuppliesDTO> getLaundrySuppliesList() {
        List<LaundrySupplies> results =
                laundrySuppliesRepository.findAll();

        if (results.isEmpty()) {
            throw new ListEmptyException();
        }

        List<LaundrySuppliesDTO> resultsDTO = new ArrayList<>();

        results.forEach(r -> {
            resultsDTO.add(LaundrySuppliesDTO.from(r));
        });

        return resultsDTO;
    }

    @Transactional
    public LaundrySuppliesDetailDTO getLaundrySuppliesByLaundrySuppliesId(Long laundrySuppliesId, Long storeId) {
        Optional<LaundrySupplies> result =
                laundrySuppliesRepository.findLaundrySuppliesByLaundrySuppliesId(laundrySuppliesId);

        if (result.isEmpty()) {
            throw new ObjectEmptyException();
        }

        LaundrySupplies laundrySupplies = result.get();

        try {
            // Firestore에서 store_id를 필터링하여 문서 가져오기
            var querySnapshot = db.collection("stores_laundry_supplies_count")
                    .whereEqualTo("store_id", storeId) // store_id를 기준으로 필터링
                    .get()
                    .get(); // 비동기 작업 대기

            if (querySnapshot.isEmpty()) {
                throw new ObjectEmptyException();
            }

            // 첫 번째 문서 ID 추출
            String documentId = querySnapshot.getDocuments().get(0).getId();

            // 하위 컬렉션 meal_kits_count 접근
            var laundrySuppliesCollection = db.collection("stores_laundry_supplies_count")
                    .document(documentId)
                    .collection("laundry_supplies_count")
                    .whereEqualTo("laundry_supplies_id", laundrySuppliesId)
                    .get()
                    .get();

            if (laundrySuppliesCollection.isEmpty()) {
                throw new ObjectEmptyException();
            }

            // 첫 번째 하위 컬렉션 데이터 가져오기
            var laundrySuppliesSnapshot = laundrySuppliesCollection.getDocuments().get(0);
            Integer count = ((Number) laundrySuppliesSnapshot.get("laundry_supplies_count")).intValue();

            // DTO 생성 및 반환
            return new LaundrySuppliesDetailDTO(
                    laundrySupplies.getLaundrySuppliesId(),
                    storeId,
                    laundrySupplies.getLaundrySuppliesName(),
                    laundrySupplies.getLaundrySuppliesPrice(),
                    count,
                    laundrySupplies.getLaundrySuppliesClassification(),
                    laundrySupplies.getLaundrySuppliesCompanyName(),
                    laundrySupplies.getLaundrySuppliesDescription(),
                    laundrySupplies.getLaundrySuppliesUrl()
            );

        } catch (Exception e) {
            throw new FirebaseException();
        }
    }

}
