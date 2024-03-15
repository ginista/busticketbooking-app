package com.guvi.ticket_reservation_app.controller;

import com.guvi.ticket_reservation_app.model.CreateBookingRequest;
import com.guvi.ticket_reservation_app.model.BookingResponse;
import com.guvi.ticket_reservation_app.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("bookings")
@PreAuthorize("hasAuthority('ROLE_USER')")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingResponse> bookTickets(Authentication authentication,  @Valid @RequestBody CreateBookingRequest createBookingRequest) {
        BookingResponse bookingResponse = bookingService.bookTickets(authentication.getName(), createBookingRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(bookingResponse);
    }

    @GetMapping
    public ResponseEntity<List<BookingResponse>> getBookings(Authentication authentication){
        List<BookingResponse> bookings = bookingService.getBookings(authentication.getName());

        return ResponseEntity.ok(bookings);
    }
}
