package com.temple.tdb_vazhipadu_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.temple.tdb_vazhipadu_backend.entity.TempleOrder;

public interface OrderRepository extends JpaRepository<TempleOrder, Long> {

}
