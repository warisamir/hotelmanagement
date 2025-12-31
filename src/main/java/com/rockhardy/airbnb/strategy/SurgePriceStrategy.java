package com.rockhardy.airbnb.strategy;

import com.rockhardy.airbnb.entity.Inventory;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class SurgePriceStrategy implements PricingStrategy{
    private final PricingStrategy wrapper;
// FOLLOWING  Strategy Design Pattern
    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal price=wrapper.calculatePrice(inventory);
        return price.multiply(inventory.getSurgeFactor());
    }
}
