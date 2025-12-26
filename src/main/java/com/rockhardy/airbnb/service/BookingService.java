package com.rockhardy.airbnb.service;


import com.rockhardy.airbnb.dto.BookingDto;
import com.rockhardy.airbnb.dto.BookingRequest;
import com.rockhardy.airbnb.dto.GuestDto;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface BookingService {

    BookingDto initializeBooking(BookingRequest bookingRequest) ;

    BookingDto addGuests(Long bookingId, List<GuestDto> guestDtoList);
}
