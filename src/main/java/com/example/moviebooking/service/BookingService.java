package com.example.moviebooking.service;

import com.example.moviebooking.model.*;
import com.example.moviebooking.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final SeatRepository seatRepository;
    private final BookingRepository bookingRepository;
    private final BookingSeatRepository bookingSeatRepository;
    private final ShowRepository showRepository;
    private final PromoCodeRepository promoCodeRepository;

    public BookingService(SeatRepository seatRepository,
                          BookingRepository bookingRepository,
                          BookingSeatRepository bookingSeatRepository,
                          ShowRepository showRepository,
                          PromoCodeRepository promoCodeRepository) {
        this.seatRepository = seatRepository;
        this.bookingRepository = bookingRepository;
        this.bookingSeatRepository = bookingSeatRepository;
        this.showRepository = showRepository;
        this.promoCodeRepository = promoCodeRepository;
    }

    @Transactional
    public synchronized BookingResponse book(String username, Long showId, List<String> seatCodes, String promoCode) {
        Show show = showRepository.findById(showId).orElseThrow(() -> new RuntimeException("Show not found"));

        // fetch seats with PESSIMISTIC_WRITE lock
        var seats = seatRepository.findByAuditoriumAndCodesForUpdate(show.getAuditorium().getId(), seatCodes);
        if (seats.size() != seatCodes.size()) throw new RuntimeException("Invalid seat codes requested");

        var seatIds = seats.stream().map(Seat::getId).collect(Collectors.toList());
        var booked = bookingSeatRepository.findByShowAndSeatIds(showId, seatIds);
        if (!booked.isEmpty()) throw new RuntimeException("One or more seats already booked");

        BigDecimal total = show.getTicketPrice().multiply(BigDecimal.valueOf(seats.size()));
        BigDecimal discount = BigDecimal.ZERO;

        if (promoCode != null && !promoCode.isBlank()) {
            var promoOpt = promoCodeRepository.findByCode(promoCode);
            if (promoOpt.isPresent()) {
                PromoCode promo = promoOpt.get();
                if (promo.getActive() && (promo.getExpiryDate() == null || !promo.getExpiryDate().isBefore(java.time.LocalDate.now()))) {
                    if ("FREE_SEAT".equals(promo.getType())) {
                        // free cheapest seat (single seat free)
                        discount = show.getTicketPrice();
                    } else if ("FLAT_250".equals(promo.getType())) {
                        discount = BigDecimal.valueOf(250);
                        if (discount.compareTo(total) > 0) discount = total;
                    }
                }
            }
        }

        Booking booking = new Booking();
        booking.setUsername(username);
        booking.setShow(show);
        booking.setTotalAmount(total);
        booking.setDiscountAmount(discount);
        booking.setFinalAmount(total.subtract(discount));
        booking.setStatus("CONFIRMED");
        booking.setCreatedAt(LocalDateTime.now());
        booking = bookingRepository.save(booking);

        for (Seat s : seats) {
            BookingSeat bs = new BookingSeat();
            bs.setBooking(booking);
            bs.setShow(show);
            bs.setSeat(s);
            bs.setPrice(show.getTicketPrice().doubleValue());
            bookingSeatRepository.save(bs);
        }

        return new BookingResponse(booking.getId(), booking.getStatus(),
                seats.stream().map(Seat::getSeatCode).collect(Collectors.toList()),
                booking.getTotalAmount(), booking.getDiscountAmount(), booking.getFinalAmount());
    }
}
