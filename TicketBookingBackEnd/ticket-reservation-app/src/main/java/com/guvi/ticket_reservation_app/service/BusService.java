package com.guvi.ticket_reservation_app.service;

import com.guvi.ticket_reservation_app.entity.Bus;
import com.guvi.ticket_reservation_app.model.BusResponse;
import com.guvi.ticket_reservation_app.repository.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusService {
    @Autowired
    private BusRepository busRepository;

    // Search Buses
    public List<BusResponse> searchBuses(String from, String to) {
        List<Bus> buses = busRepository.findAllByFromAndTo(from, to);

        return buses.stream().map(this::toBusResponse).collect(Collectors.toList());
    }

    private BusResponse toBusResponse(Bus bus) {
        BusResponse busResponse = new BusResponse();
        busResponse.setBusId(bus.getBusId());
        busResponse.setBusName(bus.getBusName());
        busResponse.setBusType(bus.getBusType());
        busResponse.setFrom(bus.getFrom());
        busResponse.setTo(bus.getTo());
        busResponse.setDepartureTime(bus.getDepartureTime());
        busResponse.setArrivalTime(bus.getArrivalTime());
        busResponse.setPrice(bus.getPrice());
        busResponse.setTotalSeats(bus.getTotalSeats());
        busResponse.setAvailableSeats(bus.getAvailableSeats());
        return busResponse;
    }
}
