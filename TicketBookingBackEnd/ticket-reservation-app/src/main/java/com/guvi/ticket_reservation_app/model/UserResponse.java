package com.guvi.ticket_reservation_app.model;

import com.guvi.ticket_reservation_app.model.enums.Gender;
import com.guvi.ticket_reservation_app.model.enums.Role;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class UserResponse {
    private long userId;
    private String name;
    private List<Role> roles;
    private Gender gender;
    private LocalDate dateOfBirth;
    private String email;
    private String mobileNumber;
}
