package com.guvi.ticket_reservation_app.service;

import com.guvi.ticket_reservation_app.entity.Booking;
import com.guvi.ticket_reservation_app.entity.Bus;
import com.guvi.ticket_reservation_app.entity.Passenger;
import com.guvi.ticket_reservation_app.entity.User;
import com.guvi.ticket_reservation_app.exception.ResourceNotFoundException;
import com.guvi.ticket_reservation_app.model.CreateBookingRequest;
import com.guvi.ticket_reservation_app.model.BookingResponse;
import com.guvi.ticket_reservation_app.model.PassengerResponse;
import com.guvi.ticket_reservation_app.repository.BookingRepository;
import com.guvi.ticket_reservation_app.repository.BusRepository;
import com.guvi.ticket_reservation_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public BookingResponse bookTickets(String userName, CreateBookingRequest createBookingRequest) {
        User user = userRepository.findByName(userName).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        Bus bus = busRepository.findById(createBookingRequest.getBusId()).orElseThrow(() -> new ResourceNotFoundException("Bus Data Not Found for the provided bus id"));

        if (bus.getAvailableSeats() < createBookingRequest.getPassengers().size()) {
            throw new RuntimeException("Seats Not Available");
        }

        AtomicInteger lastSoldSeatNumber = new AtomicInteger(bus.getLastSoldSeatNumber());
        AtomicInteger availableSeats = new AtomicInteger(bus.getAvailableSeats());

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setBus(bus);
        booking.setBookingTimestamp(LocalDateTime.now());
        booking.setNumberOfTickets(createBookingRequest.getPassengers().size());
        booking.setPassengers(toPassengers(createBookingRequest, booking, lastSoldSeatNumber, availableSeats));

        bus.setLastSoldSeatNumber(lastSoldSeatNumber.get());
        bus.setAvailableSeats(availableSeats.get());

        bookingRepository.save(booking);
        busRepository.save(bus);

        return mapBookingConfirmationResponse(booking);
    }

    private static List<Passenger> toPassengers(CreateBookingRequest createBookingRequest, Booking booking, AtomicInteger lastSoldSeatNumber, AtomicInteger availableSeats) {
        return createBookingRequest.getPassengers()
                .stream()
                .map(passengerRequest -> {
                    Passenger passenger = new Passenger();
                    passenger.setBooking(booking);
                    passenger.setName(passengerRequest.getName());
                    passenger.setAge(passengerRequest.getAge());
                    passenger.setGender(passengerRequest.getGender());
                    passenger.setSeatNumber(lastSoldSeatNumber.incrementAndGet());

                    availableSeats.decrementAndGet();

                    return passenger;
                })
                .collect(Collectors.toList());
    }

    private BookingResponse mapBookingConfirmationResponse(Booking booking) {
        Bus bus = booking.getBus();

        BookingResponse bookingResponse = new BookingResponse();

        bookingResponse.setBookingId(booking.getBookingId());
        bookingResponse.setBusId(booking.getBookingId());
        bookingResponse.setBusName(bus.getBusName());
        bookingResponse.setBusType(bus.getBusType());
        bookingResponse.setFrom(bus.getFrom());
        bookingResponse.setTo(bus.getTo());
        bookingResponse.setDepartureTime(bus.getDepartureTime());
        bookingResponse.setArrivalTime(bus.getArrivalTime());
        bookingResponse.setBookingTimestamp(booking.getBookingTimestamp());
        bookingResponse.setPassengers(mapBookingConfirmationPassengerResponses(booking));

        return bookingResponse;
    }

    private List<PassengerResponse> mapBookingConfirmationPassengerResponses(Booking booking) {
        return booking.getPassengers()
                .stream()
                .map(passenger -> {
                    PassengerResponse passengerResponse = new PassengerResponse();
                    passengerResponse.setName(passenger.getName());
                    passengerResponse.setAge(passenger.getAge());
                    passengerResponse.setGender(passenger.getGender());
                    passengerResponse.setSeatNumber(passenger.getSeatNumber());
                    return passengerResponse;
                })
                .collect(Collectors.toList());
    }

    public List<BookingResponse> getBookings(String userName) {
        List<Booking> bookings = bookingRepository.findAllByName(userName);

        return bookings.stream()
                .map(this::mapBookingConfirmationResponse)
                .collect(Collectors.toList());
    }
}
