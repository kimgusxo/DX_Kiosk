package com.example.dx_kiosk.entity.laundry_ticket.controller;

import com.example.dx_kiosk.entity.laundry_ticket.domain.dto.LaundryTicketDTO;
import com.example.dx_kiosk.entity.laundry_ticket.service.LaundryTicketService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/laundryTicket")
@RequiredArgsConstructor
public class LaundryTicketController {

    private final LaundryTicketService laundryTicketService;

    @GetMapping("/get/all")
    @Operation(summary = "Get LaundryTicketList", description = "모든 세탁권 리스트 가져오기")
    public ResponseEntity<List<LaundryTicketDTO>> getLaundryTicketList() {
        log.info("getLaundryTicketList");
        return new ResponseEntity<>(laundryTicketService.getLaundryTicketList(), HttpStatus.OK);
    }

    @GetMapping("/get/one/{laundryTicketId}")
    @Operation(summary = "Get LaundryTicket By LaundryTicketId", description = "선택한 세탁권 가져오기")
    public ResponseEntity<LaundryTicketDTO> getLaundryTicketByLaundryTicketId(@PathVariable("laundryTicketId") String laundryTicketId) {
        log.info("getLaundryTicketByLaundryTicketId : laundryTicketId = {}", laundryTicketId);
        return new ResponseEntity<>(laundryTicketService.getLaundryTicketByLaundryTicketId(laundryTicketId), HttpStatus.OK);
    }

}
