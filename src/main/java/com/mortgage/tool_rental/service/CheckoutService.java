package com.mortgage.tool_rental.service;
import com.mortgage.tool_rental.factory.ToolFactory;
import com.mortgage.tool_rental.strategy.ChargeCalculationStrategy;
import com.mortgage.tool_rental.model.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CheckoutService {
    private final ChargeCalculationStrategy chargeCalculationStrategy;

    public CheckoutService(ChargeCalculationStrategy chargeCalculationStrategy) {
        this.chargeCalculationStrategy = chargeCalculationStrategy;
    }

    public RentalAgreement checkout(String toolCode, int rentalDays, int discountPercent, LocalDate checkoutDate) {
        if (rentalDays < 1) {
            throw new IllegalArgumentException("Rental day count must be 1 or greater");
        }
        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("Discount percent must be between 0 and 100");
        }

        Tool tool = ToolFactory.createTool(toolCode);
        LocalDate dueDate = checkoutDate.plusDays(rentalDays);
        int chargeableDays = chargeCalculationStrategy.calculateChargeableDays(checkoutDate, dueDate, tool);
        double preDiscountCharge = chargeableDays * tool.getDailyCharge();
        double discountAmount = (preDiscountCharge * discountPercent) / 100;
        double finalCharge = preDiscountCharge - discountAmount;

        return new RentalAgreement(toolCode, tool.getToolType(), tool.getBrand(), rentalDays, checkoutDate, dueDate, tool.getDailyCharge(), chargeableDays, preDiscountCharge, discountPercent, discountAmount, finalCharge);
    }
}


