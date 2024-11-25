package com.example.dx_kiosk.entity.laundry_supplies.domain.dto;

import com.example.dx_kiosk.entity.laundry_supplies.domain.LaundrySupplies;

public record LaundrySuppliesDTO(
        String laundrySuppliesId,
        String laundrySuppliesName,
        Integer laundrySuppliesPrice,
        Integer laundrySuppliesCount,
        String laundrySuppliesClassification,
        String laundrySuppliesCompanyName,
        String laundrySuppliesDescription,
        String laundrySuppliesUrl
) {
    public static LaundrySuppliesDTO from(LaundrySupplies laundrySupplies) {
        return new LaundrySuppliesDTO(
                laundrySupplies.getLaundrySuppliesId(),
                laundrySupplies.getLaundrySuppliesName(),
                laundrySupplies.getLaundrySuppliesPrice(),
                laundrySupplies.getLaundrySuppliesCount(),
                laundrySupplies.getLaundrySuppliesClassification(),
                laundrySupplies.getLaundrySuppliesCompanyName(),
                laundrySupplies.getLaundrySuppliesDescription(),
                laundrySupplies.getLaundrySuppliesUrl()
        );
    }
}
