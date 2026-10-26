
package com.temple.tdb_vazhipadu_backend.controller;

import com.temple.tdb_vazhipadu_backend.entity.TempleOrder;
import com.temple.tdb_vazhipadu_backend.service.OrderSearchService;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderSearchController {

    private final OrderSearchService orderSearchService;

    public OrderSearchController(OrderSearchService orderSearchService) {
        this.orderSearchService = orderSearchService;
    }

    @GetMapping
    public Page<TempleOrder> searchOrders(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime fromDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime toDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction) {

        return orderSearchService.search(
                status, phone, fromDate, toDate,
                page, size, sortBy, direction);
    }
}
