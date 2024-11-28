package com.example.dx_kiosk.entity.shop_cart.service;

import com.example.dx_kiosk.entity.shop_cart.domain.ShopCartDTO;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteBatch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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
            // TODO: 주문에 맞는 current 문서 업데이트 로직 추가

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
}
