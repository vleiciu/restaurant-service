package com.org.rs.RestaurantService.repository;

import com.org.rs.RestaurantService.persistance.Restaurants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantsRepository extends JpaRepository<Restaurants, Integer> {
}
