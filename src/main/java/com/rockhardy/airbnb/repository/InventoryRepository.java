package com.rockhardy.airbnb.repository;

import com.rockhardy.airbnb.entity.Inventory;
import com.rockhardy.airbnb.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {
    void deleteByDateAfterAndRoom(LocalDate date, Room room);
}
