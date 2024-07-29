package com.mortgage.tool_rental.strategy;

import com.mortgage.tool_rental.model.Tool;
import com.mortgage.tool_rental.util.DateUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DefaultChargeCalculationStrategy implements ChargeCalculationStrategy {
    @Override
    public int calculateChargeableDays(LocalDate checkoutDate, LocalDate dueDate, Tool tool) {
        int chargeableDays = 0;
        LocalDate currentDate = checkoutDate.plusDays(1);

        while (!currentDate.isAfter(dueDate)) {
            boolean isHoliday = DateUtils.isHoliday(currentDate);
            boolean isWeekday = DateUtils.isWeekday(currentDate);
            boolean isWeekend = DateUtils.isWeekend(currentDate);

            if ((tool.isWeekdayCharge() && isWeekday && !isHoliday)
                    || (tool.isWeekendCharge() && isWeekend && !isHoliday)
                    || (tool.isHolidayCharge() && isHoliday)) {
                chargeableDays++;
            }
            currentDate = currentDate.plusDays(1);
        }

        return chargeableDays;
    }
}





