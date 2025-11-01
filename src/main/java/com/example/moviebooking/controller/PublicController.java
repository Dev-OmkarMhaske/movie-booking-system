package com.example.moviebooking.controller;

import com.example.moviebooking.model.Movie;
import com.example.moviebooking.model.Seat;
import com.example.moviebooking.model.Show;
import com.example.moviebooking.repository.MovieRepository;
import com.example.moviebooking.repository.SeatRepository;
import com.example.moviebooking.repository.ShowRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PublicController {

    private final MovieRepository movieRepository;
    private final ShowRepository showRepository;
    private final SeatRepository seatRepository;

    public PublicController(MovieRepository movieRepository, ShowRepository showRepository, SeatRepository seatRepository) {
        this.movieRepository = movieRepository;
        this.showRepository = showRepository;
        this.seatRepository = seatRepository;
    }

    @GetMapping("/movies")
    public List<Movie> movies() {
        return movieRepository.findAll();
    }

    @GetMapping("/shows")
    public List<Show> shows() {
        return showRepository.findAll();
    }

    @GetMapping("/shows/{id}/seats")
    public List<Seat> seats(@PathVariable Long id) {
        Show s = showRepository.findById(id).orElseThrow();
        return seatRepository.findByAuditoriumId(s.getAuditorium().getId());
    }
}
