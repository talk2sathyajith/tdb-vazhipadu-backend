package com.temple.tdb_vazhipadu_backend.dto;

import java.math.BigDecimal;

public record OfferingResponse(
    Long id,
    String name,
    BigDecimal price,
    boolean active
) {}