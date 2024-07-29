package com.mortgage.tool_rental.strategy;

import com.mortgage.tool_rental.model.Tool;

import java.time.LocalDate;

public interface ChargeCalculationStrategy {
    int calculateChargeableDays(LocalDate checkoutDate, LocalDate dueDate, Tool tool);
}

