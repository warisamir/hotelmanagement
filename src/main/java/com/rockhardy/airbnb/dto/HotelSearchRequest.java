package com.rockhardy.airbnb.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HotelSearchRequest {
    String city;
    LocalDate startDate;
    LocalDate endDate;
    Integer roomCount;
    Integer page=0;
    Integer size=10;
}
