package com.temple.tdb_vazhipadu_backend.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class OfferingDailyUsageId implements Serializable {

    private Long offeringId;
    private LocalDate bookingDate;

    public OfferingDailyUsageId() {
    }

    public OfferingDailyUsageId(Long offeringId, LocalDate bookingDate) {
        this.offeringId = offeringId;
        this.bookingDate = bookingDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof OfferingDailyUsageId other)) return false;

        return Objects.equals(offeringId, other.offeringId)
                && Objects.equals(bookingDate, other.bookingDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(offeringId, bookingDate);
    }
}