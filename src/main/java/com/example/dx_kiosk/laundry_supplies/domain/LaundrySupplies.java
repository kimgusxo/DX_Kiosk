package com.example.dx_kiosk.laundry_supplies.domain;

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
    private String laundrySuppliesId;

    private String laundrySuppliesName;
    private Integer laundrySuppliesPrice;
    private Integer laundrySuppliesCount;
    private String laundrySuppliesClassification;
    private String laundrySuppliesCompanyName;
    private String laundrySuppliesDescription;
    private String laundrySuppliesUrl;

}
