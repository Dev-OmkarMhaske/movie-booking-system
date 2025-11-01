package com.example.moviebooking.service;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class BookingResponse {
    private Long bookingId;
    private String status;
    private List<String> seats;
    private BigDecimal totalAmount;
    private BigDecimal discount;
    private BigDecimal finalAmount;
}
