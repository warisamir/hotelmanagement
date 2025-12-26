package com.rockhardy.airbnb.service;

import com.rockhardy.airbnb.dto.HotelDto;
import com.rockhardy.airbnb.dto.HotelInfoDto;
import com.rockhardy.airbnb.dto.RoomDto;
import com.rockhardy.airbnb.entity.Hotel;
import com.rockhardy.airbnb.entity.Room;
import com.rockhardy.airbnb.exception.ResourceNotFoundException;
import com.rockhardy.airbnb.repository.HotelRepository;
import com.rockhardy.airbnb.repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class HotelServiceImpl implements HotelService{
    HotelRepository hotelRepository;
    ModelMapper modelMapper;
    InventoryService inventoryService;
    RoomRepository roomRepository;
    @Override
    public HotelDto createHotel(HotelDto hotelDto) {
         log.info("Create a new hotel with name: {}",hotelDto.getName());
         Hotel hotel=modelMapper.map(hotelDto,Hotel.class);
         hotel.setActive(false);
         hotel= hotelRepository.save(hotel);
         log.info("Created a new hotel with ID: {}",hotelDto.getId());
        return modelMapper.map(hotel,HotelDto.class);
    }

    @Override
    public HotelDto getHotelById(Long id) {
        log.info("Getting the hotel by Id: {}", id);
        boolean exist=hotelRepository.existsById(id);
        Hotel hotel= hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with Id: " + id));
        return modelMapper.map(hotel,HotelDto.class);
    }

    @Override
    public HotelDto updateHotelById(Long id,HotelDto hotelDto) {
        log.info("updating the hotel by Id: {}", id);
        Hotel hotel= hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with Id: " + id));
        modelMapper.map(hotelDto,hotel);
        hotel.setId(id);
        hotel=hotelRepository.save(hotel);
        return modelMapper.map(hotel,HotelDto.class);
    }

    @Override
    @Transactional
    public void deleteHotelById(Long id) {
        Hotel hotel=hotelRepository
                .findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Hotel with this hotel Id: does not exist"+id));
        for(Room room:hotel.getRooms()) {
            inventoryService.deleteAllInventories(room);
            roomRepository.deleteById(room.getId()) ;
        }
        hotelRepository.deleteById(id);
        return ;
    }

    @Override
    @Transactional
    public void activatehotel(Long id) {
        log.info("Activating the hotel with id: {}" ,id);
        Hotel hotel=hotelRepository
                .findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Hotel with this hotel Id: does not exist"+id));
        hotel.setActive(true);
        for(Room room:hotel.getRooms()){
            inventoryService.initializedRoomforAYear(room);
        }
    }

    @Override
    public HotelInfoDto getHotelInfoById(Long hotelId) {
        Hotel hotel=hotelRepository
                .findById(hotelId)
                .orElseThrow(()->new ResourceNotFoundException("Hotel with this hotel Id: does not exist"+hotelId));
        List<RoomDto> rooms= hotel.getRooms()
                .stream()
                .map((element) -> modelMapper.map(element, RoomDto.class))
                .toList();
        return new HotelInfoDto (modelMapper.map(hotel,HotelDto.class),rooms)    ;
    }

}
