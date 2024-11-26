package com.example.dx_kiosk.entity.shop_cart.service;

import com.example.dx_kiosk.entity.shop_cart.domain.ShopCartDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ShopCartService {

    @Transactional
    public Void buy(ShopCartDTO shopCart) {

        // 파이어베이스 연동 부분
        return null;
    }
}
