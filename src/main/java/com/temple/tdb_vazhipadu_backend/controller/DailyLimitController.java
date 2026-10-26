package com.temple.tdb_vazhipadu_backend.controller;

import com.temple.tdb_vazhipadu_backend.dto.CreateLimitedOrderRequest;
import com.temple.tdb_vazhipadu_backend.dto.OrderResponse;
import com.temple.tdb_vazhipadu_backend.service.DailyLimitService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class DailyLimitController {

    private final DailyLimitService dailyLimitService;

    public DailyLimitController(DailyLimitService dailyLimitService) {
        this.dailyLimitService = dailyLimitService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(
            @Valid @RequestBody CreateLimitedOrderRequest request) {

        return dailyLimitService.createOrder(request);
    }
}