
package com.temple.tdb_vazhipadu_backend.service;

import com.temple.tdb_vazhipadu_backend.entity.TempleOrder;
import com.temple.tdb_vazhipadu_backend.repository.TempleOrderRepository;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderSearchService {

    private static final int MAX_PAGE_SIZE = 100;

    private static final Set<String> ALLOWED_SORT_FIELDS =
            Set.of("createdAt", "totalAmount", "status", "customerName");

    private final TempleOrderRepository orderRepository;

    public OrderSearchService(TempleOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional(readOnly = true)
    public Page<TempleOrder> search(
            String status,
            String phone,
            LocalDateTime fromDate,
            LocalDateTime toDate,
            int page,
            int size,
            String sortBy,
            String direction) {

        if (page < 0) {
            throw new IllegalArgumentException("Page must be zero or greater");
        }

        if (size < 1 || size > MAX_PAGE_SIZE) {
            throw new IllegalArgumentException(
                    "Page size must be between 1 and 100");
        }

        if (fromDate != null && toDate != null
                && !fromDate.isBefore(toDate)) {
            throw new IllegalArgumentException(
                    "fromDate must be earlier than toDate");
        }

        if (sortBy == null || !ALLOWED_SORT_FIELDS.contains(sortBy)) {
            throw new IllegalArgumentException("Unsupported sort field");
        }

        Sort.Direction sortDirection;
        try {
            sortDirection = Sort.Direction.fromString(
                    direction == null ? "DESC" : direction);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(
                    "Direction must be ASC or DESC");
        }

        // id is the tie-breaker, making the ordering deterministic.
        Sort sort = Sort.by(sortDirection, sortBy)
                .and(Sort.by(Sort.Direction.DESC, "id"));

        Pageable pageable = PageRequest.of(page, size, sort);

        return orderRepository.searchOrders(
                normalize(status),
                normalize(phone),
                fromDate,
                toDate,
                pageable);
    }

    private String normalize(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
