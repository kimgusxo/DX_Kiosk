package com.example.dx_kiosk.laundry_ticket.controller;

import com.example.dx_kiosk.laundry_ticket.service.LaundryTicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/laundryTicket")
@RequiredArgsConstructor
public class LaundryTicketController {

    private final LaundryTicketService laundryTicketService;


}
