package com.example.moviebooking.controller;

import com.example.moviebooking.service.BookingResponse;
import com.example.moviebooking.service.BookingService;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/book")
    public BookingResponse book(@RequestBody BookRequest req, Authentication auth) {
        String username = auth.getName();
        return bookingService.book(username, req.getShowId(), req.getSeatCodes(), req.getPromoCode());
    }

    @Data
    static class BookRequest {
        private Long showId;
        private List<String> seatCodes;
        private String promoCode;
    }
}
