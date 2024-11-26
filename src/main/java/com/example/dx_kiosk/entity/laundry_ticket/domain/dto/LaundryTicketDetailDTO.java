package com.example.dx_kiosk.entity.laundry_ticket.domain.dto;

public record LaundryTicketDetailDTO(
        Long laundryTicketId,
        Long storeId,
        String laundryTicketClassification,
        String laundryTicketPrice
) {

}
