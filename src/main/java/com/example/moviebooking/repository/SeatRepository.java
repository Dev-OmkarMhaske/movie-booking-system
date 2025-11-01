package com.example.moviebooking.repository;

import com.example.moviebooking.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import jakarta.persistence.LockModeType;
import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from Seat s where s.auditorium.id = :audId and s.seatCode in :codes")
    List<Seat> findByAuditoriumAndCodesForUpdate(@Param("audId") Long auditoriumId,
                                                 @Param("codes") List<String> codes);
    List<Seat> findByAuditoriumId(Long auditoriumId);
}
