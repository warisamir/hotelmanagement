package com.rockhardy.airbnb.service;

import com.rockhardy.airbnb.dto.RoomDto;

import java.util.List;

public interface RoomService{
    RoomDto createNewRoom(RoomDto roomDto);
    List<RoomDto> getAllRoomsById(Long hotelId);
    RoomDto getRoomById(Long roomId);
    void deleteRoomById(Long roomId);
}
