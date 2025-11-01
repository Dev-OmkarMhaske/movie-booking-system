package com.example.moviebooking.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "auditoriums")
public class Auditorium {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private Integer totalSeats;
}
