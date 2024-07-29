package com.mortgage.tool_rental.model;

import com.mortgage.tool_rental.util.DateUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalAgreement {
    private String toolCode;
    private String toolType;
    private String brand;
    private int rentalDays;
    private LocalDate checkoutDate;
    private LocalDate dueDate;
    private double dailyCharge;
    private int chargeDays;
    private double preDiscountCharge;
    private int discountPercent;
    private double discountAmount;
    private double finalCharge;


    public void printAgreement() {
        System.out.printf("Tool code: %s%n", toolCode);
        System.out.printf("Tool type: %s%n", toolType);
        System.out.printf("Tool brand: %s%n", brand);
        System.out.printf("Rental days: %d%n", rentalDays);
        System.out.printf("Check out date: %s%n", DateUtils.formatDate(checkoutDate));
        System.out.printf("Due date: %s%n", DateUtils.formatDate(dueDate));
        System.out.printf("Daily rental charge: %s%n", String.format("$%.2f", dailyCharge));
        System.out.printf("Charge days: %d%n", chargeDays);
        System.out.printf("Pre-discount charge: %s%n", String.format("$%.2f", preDiscountCharge));
        System.out.printf("Discount percent: %d%%%n", discountPercent);
        System.out.printf("Discount amount: %s%n", String.format("$%.2f", discountAmount));
        System.out.printf("Final charge: %s%n", String.format("$%.2f", finalCharge));
    }

}
