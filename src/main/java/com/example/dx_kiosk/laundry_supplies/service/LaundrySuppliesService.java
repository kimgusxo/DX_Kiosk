package com.example.dx_kiosk.laundry_supplies.service;

import com.example.dx_kiosk.laundry_supplies.repository.LaundrySuppliesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LaundrySuppliesService {

    private final LaundrySuppliesRepository laundrySuppliesRepository;

}
