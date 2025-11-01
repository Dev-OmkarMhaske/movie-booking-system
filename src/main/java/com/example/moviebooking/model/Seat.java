package com.example.moviebooking.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "seats", uniqueConstraints = {@UniqueConstraint(columnNames = {"auditorium_id","seat_code"})})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seat {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Auditorium auditorium;

    @Column(name="seat_code")
    private String seatCode;
}
