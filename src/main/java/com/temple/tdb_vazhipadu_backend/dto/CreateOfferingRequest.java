package com.temple.tdb_vazhipadu_backend.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.*;

public record CreateOfferingRequest(
    @NotBlank
    @Size(max = 150)
    String name,

    @NotNull
    @DecimalMin(value = "0.01")
    @Digits(integer = 10, fraction = 2)
    BigDecimal price,

    Boolean active
) {}