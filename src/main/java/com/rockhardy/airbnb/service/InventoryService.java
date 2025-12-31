package com.rockhardy.airbnb.service;

import com.rockhardy.airbnb.dto.HotelDto;
import com.rockhardy.airbnb.dto.HotelPriceDto;
import com.rockhardy.airbnb.dto.HotelSearchRequest;
import com.rockhardy.airbnb.entity.Room;
import  org.springframework.data.domain.Page;

public interface InventoryService {
    void initializedRoomforAYear(Room room);
    void deleteAllInventories(Room room);

    Page<HotelPriceDto>  searchHotel(HotelSearchRequest hotelSearchRequest);
}
