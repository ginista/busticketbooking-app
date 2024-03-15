package com.guvi.ticket_reservation_app.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class BookingResponse {
    private long bookingId;
    private long busId;
    private String busName;
    private String busType;
    private String from;
    private String to;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private LocalDateTime bookingTimestamp;
    private List<PassengerResponse> passengers;
}
