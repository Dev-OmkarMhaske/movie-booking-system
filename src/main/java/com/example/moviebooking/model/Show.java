package com.example.moviebooking.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name="show_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shows")
public class Show {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Movie movie;

    @ManyToOne
    private Auditorium auditorium;

    private LocalDateTime showTime;

    private BigDecimal ticketPrice;
}
