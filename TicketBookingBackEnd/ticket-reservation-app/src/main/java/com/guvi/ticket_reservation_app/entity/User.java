package com.guvi.ticket_reservation_app.entity;

import com.guvi.ticket_reservation_app.model.enums.Gender;
import com.guvi.ticket_reservation_app.model.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "roles", nullable = false)
    private String roles;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "dob")
    private LocalDate dateOfBirth;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @OneToMany(mappedBy = "user")
    private List<Booking> bookings;

    public List<Role> getRolesList(){
        return Arrays.stream(this.roles.split(",")).map(Role::valueOf).collect(Collectors.toList());
    }

    public void setRolesList(List<Role> roles) {
        this.roles = roles.stream().map(Role::name).collect(Collectors.joining(","));
    }
}
