package com.temple.tdb_vazhipadu_backend.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.temple.tdb_vazhipadu_backend.dto.CreateLimitedOrderRequest;
import com.temple.tdb_vazhipadu_backend.dto.OrderResponse;
import com.temple.tdb_vazhipadu_backend.entity.Offering;
import com.temple.tdb_vazhipadu_backend.entity.TempleOrder;
import com.temple.tdb_vazhipadu_backend.exception.DailyLimitExceededException;
import com.temple.tdb_vazhipadu_backend.repository.DailyUsageRepository;
import com.temple.tdb_vazhipadu_backend.repository.OfferingRepository;
import com.temple.tdb_vazhipadu_backend.repository.TempleOrderRepository;
@Service
public class DailyLimitService {

    private final OfferingRepository offeringRepository;
    private final TempleOrderRepository orderRepository;
    private final DailyUsageRepository usageRepository;

    public DailyLimitService(
            OfferingRepository offeringRepository,
            TempleOrderRepository orderRepository,
            DailyUsageRepository usageRepository) {

        this.offeringRepository = offeringRepository;
        this.orderRepository = orderRepository;
        this.usageRepository = usageRepository;
    }

    @Transactional
    public OrderResponse createOrder(CreateLimitedOrderRequest request) {

        if (request.quantity() <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than zero");
        }

        Offering offering = offeringRepository
                .findById(request.offeringId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Offering not found"));

        if (!offering.isActive()) {
            throw new IllegalArgumentException(
                    "Offering is inactive");
        }

        Integer dailyLimit = offering.getDailyLimit();

        if (dailyLimit != null) {

            if (request.quantity() > dailyLimit) {
                throw new DailyLimitExceededException(
                        "Requested quantity exceeds the daily limit");
            }

            boolean reserved = usageRepository.reserve(
                    offering.getId(),
                    request.bookingDate(),
                    request.quantity(),
                    dailyLimit
            );

            if (!reserved) {
                throw new DailyLimitExceededException(
                        "Daily limit exceeded for this offering and date");
            }
        }

        BigDecimal total = offering.getPrice()
                .multiply(BigDecimal.valueOf(request.quantity()));

        TempleOrder order = new TempleOrder(
                offering,
                request.devoteeName(),
                request.phoneNumber(),
                request.bookingDate(),
                request.quantity(),
                total
        );

        TempleOrder savedOrder = orderRepository.saveAndFlush(order);

        return null;
    }
}