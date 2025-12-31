package com.rockhardy.airbnb.strategy;
import com.rockhardy.airbnb.entity.Inventory;
import java.math.BigDecimal;

public interface  PricingStrategy {
    BigDecimal calculatePrice(Inventory inventory);
}
