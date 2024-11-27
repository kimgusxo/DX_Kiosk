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

            // Meal Kits 데이터 준비
            Map<String, Map<String, Object>> mealKitsOrder = new HashMap<>();
            if (shopCart.getMealKitQuantities() != null) {
                for (Map.Entry<Long, Integer> entry : shopCart.getMealKitQuantities().entrySet()) {
                    Map<String, Object> mealKitData = Map.of(
                            "meal_kit_id", entry.getKey(),
                            "quantity", entry.getValue()
                    );
                    mealKitsOrder.put(String.valueOf(entry.getKey()), mealKitData);
                }
            }
            orderData.put("meal_kits_order", mealKitsOrder);

            // Laundry Supplies 데이터 준비
            Map<String, Map<String, Object>> laundrySuppliesOrder = new HashMap<>();
            if (shopCart.getLaundrySuppliesQuantities() != null) {
                for (Map.Entry<Long, Integer> entry : shopCart.getLaundrySuppliesQuantities().entrySet()) {
                    Map<String, Object> laundrySupplyData = Map.of(
                            "laundry_supplies_id", entry.getKey(),
                            "quantity", entry.getValue()
                    );
                    laundrySuppliesOrder.put(String.valueOf(entry.getKey()), laundrySupplyData);
                }
            }
            orderData.put("laundry_supplies_order", laundrySuppliesOrder);

            // Laundry Tickets 데이터 준비
            Map<String, Map<String, Object>> laundryTicketsUsed = new HashMap<>();
            if (shopCart.getLaundryTicketUsage() != null) {
                for (Map.Entry<Long, Boolean> entry : shopCart.getLaundryTicketUsage().entrySet()) {
                    Map<String, Object> laundryTicketData = Map.of(
                            "laundry_ticket_id", entry.getKey(),
                            "is_used", entry.getValue()
                    );
                    laundryTicketsUsed.put(String.valueOf(entry.getKey()), laundryTicketData);
                }
            }
            orderData.put("laundry_tickets_used", laundryTicketsUsed);

            // Home Appliances 데이터 준비
            Map<String, Map<String, Object>> homeAppliancesUsed = new HashMap<>();
            if (shopCart.getHomeAppliancesUsage() != null) {
                for (Map.Entry<Long, Boolean> entry : shopCart.getHomeAppliancesUsage().entrySet()) {
                    Map<String, Object> homeApplianceData = Map.of(
                            "home_appliances_id", entry.getKey(),
                            "is_used", entry.getValue()
                    );
                    homeAppliancesUsed.put(String.valueOf(entry.getKey()), homeApplianceData);
                }
            }
            orderData.put("home_appliances_used", homeAppliancesUsed);

            // Firestore Batch 처리 시작
            WriteBatch batch = db.batch();
            batch.set(db.collection("orders").document(String.valueOf(newOrderId)), orderData);

            // Batch 커밋
            batch.commit().get(); // 비동기 작업을 동기적으로 처리
            System.out.println("Order saved successfully with order_id: " + newOrderId);
            
            // 이거 커밋이 끝나면 재고 데이터 업데이트 해야함
            // 그리고 주문이 완료되면 그거에 맞는 current문서 업데이트 해야함

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