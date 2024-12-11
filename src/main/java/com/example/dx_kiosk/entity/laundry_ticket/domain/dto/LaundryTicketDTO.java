package com.example.dx_kiosk.entity.laundry_ticket.domain.dto;

import com.example.dx_kiosk.entity.laundry_ticket.domain.LaundryTicket;

public record LaundryTicketDTO(
        Long laundryTicketId,
        String laundryTicketName,
        String laundryTicketClassification,
        Integer laundryTicketPrice,
        String laundryTicketUrl

) {
    public static LaundryTicketDTO from(LaundryTicket laundryTicket) {
        return new LaundryTicketDTO(
                laundryTicket.getLaundryTicketId(),
                laundryTicket.getLaundryTicketName(),
                laundryTicket.getLaundryTicketClassification(),
                laundryTicket.getLaundryTicketPrice(),
                laundryTicket.getLaundryTicketUrl()
        );
    }
}
