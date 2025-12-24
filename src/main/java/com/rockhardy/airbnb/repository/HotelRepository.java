package com.rockhardy.airbnb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rockhardy.airbnb.entity.Hotel;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel,Long> {
}
