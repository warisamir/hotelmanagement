package com.rockhardy.airbnb.service;

import com.rockhardy.airbnb.entity.Hotel;
import com.rockhardy.airbnb.entity.HotelMinPrice;
import com.rockhardy.airbnb.entity.Inventory;
import com.rockhardy.airbnb.repository.HotelMinRepository;
import com.rockhardy.airbnb.repository.HotelRepository;
import com.rockhardy.airbnb.repository.InventoryRepository;
import com.rockhardy.airbnb.strategy.PricingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class PricingUpdateService {
    //Scheduler to update the inventory and hoteladmin table every price
    HotelMinRepository hotelMinRepository;
    HotelRepository hotelRepository;
    InventoryRepository inventoryRepository;
    PricingService pricingService;

    @Scheduled(cron="*/5 0 * * * *")
    public void updatePrice(){
        int page =0;
        int batchSize=108;
        while (true){
            Page<Hotel> hotelPage= hotelRepository.findAll(PageRequest.of(page,batchSize));
            if(hotelPage.isEmpty()){
                break;
            }
            hotelPage.getContent().forEach(this::updateHotelPrice);
            page++;
        }
    }
    private void updateHotelPrice(Hotel hotel) {
        log.info("Updating hotel prices for hotel {}",hotel.getId());
        LocalDate startDate= LocalDate.now();
        LocalDate endDate= LocalDate.now().plusYears(1);
        List<Inventory> inventoryList= inventoryRepository.findByHotelAndDateBetween(hotel, startDate,endDate);
        updateInventoryPrice(inventoryList);
        updateHotelMinPrice(hotel,inventoryList,startDate,endDate);
    }
    private void updateHotelMinPrice(Hotel hotel, List<Inventory> inventoryList, LocalDate startDate, LocalDate endDate) {
        Map<LocalDate,BigDecimal>dailyMinPrice=inventoryList.stream()
                .collect(Collectors.groupingBy(
                        Inventory::getDate,
                        Collectors.mapping(Inventory::getPrice,Collectors.minBy(Comparator.naturalOrder()))
                        )).entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,e->e.getValue().orElse(BigDecimal.ZERO)));
        List<HotelMinPrice>hotelPrices= new ArrayList<>();
        dailyMinPrice.forEach((date,price)->{
            HotelMinPrice hotelPrice=hotelMinRepository.findByHotelAndDate(
                    hotel,date).orElse(new HotelMinPrice(hotel,date));
            hotelPrice.setPrice(price);
            hotelPrices.add(hotelPrice);
        });
        hotelMinRepository.saveAll(hotelPrices);

    }

    private void updateInventoryPrice(List<Inventory>inventoryList){
        inventoryList.forEach(inventory ->{
            BigDecimal dynamicPrice= pricingService.calculateDynamicPricing(inventory);
            inventory.setPrice(dynamicPrice);
        });
        inventoryRepository.saveAll(inventoryList);
    }
}
