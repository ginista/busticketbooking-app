package com.guvi.ticket_reservation_app.controller;

import com.guvi.ticket_reservation_app.model.BusResponse;
import com.guvi.ticket_reservation_app.service.BusService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BusControllerTest {

    @Mock
    private BusService busService;

    @InjectMocks
    private BusController busController;

    @Test
    void searchBuses_ShouldReturnListOfBusResponses() {
        // Arrange
        String from = "CityA";
        String to = "CityB";
        List<BusResponse> busResponses = Arrays.asList(createValidBusResponse(), createValidBusResponse());

        when(busService.searchBuses(from, to)).thenReturn(busResponses);

        // Act
        ResponseEntity<List<BusResponse>> responseEntity = busController.searchBuses(from, to);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(busResponses, responseEntity.getBody());

        // Verify interactions
        verify(busService, times(1)).searchBuses(from, to);
    }

    // Utility methods...

    private BusResponse createValidBusResponse() {
        BusResponse busResponse = new BusResponse();
        // Set properties as needed
        return busResponse;
    }
}
