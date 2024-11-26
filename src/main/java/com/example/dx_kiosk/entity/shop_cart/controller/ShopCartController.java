package com.example.dx_kiosk.entity.shop_cart.controller;

import com.example.dx_kiosk.entity.shop_cart.domain.ShopCartDTO;
import com.example.dx_kiosk.entity.shop_cart.service.ShopCartService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/shopCart")
@RequiredArgsConstructor
public class ShopCartController {

    private final ShopCartService shopCartService;

    @PutMapping("/buy")
    @Operation(summary = "Buy Items", description = "물품 구매하기")
    public ResponseEntity<Void> buy(@RequestBody ShopCartDTO shopCart) {
        log.info("buy : shopCart = {}", shopCart);
        return new ResponseEntity<>(shopCartService.buy(shopCart), HttpStatus.OK);
    }

}
