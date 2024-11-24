package com.example.dx_kiosk.laundry_ticket.service;

import com.example.dx_kiosk.laundry_ticket.repository.LaundryTicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LaundryTicketService {

    private final LaundryTicketRepository laundryTicketRepository;

}
