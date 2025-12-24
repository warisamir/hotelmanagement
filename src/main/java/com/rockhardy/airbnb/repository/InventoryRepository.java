package com.rockhardy.airbnb.repository;

import com.rockhardy.airbnb.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {
}
