package com.guvi.ticket_reservation_app.repository;

import com.guvi.ticket_reservation_app.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {
}
