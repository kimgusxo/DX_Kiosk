package com.example.dx_kiosk.entity.laundry_supplies.service;

import com.example.dx_kiosk.entity.laundry_supplies.domain.dto.LaundrySuppliesDTO;
import com.example.dx_kiosk.entity.laundry_supplies.repository.LaundrySuppliesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LaundrySuppliesService {

    private final LaundrySuppliesRepository laundrySuppliesRepository;

    @Transactional
    public List<LaundrySuppliesDTO> getLaundrySuppliesList() {
    }

    @Transactional
    public LaundrySuppliesDTO getLaundrySuppliesByLaundrySuppliesId(String laundrySuppliesId) {
    }
}
