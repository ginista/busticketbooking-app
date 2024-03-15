package com.guvi.ticket_reservation_app.service;

import com.guvi.ticket_reservation_app.entity.Bus;
import com.guvi.ticket_reservation_app.model.BusResponse;
import com.guvi.ticket_reservation_app.repository.BusRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BusServiceTest {

    @Mock
    private BusRepository busRepository;

    @InjectMocks
    private BusService busService;

    @Test
    void searchBuses_ShouldReturnListOfBusResponses_WhenBusesFound() {
        // Arrange
        String from = "CityA";
        String to = "CityB";

        List<Bus> mockBuses = Arrays.asList(
                new Bus(1L, "Bus1", "Type1", "CityA", "CityB", LocalTime.parse("10:00:00"), LocalTime.parse("12:00:00"), 20, 50, 20, 0),
                new Bus(2L, "Bus2", "Type2", "CityA", "CityB", LocalTime.parse("14:00:00"), LocalTime.parse("16:00:00"), 25, 40, 10, 0)
        );

        when(busRepository.findAllByFromAndTo(from, to)).thenReturn(mockBuses);

        // Act
        List<BusResponse> busResponses = busService.searchBuses(from, to);

        // Assert
        assertEquals(2, busResponses.size());

        // Verify that the repository method was called with the correct parameters
        verify(busRepository, times(1)).findAllByFromAndTo(from, to);

        // Additional assertions based on your specific business logic
        assertEquals("Bus1", busResponses.get(0).getBusName());
        assertEquals("Bus2", busResponses.get(1).getBusName());
        // Add more assertions as needed
    }

    @Test
    void searchBuses_ShouldReturnEmptyList_WhenNoBusesFound() {
        // Arrange
        String from = "CityX";
        String to = "CityY";

        when(busRepository.findAllByFromAndTo(from, to)).thenReturn(Arrays.asList());

        // Act
        List<BusResponse> busResponses = busService.searchBuses(from, to);

        // Assert
        assertEquals(0, busResponses.size());

        // Verify that the repository method was called with the correct parameters
        verify(busRepository, times(1)).findAllByFromAndTo(from, to);

        // Additional assertions based on your specific business logic for empty result
    }
}
