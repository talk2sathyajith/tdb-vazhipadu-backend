package com.temple.tdb_vazhipadu_backend.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "offering_daily_usage")
@IdClass(OfferingDailyUsageId.class)
public class OfferingDailyUsage {

    @Id
    @Column(name = "offering_id")
    private Long offeringId;

    @Id
    @Column(name = "booking_date")
    private LocalDate bookingDate;

    @Column(name = "booked_quantity", nullable = false)
    private int bookedQuantity;

    protected OfferingDailyUsage() {
    }

    public int getBookedQuantity() {
        return bookedQuantity;
    }
}