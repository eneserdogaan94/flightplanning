package com.example.flightplanning.controller;

import com.example.flightplanning.dto.request.FlightSaveRequest;
import com.example.flightplanning.entity.Airport;
import com.example.flightplanning.entity.Flight;
import com.example.flightplanning.entity.User;
import com.example.flightplanning.security.JwtUtil;
import com.example.flightplanning.service.AirportService;
import com.example.flightplanning.service.FlightService;
import com.example.flightplanning.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jose4j.jwt.MalformedClaimException;
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

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private AirportService airportService;

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

    @GetMapping("/searchById")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Flight> searchFlights(@RequestHeader("Authorization") String token) throws MalformedClaimException {
        String username = jwtUtil.getUsernameFromToken(token.replace("Bearer ", ""));
        String city = userService.getUserByUsername(username).getCity();
        Integer airportId = airportService.getByCity(city).getId();
        return flightService.searchDepartureAirportById(airportId);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Flight> saveFlight(@RequestBody FlightSaveRequest flightSaveRequest) {
        Airport departureAirport = airportService.getById(flightSaveRequest.getDepartureAirportId());
        Airport arrivalAirport = airportService.getById(flightSaveRequest.getArrivalAirportId());
        Flight flight=new Flight();
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalTime(flightSaveRequest.getArrivalTime());
        flight.setDepartureTime(flightSaveRequest.getDepartureTime());
        Flight savedFlight = flightService.saveFlight(flight);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFlight);
    }
}
