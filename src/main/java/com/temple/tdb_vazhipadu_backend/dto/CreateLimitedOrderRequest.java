package com.temple.tdb_vazhipadu_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreateLimitedOrderRequest(
        @NotNull Long offeringId,
        @NotBlank @Size(max = 150) String devoteeName,
        @NotBlank @Size(max = 20) String phoneNumber,
        @NotNull LocalDate bookingDate,
        @Positive int quantity
) {
}