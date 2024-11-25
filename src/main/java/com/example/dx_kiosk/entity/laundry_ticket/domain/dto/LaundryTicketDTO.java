package com.example.dx_kiosk.entity.laundry_ticket.domain.dto;

import com.example.dx_kiosk.entity.laundry_ticket.domain.LaundryTicket;

public record LaundryTicketDTO(
        String laundryTicketId,
        String laundryTicketClassification,
        String laundryTicketPrice
) {
    public static LaundryTicketDTO from(LaundryTicket laundryTicket) {
        return new LaundryTicketDTO(
                laundryTicket.getLaundryTicketId(),
                laundryTicket.getLaundryTicketClassification(),
                laundryTicket.getLaundryTicketPrice()
        );
    }
}
