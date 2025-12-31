package com.rockhardy.airbnb.strategy;

import com.rockhardy.airbnb.entity.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class PricingService {
    public BigDecimal calculateDynamicPricing(Inventory inventory){
        PricingStrategy pricingStrategy=new BasePricingStrategy();
        pricingStrategy=new SurgePriceStrategy(pricingStrategy);
        pricingStrategy= new OccupancyPricingStrategy(pricingStrategy);
        pricingStrategy= new UrgencyPricingStrategy(pricingStrategy);
        pricingStrategy= new HolidayPricingStrategy(pricingStrategy);

        return pricingStrategy.calculatePrice(inventory)    ;
    }
}
