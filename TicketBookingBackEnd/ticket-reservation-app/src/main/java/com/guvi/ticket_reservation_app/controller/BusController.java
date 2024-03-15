package com.guvi.ticket_reservation_app.controller;

import com.guvi.ticket_reservation_app.model.BusResponse;
import com.guvi.ticket_reservation_app.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@PreAuthorize("hasAuthority('ROLE_USER')")
public class BusController {
    @Autowired
    private BusService busService;

    // Search Buses using from and to locations
    @GetMapping("buses")
    public ResponseEntity<List<BusResponse>> searchBuses(@RequestParam(name = "from") String from, @RequestParam(name = "to") String to) {
        List<BusResponse> busResponses = busService.searchBuses(from, to);

        return ResponseEntity.ok(busResponses);
    }
}
