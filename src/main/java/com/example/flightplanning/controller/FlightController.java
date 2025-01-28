package com.example.flightplanning.controller;

import com.example.flightplanning.entity.Flight;
import com.example.flightplanning.service.FlightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Tag(name = "Flight Management", description = "Operations related to flight management")
    public List<Flight> getAllFlights() {
        return flightService.getAllFlights();
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Flight> searchFlights(@RequestParam Integer departureAirportId,
                                      @RequestParam Integer arrivalAirportId,
                                      @RequestParam LocalDateTime start,
                                      @RequestParam LocalDateTime end) {
        return flightService.searchFlights(departureAirportId, arrivalAirportId, start, end);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Flight> saveFlight(@RequestBody Flight flight) {
        Flight savedFlight = flightService.saveFlight(flight);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFlight);
    }
}
