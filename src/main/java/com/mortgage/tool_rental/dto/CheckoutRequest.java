package com.mortgage.tool_rental.dto;

import lombok.Data;

@Data
public class CheckoutRequest {
    private String toolCode;
    private int rentalDays;
    private int discountPercent;
    private String checkoutDate;
}

