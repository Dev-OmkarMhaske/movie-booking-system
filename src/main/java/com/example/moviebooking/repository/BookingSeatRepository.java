package com.example.moviebooking.repository;

import com.example.moviebooking.model.BookingSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface BookingSeatRepository extends JpaRepository<BookingSeat, Long> {
    @Query("select bs from BookingSeat bs where bs.show.id = :showId and bs.seat.id in :seatIds")
    List<BookingSeat> findByShowAndSeatIds(Long showId, List<Long> seatIds);
}
