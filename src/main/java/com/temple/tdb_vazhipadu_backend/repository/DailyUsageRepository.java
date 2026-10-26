package com.temple.tdb_vazhipadu_backend.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DailyUsageRepository {

    private final JdbcTemplate jdbcTemplate;

    public DailyUsageRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean reserve(
            Long offeringId,
            LocalDate bookingDate,
            int quantity,
            int dailyLimit) {

        String sql = """
            INSERT INTO offering_daily_usage
                (offering_id, booking_date, booked_quantity)
            VALUES (?, ?, ?)
            ON CONFLICT (offering_id, booking_date)
            DO UPDATE SET
                booked_quantity =
                    offering_daily_usage.booked_quantity
                    + EXCLUDED.booked_quantity
            WHERE offering_daily_usage.booked_quantity
                    + EXCLUDED.booked_quantity <= ?
            RETURNING booked_quantity
            """;

        List<Integer> result = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> rs.getInt("booked_quantity"),
                offeringId,
                bookingDate,
                quantity,
                dailyLimit
        );

        return !result.isEmpty();
    }
}