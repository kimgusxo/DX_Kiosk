package com.example.dx_kiosk.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/input")
@RequiredArgsConstructor
public class UtilsController {

    private final DataInputUtils dataInputUtils;

    @GetMapping("/data")
    public ResponseEntity<Void> inputData() {
        dataInputUtils.loadCsvToRedis("C:\\Users\\lenovo\\Downloads\\241126_data_csv_utf-8\\241126_data_csv_utf-8\\meal_kit_tb.csv", "meal_kit");
        dataInputUtils.loadCsvToRedis("C:\\Users\\lenovo\\Downloads\\241126_data_csv_utf-8\\241126_data_csv_utf-8\\laundry_supplies_tb.csv", "laundry_supplies");
        dataInputUtils.loadCsvToRedis("C:\\Users\\lenovo\\Downloads\\241126_data_csv_utf-8\\241126_data_csv_utf-8\\laundry_ticket_tb.csv", "laundry_ticket");
        return ResponseEntity.ok().build();
    }

}
