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

    public List<Flight> searchFlights(Integer departureAirportId, Integer arrivalAirportId, LocalDateTime start, LocalDateTime end) {
        return flightRepository.findByDepartureAirportIdAndArrivalAirportIdAndDepartureTimeBetween(departureAirportId, arrivalAirportId, start, end);
    }

    public Flight saveFlight(Flight flight) {
        if (checkFlightOverlap(flight)) {
            throw new RuntimeException("Flight time overlaps with another flight in the same airport");
        }
        return flightRepository.save(flight);
    }

    private boolean checkFlightOverlap(Flight flight) {
        List<Flight> overlappingFlights = flightRepository.findOverlappingFlights(
                flight.getDepartureAirport().getId(),
                flight.getDepartureTime(),
                flight.getArrivalTime()
        );
        return !overlappingFlights.isEmpty();
    }
}
