package com.example.moviebooking.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"show_id","seat_id"})})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingSeat {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Booking booking;

    @ManyToOne
    private Show show;

    @ManyToOne
    private Seat seat;

    private Double price;
}
