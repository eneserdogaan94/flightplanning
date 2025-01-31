package com.example.flightplanning.repository;

import com.example.flightplanning.entity.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Integer>, JpaSpecificationExecutor<Flight> {

    @Query("SELECT f FROM Flight f WHERE f.departureAirport.id = :airportId " +
            "AND (f.departureTime BETWEEN :startTime AND :endTime " +
            "OR f.arrivalTime BETWEEN :startTime AND :endTime)")
    List<Flight> findOverlappingFlights(Integer airportId, LocalDateTime startTime, LocalDateTime endTime);

    List<Flight> findByDepartureAirportIdAndArrivalAirportIdAndDepartureTimeBetween(
            Integer departureAirportId, Integer arrivalAirportId, LocalDateTime start, LocalDateTime end);

    @Query("SELECT f FROM Flight f WHERE f.departureAirport.id = :airportId OR f.arrivalAirport.id = :airportId ORDER BY f.departureTime ASC")
    List<Flight> findByAirportIdOrderByDepartureTime(@Param("airportId") Integer airportId);

    @Query("SELECT f FROM Flight f WHERE f.departureAirport.city = :city " +
            "AND f.departureTime BETWEEN :startTime AND :endTime")
    List<Flight> findFlightsFromSameCity(String city, LocalDateTime startTime, LocalDateTime endTime);

    Page<Flight> findAll(Pageable pageable);

}
