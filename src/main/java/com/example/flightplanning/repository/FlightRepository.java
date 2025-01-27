package com.example.flightplanning.repository;

import com.example.flightplanning.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findByDepartureAirportIdAndArrivalAirportIdAndDepartureTimeBetween(
            Long departureAirportId, Long arrivalAirportId, LocalDateTime start, LocalDateTime end);
}