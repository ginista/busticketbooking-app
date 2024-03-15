package com.guvi.ticket_reservation_app.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateBookingRequest {
    @NotNull(message = "Bus Id is required")
    private Long busId;

    @NotNull(message = "Passenger(s) is required")
    @Size(min = 1, message = "Minimum 1 passenger is required")
    @Valid
    private List<PassengerRequest> passengers;
}
