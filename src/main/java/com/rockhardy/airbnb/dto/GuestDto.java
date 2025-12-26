package com.rockhardy.airbnb.dto;

import com.rockhardy.airbnb.entity.User;
import com.rockhardy.airbnb.entity.enums.Gender;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class GuestDto {
     private Long id;
     private User user;
     private String name;
     private Gender gender;
     private  Integer age;
}
