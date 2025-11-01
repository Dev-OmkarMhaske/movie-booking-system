package com.example.moviebooking.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "promo_codes")
public class PromoCode {
    @Id @GeneratedValue
    private Long id;
    private String code;
    private String type; // FREE_SEAT or FLAT_250
    private LocalDate expiryDate;
    private Boolean active;
}
