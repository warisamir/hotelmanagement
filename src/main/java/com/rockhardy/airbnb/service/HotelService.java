package com.rockhardy.airbnb.service;

import com.rockhardy.airbnb.dto.HotelDto;
import com.rockhardy.airbnb.dto.HotelInfoDto;
import com.rockhardy.airbnb.entity.Hotel;
import org.jspecify.annotations.Nullable;

public interface HotelService{
    HotelDto createHotel(HotelDto hotelDto);
    HotelDto getHotelById(Long id);
    HotelDto updateHotelById(Long id,HotelDto hotelDto);
    void  deleteHotelById(Long id);
    void activatehotel(Long id);
    HotelInfoDto getHotelInfoById(Long hotelId);
}
