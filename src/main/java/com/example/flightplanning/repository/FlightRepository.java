package com.example.flightplanning.repository;

import com.example.flightplanning.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query("SELECT f FROM Flight f WHERE f.departureAirport.id = :airportId " +
            "AND (f.departureTime BETWEEN :startTime AND :endTime " +
            "OR f.arrivalTime BETWEEN :startTime AND :endTime)")
    List<Flight> findOverlappingFlights(Long airportId, LocalDateTime startTime, LocalDateTime endTime);

    List<Flight> findByDepartureAirportIdAndArrivalAirportIdAndDepartureTimeBetween(
            Long departureAirportId, Long arrivalAirportId, LocalDateTime start, LocalDateTime end);
}
