package com.example.dx_kiosk.entity.mealkit.controller;

import com.example.dx_kiosk.entity.mealkit.domain.dto.MealKitDTO;
import com.example.dx_kiosk.entity.mealkit.domain.dto.MealKitDetailDTO;
import com.example.dx_kiosk.entity.mealkit.service.MealKitService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mealKit")
@RequiredArgsConstructor
public class MealKitController {

    private final MealKitService mealKitService;

    @GetMapping("/get/all")
    @Operation(summary = "Get MealKitList", description = "모든 밀키트 리스트 가져오기")
    public ResponseEntity<List<MealKitDTO>> getMealKitList() {
        log.info("getMealKitList");
        return new ResponseEntity<>(mealKitService.getMealKitList(), HttpStatus.OK);
    }

    @GetMapping("/get/one/{mealKitId}")
    @Operation(summary = "Get MealKitDetailDTO By MealKitId", description = "선택한 밀키트 가져오기")
    public ResponseEntity<MealKitDetailDTO> getMealKitByMealKitId(@PathVariable String mealKitId) {
        log.info("getMealKitByMealKitId : mealKitId = {}", mealKitId);
        return new ResponseEntity<>(mealKitService.getMealKitByMealKitId(mealKitId), HttpStatus.OK);
    }

    @GetMapping("/get/list/{mealKitClassification}")
    @Operation(summary = "Get MealKitList By MealKitClassification", description = "분류 별 밀키트 리스트 가져오기")
    public ResponseEntity<List<MealKitDTO>> getMealKitListByMealKitClassification(@PathVariable String mealKitClassification) {
        log.info("getMealKitListByMealKitClassification : mealKitClassification = {}", mealKitClassification);
        return new ResponseEntity<>(mealKitService.getMealKitListByMealKitClassification(mealKitClassification), HttpStatus.OK);
    }

}
