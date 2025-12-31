package com.rockhardy.airbnb.strategy;

import com.rockhardy.airbnb.entity.Inventory;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@RequiredArgsConstructor
public class UrgencyPricingStrategy implements PricingStrategy{
    private final PricingStrategy wrapper;
    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        ///  if someone books the ticket in the last 7 days
        BigDecimal price=wrapper.calculatePrice(inventory);
        LocalDate today= LocalDate.now();
        LocalDate plus = today.plusDays(7);
        if(!inventory.getDate().isBefore(today) && inventory.getDate().isBefore(plus)){
            price=price.multiply(BigDecimal.valueOf(1.15));
        }
        return price;
    }
}
