package com.guvi.ticket_reservation_app.repository;

import com.guvi.ticket_reservation_app.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("From Booking b where b.user.name = :userName")
    List<Booking> findAllByName(String userName);
}
