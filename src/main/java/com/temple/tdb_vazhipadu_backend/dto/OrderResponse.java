
package com.temple.tdb_vazhipadu_backend.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record OrderResponse(
        Long id,
        Long templeId,
        String devoteeName,
        String phone,
        String nakshatra,
        LocalDate bookingDate,
        String status,
        BigDecimal totalAmount,
        Instant createdAt,
        List<OrderItemResponse> items
) {

   

	public record OrderItemResponse(
            Long offeringId,
            String offeringName,
            int quantity,
            BigDecimal unitPrice,
            BigDecimal lineTotal
    ) {
    }
}
