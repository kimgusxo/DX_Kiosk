package com.example.dx_kiosk.mealkit.controller;

import com.example.dx_kiosk.mealkit.service.MealKitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/mealKit")
@RequiredArgsConstructor
public class MealKitController {

    private final MealKitService mealKitService;




}
