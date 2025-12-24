package com.rockhardy.airbnb.controller;

import com.rockhardy.airbnb.dto.RoomDto;
import com.rockhardy.airbnb.entity.Room;
import com.rockhardy.airbnb.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/hotels/{hotelId}/rooms")
@RequiredArgsConstructor
@Slf4j
public class RoomAdminController {
    private final RoomService roomService;
    @PostMapping
    public ResponseEntity<RoomDto> createNewRoom(@PathVariable Long hotelId,
                                                 @RequestBody RoomDto roomDto){
         RoomDto room =roomService.createNewRoom(hotelId,roomDto);
         return  new ResponseEntity<>(room, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RoomDto>> getAllRoombyId(@PathVariable Long hotelId){
       List<RoomDto> room= roomService.getAllRoomsById(hotelId);
       return ResponseEntity.ok(room);
    }
    @GetMapping("/{roomId}")
    public ResponseEntity<RoomDto> getRoombyId(@PathVariable Long roomId){
        RoomDto room= roomService.getRoomById(roomId)   ;
        return ResponseEntity.ok(room);
    }
    @DeleteMapping("/{roomId}")
    public ResponseEntity<RoomDto> deleteById(@PathVariable Long hotelId,
                                              @PathVariable Long roomId){
        roomService.deleteRoomById(roomId);
        return ResponseEntity.noContent().build();
    }
}
