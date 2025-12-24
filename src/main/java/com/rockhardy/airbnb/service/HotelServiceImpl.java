package com.rockhardy.airbnb.service;

import com.rockhardy.airbnb.dto.HotelDto;
import com.rockhardy.airbnb.entity.Hotel;
import com.rockhardy.airbnb.exception.ResourceNotFoundException;
import com.rockhardy.airbnb.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService{
    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;
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
        Hotel hotel= hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with Id: " + id));
        return modelMapper.map(hotel,HotelDto.class);
    }

    @Override
    public HotelDto updateHotelById(Long id,HotelDto hotelDto) {
        log.info("updagting the hotel by Id: {}", id);
        Hotel hotel= hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with Id: " + id));
        modelMapper.map(hotelDto,hotel);
        hotel.setId(id);
        hotel=hotelRepository.save(hotel);
        return modelMapper.map(hotel,HotelDto.class);
    }

    @Override
    public void deleteHotelById(Long id) {
        boolean exist= hotelRepository.existsById(id);
        if(!exist) throw new ResourceNotFoundException("Hotel not found with Id: " + id);
        hotelRepository.deleteById(id);
        return ;
    }

    @Override
    public void activatehotel(Long id) {
        log.info("Activating the hotel with id: {}" ,id);
        Hotel hotel=hotelRepository
                .findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Hotel with this hotel Id: does not exist"+id));
        hotel.setActive(true);
    }

}
