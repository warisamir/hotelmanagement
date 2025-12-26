package com.rockhardy.airbnb.dto;

import com.rockhardy.airbnb.entity.Booking;
import com.rockhardy.airbnb.entity.Hotel;
import com.rockhardy.airbnb.entity.Room;
import com.rockhardy.airbnb.entity.User;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingRequest {
    Long hotelId;
    Long roomId;
     Integer roomsCount;
     LocalDate checkInDate;
     LocalDate checkOutDate;
}
