package com.org.rs.RestaurantService.repository;

import com.org.rs.RestaurantService.persistance.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {

    @Query("SELECT o FROM ORDERS o WHERE o.RESTAURANT_ID = ?1")
    List<Orders> findAllOrdersByRestaurantId(Integer id);

    Optional<Orders> findByCorrelationId(String id);
}
