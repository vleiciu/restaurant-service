package com.org.rs.RestaurantService.repository;

import com.org.rs.RestaurantService.persistance.LineItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineItemsRepository extends JpaRepository<LineItems, Integer> {
}
