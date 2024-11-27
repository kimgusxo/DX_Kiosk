package com.example.dx_kiosk.entity.shop_cart.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class ShopCartDTO {

    private String userId;

    private String storeId;

    private Boolean spaceIsUsed;
    private Integer totalPrice;

    private HashMap<Long, Integer> mealKitQuantities;
    private HashMap<Long, Integer> laundrySuppliesQuantities;
    private HashMap<Long, Boolean> laundryTicketUsage;
    private HashMap<Long, Boolean> homeAppliancesUsage;

}
