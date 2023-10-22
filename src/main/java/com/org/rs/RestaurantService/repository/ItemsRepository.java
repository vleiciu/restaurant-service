package com.org.rs.RestaurantService.repository;

import com.org.rs.RestaurantService.persistance.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemsRepository extends JpaRepository<Items, Integer> {
}
