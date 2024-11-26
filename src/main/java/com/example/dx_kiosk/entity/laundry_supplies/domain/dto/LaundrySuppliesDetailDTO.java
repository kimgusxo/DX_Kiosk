package com.example.dx_kiosk.entity.laundry_supplies.domain.dto;

public record LaundrySuppliesDetailDTO(
        Long laundrySuppliesId,
        Long storeId,
        String laundrySuppliesName,
        Integer laundrySuppliesPrice,
        Integer laundrySuppliesCount,
        String laundrySuppliesClassification,
        String laundrySuppliesCompanyName,
        String laundrySuppliesDescription,
        String laundrySuppliesUrl
) {

}
