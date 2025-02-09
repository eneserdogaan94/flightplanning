package com.example.flightplanning.controller;

import com.example.flightplanning.dto.request.FlightSaveRequest;
import com.example.flightplanning.dto.request.FlightSearchRequest;
import com.example.flightplanning.entity.Flight;
import com.example.flightplanning.exception.FlightException;
import com.example.flightplanning.security.JwtUtil;
import com.example.flightplanning.service.AirportService;
import com.example.flightplanning.service.FlightService;
import com.example.flightplanning.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jose4j.jwt.MalformedClaimException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private AirportService airportService;

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Tag(name = "Flight Management", description = "Operations related to flight management")
    public Page<Flight> getAllFlights(
            @PageableDefault(size = 5, sort = "departureTime", direction = Sort.Direction.ASC) Pageable pageable) {
        return flightService.getAllFlights(pageable);
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Flight> searchFlights(@RequestParam Integer departureAirportId,
                                      @RequestParam Integer arrivalAirportId,
                                      @RequestParam LocalDateTime start,
                                      @RequestParam LocalDateTime end) {
        return flightService.searchFlights(departureAirportId, arrivalAirportId, start, end);
    }

    @GetMapping("/searchById")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Flight> searchFlights(@RequestHeader("Authorization") String token) throws MalformedClaimException {
        return flightService.searchDepartureAirportById(token);
    }

    @PostMapping("/saveFlight")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveFlight(@RequestHeader("Authorization") String token, @RequestBody FlightSaveRequest flightSaveRequest) {
        try {
            Flight savedFlight = flightService.saveFlight(flightSaveRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedFlight);
        } catch (FlightException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/filter")
    public ResponseEntity<List<Flight>> filterFlights(@RequestHeader("Authorization") String token, @RequestBody FlightSearchRequest request) throws MalformedClaimException {
        List<Flight> flights = flightService.searchFlights(token, request);
        return ResponseEntity.ok(flights);
    }
}
