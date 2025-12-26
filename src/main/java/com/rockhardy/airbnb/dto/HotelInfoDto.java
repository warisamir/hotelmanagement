package com.rockhardy.airbnb.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HotelInfoDto {
    HotelDto hotelDto;
    List<RoomDto> rooms;

}
