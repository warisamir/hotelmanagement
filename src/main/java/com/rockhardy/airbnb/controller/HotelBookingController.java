package com.rockhardy.airbnb.controller;

import com.rockhardy.airbnb.dto.BookingDto;
import com.rockhardy.airbnb.dto.BookingRequest;
import com.rockhardy.airbnb.dto.GuestDto;
import com.rockhardy.airbnb.service.BookingService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequestMapping("/bookings")
public class HotelBookingController {
    BookingService bookingService;
    @PostMapping("/init")
    public ResponseEntity<BookingDto>initializedBooking(@RequestBody BookingRequest bookingRequest){
        return ResponseEntity.ok(bookingService.initializeBooking(bookingRequest));
    }
    @PostMapping("/{bookingId}/addGuests")
    public ResponseEntity<BookingDto> addGuests(@PathVariable Long bookingId,@RequestBody List<GuestDto>guestDtoList){
        return ResponseEntity.ok(bookingService.addGuests(bookingId, guestDtoList));
    }
}
