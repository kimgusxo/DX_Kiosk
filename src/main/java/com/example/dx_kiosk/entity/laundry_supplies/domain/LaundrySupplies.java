package com.example.dx_kiosk.entity.laundry_supplies.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "laundry_supplies", timeToLive = 1209600)
public class LaundrySupplies {

    @Id
    private Long laundrySuppliesId;

    private String laundrySuppliesName;
    private Integer laundrySuppliesPrice;
    private String laundrySuppliesClassification;
    private String laundrySuppliesCompanyName;
    private String laundrySuppliesDescription;
    private String laundrySuppliesUrl;

}
