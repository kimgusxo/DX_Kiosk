package com.example.dx_kiosk.entity.shop_cart.service;

import com.example.dx_kiosk.entity.shop_cart.domain.ShopCartDTO;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteBatch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ShopCartService {

    private final Firestore db;

    public Void buy(ShopCartDTO shopCart) {
        try {
            // 마지막 order_id를 기반으로 새 ID 생성
            int newOrderId = getNextOrderId(db);

            // Order 데이터 준비
            Map<String, Object> orderData = new HashMap<>();
            orderData.put("order_id", newOrderId);
            orderData.put("user_id", shopCart.getUserId());
            orderData.put("store_id", shopCart.getStoreId());
            orderData.put("space_is_used", shopCart.getSpaceIsUsed());
            orderData.put("order_total_price", shopCart.getTotalPrice());
            orderData.put("order_time", LocalDateTime.now().toString()); // 주문 시간 추가

            // Firestore Batch 처리 시작
            WriteBatch batch = db.batch();

            // 상위 문서(주문 데이터) 저장
            var orderRef = db.collection("orders").document(String.valueOf(newOrderId));
            batch.set(orderRef, orderData);

            // Meal Kits 하위 컬렉션 저장
            if (shopCart.getMealKitQuantities() != null) {
                for (Map.Entry<Long, Integer> entry : shopCart.getMealKitQuantities().entrySet()) {
                    Map<String, Object> mealKitData = Map.of(
                            "meal_kit_id", entry.getKey(),
                            "quantity", entry.getValue()
                    );
                    batch.set(orderRef.collection("meal_kits").document(String.valueOf(entry.getKey())), mealKitData);
                }
            }

            // Laundry Supplies 하위 컬렉션 저장
            if (shopCart.getLaundrySuppliesQuantities() != null) {
                for (Map.Entry<Long, Integer> entry : shopCart.getLaundrySuppliesQuantities().entrySet()) {
                    Map<String, Object> laundrySupplyData = Map.of(
                            "laundry_supplies_id", entry.getKey(),
                            "quantity", entry.getValue()
                    );
                    batch.set(orderRef.collection("laundry_supplies").document(String.valueOf(entry.getKey())), laundrySupplyData);
                }
            }

            // Laundry Tickets 하위 컬렉션 저장
            if (shopCart.getLaundryTicketUsage() != null) {
                for (Map.Entry<Long, Boolean> entry : shopCart.getLaundryTicketUsage().entrySet()) {
                    Map<String, Object> laundryTicketData = Map.of(
                            "laundry_ticket_id", entry.getKey(),
                            "is_used", entry.getValue()
                    );
                    batch.set(orderRef.collection("laundry_tickets").document(String.valueOf(entry.getKey())), laundryTicketData);
                }
            }

            // Home Appliances 하위 컬렉션 저장
            if (shopCart.getHomeAppliancesUsage() != null) {
                for (Map.Entry<Long, Boolean> entry : shopCart.getHomeAppliancesUsage().entrySet()) {
                    Map<String, Object> homeApplianceData = Map.of(
                            "home_appliances_id", entry.getKey(),
                            "is_used", entry.getValue()
                    );
                    batch.set(orderRef.collection("home_appliances").document(String.valueOf(entry.getKey())), homeApplianceData);
                }
            }

            // Batch 커밋
            batch.commit().get(); // 비동기 작업을 동기적으로 처리
            System.out.println("Order saved successfully with order_id: " + newOrderId);

            // TODO: 커밋 완료 후 재고 데이터 업데이트 로직 추가
            updateStock(shopCart);

//            // TODO: 주문에 맞는 current 문서 업데이트 로직 추가
//            manageApplianceUsage(shopCart);

        } catch (Exception e) {
            System.err.println("Error saving order: " + e.getMessage());
        }
        return null;
    }

    private int getNextOrderId(Firestore db) throws Exception {
        // Firestore에서 가장 큰 order_id 하나만 가져오기
        return db.collection("orders")
                .orderBy("order_id", com.google.cloud.firestore.Query.Direction.DESCENDING) // order_id 기준 내림차순 정렬
                .limit(1) // 가장 큰 값 하나만 가져오기
                .get()
                .get()
                .getDocuments()
                .stream()
                .findFirst()
                .map(document -> document.getLong("order_id").intValue()) // order_id 필드 값 가져오기
                .map(lastOrderId -> lastOrderId + 1) // 다음 ID 생성
                .orElse(1); // 데이터가 없으면 1 반환
    }

    // 재고 데이터 업데이트 로직
    private void updateStock(ShopCartDTO shopCart) {
        db.runTransaction(transaction -> {
            // Meal Kits 재고 차감
            if (shopCart.getMealKitQuantities() != null) {
                for (Map.Entry<Long, Integer> entry : shopCart.getMealKitQuantities().entrySet()) {
                    DocumentReference docRef = db.collection("stores_meal_kits_count")
                            .document(String.valueOf(shopCart.getStoreId()))
                            .collection("meal_kits_count")
                            .document(String.valueOf(entry.getKey()));
                    DocumentSnapshot snapshot = transaction.get(docRef).get();
                    int currentCount = snapshot.getLong("meal_kit_count").intValue();
                    transaction.update(docRef, "meal_kit_count", currentCount - entry.getValue());
                }
            }

            // Laundry Supplies 재고 차감
            if (shopCart.getLaundrySuppliesQuantities() != null) {
                for (Map.Entry<Long, Integer> entry : shopCart.getLaundrySuppliesQuantities().entrySet()) {
                    DocumentReference docRef = db.collection("stores_laundry_supplies_count")
                            .document(String.valueOf(shopCart.getStoreId()))
                            .collection("laundry_supplies_count")
                            .document(String.valueOf(entry.getKey()));
                    DocumentSnapshot snapshot = transaction.get(docRef).get();
                    int currentCount = snapshot.getLong("laundry_supplies_count").intValue();
                    transaction.update(docRef, "laundry_supplies_count", currentCount - entry.getValue());
                }
            }
            return null;
        });
    }

    // current 문서 업데이트 및 60분 뒤 삭제
    private void manageApplianceUsage(ShopCartDTO shopCart) {
        if (shopCart.getHomeAppliancesUsage() != null) {
            for (Map.Entry<Long, Boolean> entry : shopCart.getHomeAppliancesUsage().entrySet()) {
                Long applianceType = entry.getKey(); // 가전 타입 (10: 세탁기, 11: 건조기, 12: 스타일러, 13: 슈케어)
                String storeId = shopCart.getStoreId();

                // 현재 사용 중인 가전 불러오기
                List<Integer> usedPositions = getUsedAppliances(storeId, applianceType);

                // 가능한 가전 중에서 배정
                Integer assignedPosition = assignAppliancePosition(applianceType, usedPositions);
                if (assignedPosition == null) {
                    System.err.println("No available appliances for type: " + applianceType);
                    continue;
                }

                // Firestore에 등록
                DocumentReference docRef = db.collection("current_used_appliances")
                        .document(storeId)
                        .collection("home_appliances_used")
                        .document(String.valueOf(assignedPosition));

                Map<String, Object> applianceData = Map.of(
                        "home_appliances_id", applianceType,
                        "home_appliances_position", assignedPosition,
                        "start_time", LocalDateTime.now().toString()
                );
                docRef.set(applianceData);

                System.out.println("Assigned appliance position: " + assignedPosition + " for type: " + applianceType);

                // 60분 뒤 삭제
                scheduleApplianceDeletion(docRef, assignedPosition);
            }
        }
    }

    // 현재 사용 중인 가전 목록 조회
    private List<Integer> getUsedAppliances(String storeId, Long applianceType) {
        List<Integer> usedPositions = new ArrayList<>();
        try {
            DocumentReference docRef = db.collection("current_used_appliances").document(storeId);
            DocumentSnapshot snapshot = docRef.get().get();
            if (snapshot.exists() && snapshot.get("home_appliances_used") != null) {
                Map<String, Map<String, Object>> appliances =
                        (Map<String, Map<String, Object>>) snapshot.get("home_appliances_used");
                for (Map.Entry<String, Map<String, Object>> entry : appliances.entrySet()) {
                    Long type = ((Number) entry.getValue().get("home_appliances_id")).longValue();
                    if (type.equals(applianceType)) {
                        usedPositions.add(((Number) entry.getValue().get("home_appliances_position")).intValue());
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error fetching used appliances: " + e.getMessage());
        }
        return usedPositions;
    }

    // 가전 위치 배정
    private Integer assignAppliancePosition(Long applianceType, List<Integer> usedPositions) {
        List<Integer> allPositions = getAllPositionsByType(applianceType);
        allPositions.removeAll(usedPositions); // 사용 중인 가전 제외
        return allPositions.isEmpty() ? null : allPositions.get(0); // 사용 가능한 가전 반환
    }

    // 가전 타입별 ID 범위 정의
    private List<Integer> getAllPositionsByType(Long applianceType) {
        switch (applianceType.intValue()) {
            case 10: return Arrays.asList(1, 2, 3); // 세탁기
            case 11: return Arrays.asList(1, 2, 3); // 건조기
            case 12: return Arrays.asList(1, 2, 3); // 스타일러
            case 13: return Arrays.asList(1, 2, 3); // 슈케어
            default: return Collections.emptyList();
        }
    }

    // 60분 뒤 삭제 스케줄 설정
    private void scheduleApplianceDeletion(DocumentReference docRef, Integer assignedPosition) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(() -> {
            try {
                docRef.delete();
                System.out.println("Appliance usage deleted for position: " + assignedPosition);
            } catch (Exception e) {
                System.err.println("Error deleting appliance usage: " + e.getMessage());
            }
        }, 60, TimeUnit.MINUTES);
    }
}
