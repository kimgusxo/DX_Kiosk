package com.example.dx_kiosk.entity.laundry_ticket.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "laundry_ticket", timeToLive = 1209600)
public class LaundryTicket {

    @Id
    private Long laundryTicketId;

    private String laundryTicketName;
    private String laundryTicketClassification;
    private String laundryTicketPrice;
    private String laundryTicketUrl;

}
