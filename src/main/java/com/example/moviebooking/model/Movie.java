package com.example.moviebooking.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movies")
public class Movie {
    @Id @GeneratedValue
    private Long id;
    private String title;
    @Column(length=2000)
    private String description;
    private Integer durationMinutes;
    private String language;
    private LocalDate releaseDate;
}
