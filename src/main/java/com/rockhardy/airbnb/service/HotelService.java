package com.rockhardy.airbnb.service;

import com.rockhardy.airbnb.dto.HotelDto;
import com.rockhardy.airbnb.entity.Hotel;

public interface HotelService{
    HotelDto createHotel(HotelDto hotelDto);
    HotelDto getHotelById(Long id);
    HotelDto updateHotelById(Long id,HotelDto hotelDto);
    void  deleteHotelById(Long id);
    void activatehotel(Long id);
}
