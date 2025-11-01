package com.example.moviebooking.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id @GeneratedValue
    private Long id;

    private String username; // simple reference to user

    @ManyToOne
    private Show show;

    private BigDecimal totalAmount;
    private BigDecimal discountAmount;
    private BigDecimal finalAmount;
    private String status; // CONFIRMED, PENDING, CANCELLED
    private LocalDateTime createdAt;
}
