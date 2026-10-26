
package com.temple.tdb_vazhipadu_backend.repository;

import com.temple.tdb_vazhipadu_backend.entity.TempleOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TempleOrderRepository
        extends JpaRepository<TempleOrder, Long> {

}
