package com.org.rs.repository;

import com.org.rs.persistance.LineItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineItemsRepository extends JpaRepository<LineItems, Integer> {
}
