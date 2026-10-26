
package com.temple.tdb_vazhipadu_backend.repository;

import com.temple.tdb_vazhipadu_backend.entity.TempleOrder;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TempleOrderRepository
        extends JpaRepository<TempleOrder, Long> {

    @Query("""
        SELECT o FROM TempleOrder o
        WHERE (:status IS NULL OR o.status = :status)
          AND (:phone IS NULL OR o.customerPhone = :phone)
          AND (:fromDate IS NULL OR o.createdAt >= :fromDate)
          AND (:toDate IS NULL OR o.createdAt < :toDate)
        """)
    Page<TempleOrder> searchOrders(
            @Param("status") String status,
            @Param("phone") String phone,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate,
            Pageable pageable
    );
}
