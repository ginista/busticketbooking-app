package com.guvi.ticket_reservation_app.repository;

import com.guvi.ticket_reservation_app.entity.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusRepository extends JpaRepository<Bus, Long> {

    List<Bus> findAllByFromAndTo(String from, String to);
}
