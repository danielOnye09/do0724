package com.mortgage.tool_rental.controller;

import com.mortgage.tool_rental.dto.ApiResponse;
import com.mortgage.tool_rental.dto.CheckoutRequest;
import com.mortgage.tool_rental.model.RentalAgreement;
import com.mortgage.tool_rental.service.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rental")
public class RentalController {

    private final CheckoutService checkoutService;

    @PostMapping("/checkout")
    public ApiResponse<RentalAgreement> checkout(@RequestBody CheckoutRequest request) {
        try {
            RentalAgreement rentalAgreement = checkoutService.checkout(
                    request.getToolCode(), request.getRentalDays(), request.getDiscountPercent(), LocalDate.parse(request.getCheckoutDate()));
            return new ApiResponse<>(true, "Checkout successful", rentalAgreement);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }
}
