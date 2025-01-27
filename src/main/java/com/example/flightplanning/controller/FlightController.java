package com.example.flightplanning.controller;

import com.example.flightplanning.entity.Flight;
import com.example.flightplanning.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @GetMapping
    public List<Flight> getAllFlights() {
        return flightService.getAllFlights();
    }

    @GetMapping("/search")
    public List<Flight> searchFlights(@RequestParam Long departureAirportId,
                                      @RequestParam Long arrivalAirportId,
                                      @RequestParam LocalDateTime start,
                                      @RequestParam LocalDateTime end) {
        return flightService.searchFlights(departureAirportId, arrivalAirportId, start, end);
    }

    @PostMapping
    public ResponseEntity<Flight> saveFlight(@RequestBody Flight flight) {
        Flight savedFlight = flightService.saveFlight(flight);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFlight);
    }
}