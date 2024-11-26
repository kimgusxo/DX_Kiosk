package com.example.dx_kiosk.entity.laundry_ticket.domain.dto;

public record LaundryTicketDetailDTO(
        String laundryTicketId,
        String storeId,
        String laundryTicketClassification,
        String laundryTicketPrice
) {

}
