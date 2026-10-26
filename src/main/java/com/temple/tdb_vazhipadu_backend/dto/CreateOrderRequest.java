package com.temple.tdb_vazhipadu_backend.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record CreateOrderRequest(
    @NotNull
    @Positive
    Long templeId,

    @NotBlank
    @Size(max = 150)
    String devoteeName,

    @NotBlank
    @Pattern(regexp = "[0-9+() -]{7,20}")
    String phone,

    @NotBlank
    @Size(max = 100)
    String nakshatra,

    @NotNull
    LocalDate bookingDate,

    @NotEmpty
    List<@Valid OrderItemRequest> items
) {
    public record OrderItemRequest(
        @NotNull @Positive Long offeringId,
        @NotNull @Positive Integer quantity
    ) {}
}