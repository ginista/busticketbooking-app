package com.guvi.ticket_reservation_app.service;

import com.guvi.ticket_reservation_app.entity.Booking;
import com.guvi.ticket_reservation_app.entity.Bus;
import com.guvi.ticket_reservation_app.entity.Passenger;
import com.guvi.ticket_reservation_app.entity.User;
import com.guvi.ticket_reservation_app.exception.ResourceNotFoundException;
import com.guvi.ticket_reservation_app.model.BookingResponse;
import com.guvi.ticket_reservation_app.model.CreateBookingRequest;
import com.guvi.ticket_reservation_app.model.PassengerRequest;
import com.guvi.ticket_reservation_app.model.enums.Gender;
import com.guvi.ticket_reservation_app.repository.BookingRepository;
import com.guvi.ticket_reservation_app.repository.BusRepository;
import com.guvi.ticket_reservation_app.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private BusRepository busRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BookingService bookingService;

    @Test
    void bookTickets_ShouldBookTickets_WhenValidRequest() {
        // Arrange
        String userName = "user1";
        CreateBookingRequest createBookingRequest = createValidBookingRequest();

        User user = new User();
        user.setName(userName);

        Bus bus = new Bus();
        bus.setBusId(1L);
        bus.setAvailableSeats(50);
        bus.setLastSoldSeatNumber(0);

        when(userRepository.findByName(userName)).thenReturn(Optional.of(user));
        when(busRepository.findById(createBookingRequest.getBusId())).thenReturn(Optional.of(bus));
        when(bookingRepository.save(any())).thenAnswer((Answer<Booking>)  invocation -> {
            Booking argument = invocation.getArgument(0, Booking.class);
            argument.setBookingId(1);
            return argument;
        });

        // Act
        BookingResponse bookingResponse = bookingService.bookTickets(userName, createBookingRequest);

        // Assert
        assertNotNull(bookingResponse);
        assertEquals(1L, bookingResponse.getBookingId());
        assertEquals(1L, bookingResponse.getBusId());

        // Verify interactions
        verify(userRepository, times(1)).findByName(userName);
        verify(busRepository, times(1)).findById(createBookingRequest.getBusId());
        verify(bookingRepository, times(1)).save(any());
    }

    @Test
    void bookTickets_ShouldThrowResourceNotFoundException_WhenUserNotFound() {
        // Arrange
        String userName = "nonExistentUser";
        CreateBookingRequest createBookingRequest = createValidBookingRequest();

        when(userRepository.findByName(userName)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> bookingService.bookTickets(userName, createBookingRequest));

        // Verify interactions
        verify(userRepository, times(1)).findByName(userName);
        verifyNoInteractions(busRepository, bookingRepository);
    }

    @Test
    void bookTickets_ShouldThrowResourceNotFoundException_WhenBusNotFound() {
        // Arrange
        String userName = "user1";
        CreateBookingRequest createBookingRequest = createValidBookingRequest();

        when(userRepository.findByName(userName)).thenReturn(Optional.of(new User()));
        when(busRepository.findById(createBookingRequest.getBusId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> bookingService.bookTickets(userName, createBookingRequest));

        // Verify interactions
        verify(userRepository, times(1)).findByName(userName);
        verify(busRepository, times(1)).findById(createBookingRequest.getBusId());
        verifyNoInteractions(bookingRepository);
    }

    @Test
    void getBookings_ShouldReturnListOfBookingResponses_WhenBookingsFound() {
        // Arrange
        String userName = "user1";
        Booking booking = createSampleBooking(1L, 1L, userName);

        when(bookingRepository.findAllByName(userName)).thenReturn(Collections.singletonList(booking));

        // Act
        var bookingResponses = bookingService.getBookings(userName);

        // Assert
        assertNotNull(bookingResponses);
        assertEquals(1, bookingResponses.size());
        assertEquals(1, bookingResponses.get(0).getBookingId());

        // Verify interactions
        verify(bookingRepository, times(1)).findAllByName(userName);
    }

    @Test
    void getBookings_ShouldReturnEmptyList_WhenNoBookingsFound() {
        // Arrange
        String userName = "user2";

        when(bookingRepository.findAllByName(userName)).thenReturn(Collections.emptyList());

        // Act
        var bookingResponses = bookingService.getBookings(userName);

        // Assert
        assertNotNull(bookingResponses);
        assertTrue(bookingResponses.isEmpty());

        // Verify interactions
        verify(bookingRepository, times(1)).findAllByName(userName);
    }

    private CreateBookingRequest createValidBookingRequest() {
        PassengerRequest passengerRequest = new PassengerRequest();
        passengerRequest.setName("Passenger1");
        passengerRequest.setAge(25);
        passengerRequest.setGender(Gender.Male);

        CreateBookingRequest createBookingRequest = new CreateBookingRequest();
        createBookingRequest.setBusId(1L);
        createBookingRequest.setPassengers(Collections.singletonList(passengerRequest));

        return createBookingRequest;
    }

    private Booking createSampleBooking(long bookingId, long busId, String userName) {
        Booking booking = new Booking();
        booking.setBookingId(bookingId);
        booking.setNumberOfTickets(1);
        booking.setUser(new User(1L, userName,"abc@gmail.com", "pwd", "ROLE_USER", Gender.Male, LocalDate.parse("2000-01-01"), "8888888888", Collections.singletonList(booking)));
        booking.setBus(new Bus(busId, "Bus1", "Type1", "CityA", "CityB", LocalTime.parse("10:00:00"), LocalTime.parse("12:00:00"), 20, 50, 20, 0));
        booking.setPassengers(Collections.singletonList(new Passenger(1L, booking, "Passenger1", 25, Gender.Male, 20)));
        return booking;
    }
}

