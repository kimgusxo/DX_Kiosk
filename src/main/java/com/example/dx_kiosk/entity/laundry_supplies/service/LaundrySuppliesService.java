package com.example.dx_kiosk.entity.laundry_supplies.service;

import com.example.dx_kiosk.entity.laundry_supplies.domain.LaundrySupplies;
import com.example.dx_kiosk.entity.laundry_supplies.domain.dto.LaundrySuppliesDTO;
import com.example.dx_kiosk.entity.laundry_supplies.repository.LaundrySuppliesRepository;
import com.example.dx_kiosk.exception.ListEmptyException;
import com.example.dx_kiosk.exception.ObjectEmptyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LaundrySuppliesService {

    private final LaundrySuppliesRepository laundrySuppliesRepository;

    @Transactional
    public List<LaundrySuppliesDTO> getLaundrySuppliesList() {
        List<LaundrySupplies> results =
                laundrySuppliesRepository.findAll();

        if (results.isEmpty()) {
            throw new ListEmptyException();
        }

        List<LaundrySuppliesDTO> resultsDTO = new ArrayList<>();

        results.forEach(r -> {
            resultsDTO.add(LaundrySuppliesDTO.from(r));
        });

        return resultsDTO;
    }

    @Transactional
    public LaundrySuppliesDTO getLaundrySuppliesByLaundrySuppliesId(String laundrySuppliesId) {
        Optional<LaundrySupplies> result =
                laundrySuppliesRepository.findLaundrySuppliesByLaundrySuppliesId(laundrySuppliesId);

        if (result.isEmpty()) {
            throw new ObjectEmptyException();
        }

        return LaundrySuppliesDTO.from(result.get());
    }
}
