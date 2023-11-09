package com.org.rs.repository;

import com.org.rs.persistance.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {

    Optional<Orders> findByCorrelationId(String id);
}
