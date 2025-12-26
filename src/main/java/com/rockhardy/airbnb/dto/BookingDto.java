package com.rockhardy.airbnb.dto;

import com.rockhardy.airbnb.entity.Guest;
import com.rockhardy.airbnb.entity.Hotel;
import com.rockhardy.airbnb.entity.Room;
import com.rockhardy.airbnb.entity.User;
import com.rockhardy.airbnb.entity.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
@Data

public class BookingDto {

    private Long id;
    private Integer roomsCount;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private BookingStatus bookingStatus;
    private Set<Guest> guests;
}
