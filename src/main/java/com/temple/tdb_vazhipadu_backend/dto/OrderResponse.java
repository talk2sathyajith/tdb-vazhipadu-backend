package com.temple.tdb_vazhipadu_backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
    Long id,
    Long templeId,
    String devoteeName,
    String phone,
    String nakshatra,
    LocalDate bookingDate,
    String status,
    BigDecimal totalAmount,
    LocalDateTime createdAt,
    List<OrderItemResponse> items
) {
    public record OrderItemResponse(
        Long offeringId,
        String offeringName,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal lineTotal
    ) {}
}