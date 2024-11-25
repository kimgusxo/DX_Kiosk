package com.example.dx_kiosk.laundry_ticket.service;

import com.example.dx_kiosk.laundry_ticket.domain.dto.LaundryTicketDTO;
import com.example.dx_kiosk.laundry_ticket.repository.LaundryTicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LaundryTicketService {

    private final LaundryTicketRepository laundryTicketRepository;

    public List<LaundryTicketDTO> getLaundryTicketList() {
    }

    public LaundryTicketDTO getLaundryTicketByLaundryTicketId(String laundryTicketId) {
    }
}
