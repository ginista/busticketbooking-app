package com.guvi.ticket_reservation_app.model;

import com.guvi.ticket_reservation_app.model.enums.Gender;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PassengerResponse {
    private String name;
    private Integer age;
    private Gender gender;
    private int seatNumber;
}
