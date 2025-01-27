package com.example.flightplanning.service;

import com.example.flightplanning.entity.Flight;
import com.example.flightplanning.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    public List<Flight> searchFlights(Long departureAirportId, Long arrivalAirportId, LocalDateTime start, LocalDateTime end) {
        return flightRepository.findByDepartureAirportIdAndArrivalAirportIdAndDepartureTimeBetween(departureAirportId, arrivalAirportId, start, end);
    }

    public Flight saveFlight(Flight flight) {
        return flightRepository.save(flight);
    }
}