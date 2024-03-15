package com.guvi.ticket_reservation_app.controller;

import com.guvi.ticket_reservation_app.model.BookingResponse;
import com.guvi.ticket_reservation_app.model.CreateBookingRequest;
import com.guvi.ticket_reservation_app.service.BookingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingControllerTest {

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    @Test
    void bookTickets_ShouldReturnBookingResponse_WhenBookingIsSuccessful() {
        // Arrange
        Authentication authentication = createMockAuthentication();
        CreateBookingRequest createBookingRequest = createValidBookingRequest();
        BookingResponse bookingResponse = createValidBookingResponse();

        when(bookingService.bookTickets(authentication.getName(), createBookingRequest)).thenReturn(bookingResponse);

        // Act
        ResponseEntity<BookingResponse> responseEntity = bookingController.bookTickets(authentication, createBookingRequest);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(bookingResponse, responseEntity.getBody());

        // Verify interactions
        verify(bookingService, times(1)).bookTickets(authentication.getName(), createBookingRequest);
    }

    @Test
    void getBookings_ShouldReturnListOfBookingResponses() {
        // Arrange
        Authentication authentication = createMockAuthentication();
        List<BookingResponse> bookingResponses = Arrays.asList(createValidBookingResponse(), createValidBookingResponse());

        when(bookingService.getBookings(authentication.getName())).thenReturn(bookingResponses);

        // Act
        ResponseEntity<List<BookingResponse>> responseEntity = bookingController.getBookings(authentication);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(bookingResponses, responseEntity.getBody());

        // Verify interactions
        verify(bookingService, times(1)).getBookings(authentication.getName());
    }

    // Utility methods...

    private Authentication createMockAuthentication() {
        return mock(Authentication.class);
    }

    private CreateBookingRequest createValidBookingRequest() {
        CreateBookingRequest createBookingRequest = new CreateBookingRequest();
        // Set properties as needed
        return createBookingRequest;
    }

    private BookingResponse createValidBookingResponse() {
        BookingResponse bookingResponse = new BookingResponse();
        // Set properties as needed
        return bookingResponse;
    }
}
