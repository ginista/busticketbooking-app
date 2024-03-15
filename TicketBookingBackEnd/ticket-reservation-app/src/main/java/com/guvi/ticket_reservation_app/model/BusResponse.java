package com.guvi.ticket_reservation_app.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class BusResponse {
    private long busId;
    private String busName;
    private String busType;
    private String from;
    private String to;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private int price;
    private int totalSeats;
    private int availableSeats;
}
