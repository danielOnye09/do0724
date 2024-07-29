package com.mortgage.tool_rental.controller;

import com.mortgage.tool_rental.dto.ApiResponse;
import com.mortgage.tool_rental.dto.CheckoutRequest;
import com.mortgage.tool_rental.model.RentalAgreement;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RentalControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCheckout() {

        String url = "http://localhost:" + port + "/api/rental/checkout";

        CheckoutRequest request = new CheckoutRequest();
        request.setToolCode("LADW");
        request.setRentalDays(3);
        request.setDiscountPercent(10);
        request.setCheckoutDate("2020-07-02");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CheckoutRequest> requestEntity = new HttpEntity<>(request, headers);

        ResponseEntity<ApiResponse<RentalAgreement>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {
                }
        );

        ApiResponse<RentalAgreement> response = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(response);
        assertTrue(response.isSuccess());
        RentalAgreement rentalAgreement = response.getData();
        assertNotNull(rentalAgreement);
        assertEquals("LADW", rentalAgreement.getToolCode());
    }
}
