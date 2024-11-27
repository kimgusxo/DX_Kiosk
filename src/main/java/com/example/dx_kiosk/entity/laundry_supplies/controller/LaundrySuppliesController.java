package com.example.dx_kiosk.entity.laundry_supplies.controller;

import com.example.dx_kiosk.entity.laundry_supplies.domain.dto.LaundrySuppliesDTO;
import com.example.dx_kiosk.entity.laundry_supplies.domain.dto.LaundrySuppliesDetailDTO;
import com.example.dx_kiosk.entity.laundry_supplies.service.LaundrySuppliesService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/laundrySupplies")
@RequiredArgsConstructor
public class LaundrySuppliesController {

    private final LaundrySuppliesService laundrySuppliesService;

    @GetMapping("/get/all")
    @Operation(summary = "Get LaundrySuppliesList", description = "모든 세탁용품 리스트 가져오기")
    public ResponseEntity<List<LaundrySuppliesDTO>> getLaundrySuppliesList() {
        log.info("getLaundrySupplierList");
        return new ResponseEntity<>(laundrySuppliesService.getLaundrySuppliesList(), HttpStatus.OK);
    }

    @GetMapping("/get/one/laundrySuppliesId")
    @Operation(summary = "Get LaundrySuppliesDetail By LaundrySuppliesId", description = "선택한 세탁용품 가져오기")
    public ResponseEntity<LaundrySuppliesDetailDTO> getLaundrySuppliesByLaundrySuppliesId(@RequestParam Long laundrySuppliesId,
                                                                                          @RequestParam Long storeId) {
        log.info("getLaundrySuppliesByLaundrySuppliesId : laundrySuppliesId = {}, storeId = {}", laundrySuppliesId, storeId);
        return new ResponseEntity<>(laundrySuppliesService.getLaundrySuppliesByLaundrySuppliesId(laundrySuppliesId, storeId), HttpStatus.OK);
    }


}
