package com.example.dx_kiosk.entity.shop_cart.domain;

import com.example.dx_kiosk.entity.laundry_supplies.domain.dto.LaundrySuppliesDTO;
import com.example.dx_kiosk.entity.laundry_ticket.domain.dto.LaundryTicketDTO;
import com.example.dx_kiosk.entity.mealkit.domain.dto.MealKitDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ShopCartDTO {

    private Boolean spaceIsUsed;
    private Integer totalPrice;

    private List<MealKitDTO> mealKitsDTO;
    private List<LaundryTicketDTO> laundryTicketsDTO;
    private List<LaundrySuppliesDTO> laundrySuppliesDTO;

}
