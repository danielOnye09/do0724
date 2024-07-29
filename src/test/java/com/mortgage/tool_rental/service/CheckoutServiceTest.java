package com.mortgage.tool_rental.service;

import com.mortgage.tool_rental.model.RentalAgreement;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CheckoutServiceTest {

	@Autowired
	private CheckoutService checkoutService;

	private static final double DELTA = 1e-2;

	@Test
	public void testCheckoutWithValidData() {
		RentalAgreement agreement = checkoutService.checkout("LADW", 3, 10, LocalDate.of(2020, 7, 2));
		assertEquals("LADW", agreement.getToolCode());
		assertEquals("Ladder", agreement.getToolType());
		assertEquals("Werner", agreement.getBrand());
		assertEquals(3, agreement.getRentalDays());
		assertEquals(LocalDate.of(2020, 7, 2), agreement.getCheckoutDate());
		assertEquals(LocalDate.of(2020, 7, 5), agreement.getDueDate());
		assertEquals(1.99, agreement.getDailyCharge(), DELTA);
		assertEquals(2, agreement.getChargeDays());
		assertEquals(3.98, agreement.getPreDiscountCharge(), DELTA);
		assertEquals(10, agreement.getDiscountPercent());
		assertEquals(0.40, agreement.getDiscountAmount(), DELTA);
		assertEquals(3.58, agreement.getFinalCharge(), DELTA);
	}

	@Test
	public void testCheckoutWithInvalidDiscount() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			checkoutService.checkout("JAKR", 5, 101, LocalDate.of(2015, 9, 3));
		});
		assertEquals("Discount percent must be between 0 and 100", thrown.getMessage());
	}

	@Test
	public void testCheckoutWithInvalidRentalDays() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			checkoutService.checkout("JAKR", 0, 10, LocalDate.of(2015, 9, 3));
		});
		assertEquals("Rental day count must be 1 or greater", thrown.getMessage());
	}

	@Test
	public void testCheckoutChainsawOverHoliday() {
		RentalAgreement agreement = checkoutService.checkout("CHNS", 5, 25, LocalDate.of(2015, 7, 2));
		assertEquals("CHNS", agreement.getToolCode());
		assertEquals("Chainsaw", agreement.getToolType());
		assertEquals("Stihl", agreement.getBrand());
		assertEquals(5, agreement.getRentalDays());
		assertEquals(LocalDate.of(2015, 7, 2), agreement.getCheckoutDate());
		assertEquals(LocalDate.of(2015, 7, 7), agreement.getDueDate());
		assertEquals(1.49, agreement.getDailyCharge(), DELTA);
		assertEquals(3, agreement.getChargeDays());
		assertEquals(4.47, agreement.getPreDiscountCharge(), DELTA);
		assertEquals(25, agreement.getDiscountPercent());
		assertEquals(1.12, agreement.getDiscountAmount(), DELTA);
		assertEquals(3.35, agreement.getFinalCharge(), DELTA);
	}


	@Test
	public void testCheckoutJackhammerOverHoliday() {
		RentalAgreement agreement = checkoutService.checkout("JAKR", 9, 0, LocalDate.of(2015, 7, 2));
		assertEquals("JAKR", agreement.getToolCode());
		assertEquals("Jackhammer", agreement.getToolType());
		assertEquals("Ridgid", agreement.getBrand());
		assertEquals(9, agreement.getRentalDays());
		assertEquals(LocalDate.of(2015, 7, 2), agreement.getCheckoutDate());
		assertEquals(LocalDate.of(2015, 7, 11), agreement.getDueDate());
		assertEquals(2.99, agreement.getDailyCharge(), DELTA);
		assertEquals(5, agreement.getChargeDays());
		assertEquals(14.95, agreement.getPreDiscountCharge(), DELTA);
		assertEquals(0, agreement.getDiscountPercent());
		assertEquals(0.00, agreement.getDiscountAmount(), DELTA);
		assertEquals(14.95, agreement.getFinalCharge(), DELTA);
	}

	@Test
	public void testCheckoutJackhammerWith50PercentDiscount() {
		RentalAgreement agreement = checkoutService.checkout("JAKR", 4, 50, LocalDate.of(2020, 7, 2));
		assertEquals("JAKR", agreement.getToolCode());
		assertEquals("Jackhammer", agreement.getToolType());
		assertEquals("Ridgid", agreement.getBrand());
		assertEquals(4, agreement.getRentalDays());
		assertEquals(LocalDate.of(2020, 7, 2), agreement.getCheckoutDate());
		assertEquals(LocalDate.of(2020, 7, 6), agreement.getDueDate());
		assertEquals(2.99, agreement.getDailyCharge(), DELTA);
		assertEquals(1, agreement.getChargeDays());
		assertEquals(2.99, agreement.getPreDiscountCharge(), DELTA);
		assertEquals(50, agreement.getDiscountPercent());
		assertEquals(1.495, agreement.getDiscountAmount(), DELTA);
		assertEquals(1.495, agreement.getFinalCharge(), DELTA);
	}


	@Test
	public void testCheckoutWithNoDiscount() {
		RentalAgreement agreement = checkoutService.checkout("JAKD", 6, 0, LocalDate.of(2015, 9, 3));
		assertEquals("JAKD", agreement.getToolCode());
		assertEquals("Jackhammer", agreement.getToolType());
		assertEquals("DeWalt", agreement.getBrand());
		assertEquals(6, agreement.getRentalDays());
		assertEquals(LocalDate.of(2015, 9, 3), agreement.getCheckoutDate());
		assertEquals(LocalDate.of(2015, 9, 9), agreement.getDueDate());
		assertEquals(2.99, agreement.getDailyCharge(), DELTA);
		assertEquals(4, agreement.getChargeDays());  // Expected 4 charge days
		assertEquals(11.96, agreement.getPreDiscountCharge(), DELTA);
		assertEquals(0, agreement.getDiscountPercent());
		assertEquals(0.00, agreement.getDiscountAmount(), DELTA);
		assertEquals(11.96, agreement.getFinalCharge(), DELTA);
	}

}
