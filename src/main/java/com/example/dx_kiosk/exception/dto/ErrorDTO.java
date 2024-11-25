package com.example.dx_kiosk.exception.dto;

public record ErrorDTO(
        String errorMessage
) {
    public static ErrorDTO of(String errorMessage) {
        return new ErrorDTO(errorMessage);
    }
}
