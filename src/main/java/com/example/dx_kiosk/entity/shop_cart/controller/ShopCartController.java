package com.example.dx_kiosk.entity.shop_cart.controller;

import com.example.dx_kiosk.entity.shop_cart.service.ShopCartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/shopCart")
@RequiredArgsConstructor
public class ShopCartController {

    private final ShopCartService shopCartService;



}
