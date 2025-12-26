package com.rockhardy.airbnb.service;

import com.rockhardy.airbnb.dto.BookingDto;
import com.rockhardy.airbnb.dto.BookingRequest;
import com.rockhardy.airbnb.dto.GuestDto;
import com.rockhardy.airbnb.entity.*;
import com.rockhardy.airbnb.entity.enums.BookingStatus;
import com.rockhardy.airbnb.exception.ResourceNotFoundException;
import com.rockhardy.airbnb.repository.*;
import jakarta.transaction.Transactional;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)

public class BookingServiceImpl implements BookingService{
    private final GuestRepository guestRepository;
    InventoryRepository inventoryRepository;
    BookingRepository bookingRepository;
    HotelRepository hotelRepository;
    RoomRepository roomRepository;
    ModelMapper modelMapper;

    @Override
    @Transactional
    public BookingDto initializeBooking(BookingRequest bookingRequest) {
        log.info("Started initializing all the bookings ");
         Hotel hotel=hotelRepository
                 .findById(bookingRequest.getHotelId())
                 .orElseThrow(()->{
                     log.error("Hotel not found for this hotelID: {}",bookingRequest.getHotelId());
                     throw  new ResourceNotFoundException("Hotel not found for this hotel Id");
                 });
        Room room=roomRepository
                .findById(bookingRequest.getRoomId())
                .orElseThrow(()->{
                    log.error("Hotel not found for this hotelID: {}",bookingRequest.getRoomId());
                    throw  new ResourceNotFoundException("Hotel not found for this room Id");
                });
        List<Inventory>inventories=inventoryRepository.findAndlockAvailableInventory(hotel.getId(),
                bookingRequest.getCheckInDate(),bookingRequest.getCheckOutDate(),
                bookingRequest.getRoomsCount());
        long daysCount= ChronoUnit.DAYS.between(bookingRequest.getCheckInDate(),bookingRequest.getCheckOutDate())+1;
        if(inventories.size()!=daysCount){
            throw new IllegalStateException("hey room is not availble anymore");
        }
        for(Inventory inventory:inventories){
            inventory.setReservedCount(inventory.getBookedCount()+ bookingRequest.getRoomsCount());
        }
        inventoryRepository.saveAll(inventories);
        log.info("Creating the booking");
        Booking booking = Booking.builder()
                .bookingStatus(BookingStatus.RESERVED)
                .hotel(hotel)
                .room(room)
                .checkInDate(bookingRequest.getCheckInDate())
                .checkOutDate(bookingRequest.getCheckOutDate())
                .roomsCount(bookingRequest.getRoomsCount())
                .amount(BigDecimal.TEN)
                .user(getCurrentUser())
                .build();
        booking=bookingRepository.save(booking);
        return modelMapper.map(booking,BookingDto.class);
    }

    @Override
    @Transactional
    public BookingDto addGuests(Long bookingId, List<GuestDto> guestDtoList) {
        log.info("Adding guests for booking with id: {}",bookingId);
        Booking booking=bookingRepository
                .findById(bookingId)
                .orElseThrow(()->{
                    log.error("Hotel not found for this hotelID: {}",bookingId);
                    throw  new ResourceNotFoundException("Hotel not found for this hotel Id");
                });
        if(hasBookingExpired(booking)){
            log.error("Booking has expired as it exceeds 10mins with bookingid: {}",bookingId);
            throw new IllegalStateException("Booking has expired");
        }
        if(booking.getBookingStatus()!=BookingStatus.RESERVED){
            log.error("Booking is not yet resered state {}",booking.getBookingStatus());
            throw  new IllegalStateException("Booking is not reserved state");
        }
        for(GuestDto guestDto:guestDtoList){
            Guest guest= modelMapper.map(guestDto,Guest.class);
            guest.setUser(getCurrentUser());
            guest=guestRepository.save(guest);
            booking.getGuests().add(guest);
        }
        booking.setBookingStatus(BookingStatus.GUESTS_ADDED);
        bookingRepository.save(booking);
        return modelMapper.map(booking,BookingDto.class);
    }
    public boolean hasBookingExpired(Booking booking){
        return booking.getCreatedAt().plusMinutes(10).isBefore(LocalDateTime.now());
    }
    public User getCurrentUser(){
        User user=new User();
        user.setId(1L);
        return user;
    }
}
