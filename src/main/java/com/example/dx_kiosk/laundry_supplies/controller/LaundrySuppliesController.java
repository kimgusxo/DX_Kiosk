package com.example.dx_kiosk.laundry_supplies.controller;

import com.example.dx_kiosk.laundry_supplies.service.LaundrySuppliesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/laundrySupplies")
@RequiredArgsConstructor
public class LaundrySuppliesController {

    private final LaundrySuppliesService laundrySuppliesService;

}
