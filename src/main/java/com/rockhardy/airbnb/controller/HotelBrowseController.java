package com.rockhardy.airbnb.controller;

import com.rockhardy.airbnb.dto.HotelDto;
import com.rockhardy.airbnb.dto.HotelInfoDto;
import com.rockhardy.airbnb.dto.HotelPriceDto;
import com.rockhardy.airbnb.dto.HotelSearchRequest;
import com.rockhardy.airbnb.service.HotelService;
import com.rockhardy.airbnb.service.InventoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequestMapping("/hotels")
public class HotelBrowseController {
    InventoryService inventoryService;
    HotelService hotelService;
    @GetMapping("/search")
    public ResponseEntity<Page<HotelPriceDto>> searchHotels(@RequestBody HotelSearchRequest hotelSearchRequest){
        var page =inventoryService.searchHotel(hotelSearchRequest);
        return ResponseEntity.ok(page);
    }
    @GetMapping("/{hotelId}/info")
    public ResponseEntity<HotelInfoDto> getHotelInfo(@PathVariable Long hotelId){
        return ResponseEntity.ok(hotelService.getHotelInfoById(hotelId));
    }
}
