package com.example.dx_kiosk.entity.mealkit.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "mealkit", timeToLive = 1209600)
public class MealKit {

    @Id
    private Long mealKitId;

    private String mealKitName;
    private Integer mealKitPrice;
    private String mealKitClassification;
    private String mealKitFoodClassification;
    private String mealKitCompanyName;
    private String mealKitDescription;
    private String mealKitUrl;

}
