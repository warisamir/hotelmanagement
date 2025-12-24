package com.rockhardy.airbnb.service;

import com.rockhardy.airbnb.dto.RoomDto;
import com.rockhardy.airbnb.entity.Hotel;
import com.rockhardy.airbnb.entity.Room;
import com.rockhardy.airbnb.exception.ResourceNotFoundException;
import com.rockhardy.airbnb.repository.HotelRepository;
import com.rockhardy.airbnb.repository.InventoryRepository;
import com.rockhardy.airbnb.repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class RoomServiceImpl implements RoomService{
    private final InventoryRepository inventoryRepository;
    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;
    private final InventoryService inventoryService;
    private final HotelRepository hotelRepository;

    @Override
    @Transactional
    public RoomDto createNewRoom(Long hotelId, RoomDto roomDto) {
        log.info("Creating a new room with hotel id {}",hotelId);
         Hotel hotel=hotelRepository.
                 findById(hotelId)
                 .orElseThrow(()->new ResourceNotFoundException("Hotel not found with hotelid"+hotelId));
         Room room=modelMapper.map(roomDto,Room.class);
         room.setHotel(hotel);
         room=roomRepository.save(room);
         if(hotel.getActive()){
             inventoryService.initializedRoomforAYear(room);
         }
        return modelMapper.map(room,RoomDto.class);
    }

    @Override
    public List<RoomDto> getAllRoomsById(Long hotelId) {
        log.info("Getting all room with hotel id {}",hotelId);
        Hotel hotel=hotelRepository.
                findById(hotelId)
                .orElseThrow(()->new ResourceNotFoundException("Hotel not found with hotelid"+hotelId));
        return (hotel.getRooms()
                .stream()
                .map((element) -> modelMapper.map(element, RoomDto.class))
                .collect(Collectors.toList()));
    }

    @Override
    public RoomDto getRoomById(Long roomId) {
        log.info("Getting the room with room id {}",roomId);
        Room room=roomRepository.
                findById(roomId)
                .orElseThrow(()->new ResourceNotFoundException("room not found with roomid "+roomId));
        return modelMapper.map(room,RoomDto.class);
    }

    @Override
    @Transactional
    public void deleteRoomById(Long roomId) {
        log.info("Deleting the room with room id {}",roomId);
        Room room=roomRepository.
                findById(roomId)
                .orElseThrow(()->new ResourceNotFoundException("room not found with roomid "+roomId));
        roomRepository.deleteById(roomId);
        inventoryService.deleteFutureInventories(room);
        return;
    }
}
