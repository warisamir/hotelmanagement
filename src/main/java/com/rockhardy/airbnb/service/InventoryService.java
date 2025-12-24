package com.rockhardy.airbnb.service;

import com.rockhardy.airbnb.entity.Room;

public interface InventoryService {
    void initializedRoomforAYear(Room room);
    void deleteFutureInventories(Room room);
}
