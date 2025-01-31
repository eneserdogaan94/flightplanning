package com.example.flightplanning.service;

import com.example.flightplanning.entity.Airport;
import com.example.flightplanning.entity.Flight;
import com.example.flightplanning.repository.AirportRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AirportServiceTest {

    @Mock
    private AirportRepository mockAirportRepository;

    @InjectMocks
    private AirportService airportServiceUnderTest;

    @Test
    void testGetAllAirports() {
        // Setup
        // Configure AirportRepository.findAll(...).
        final Airport airport = new Airport();
        airport.setId(0);
        airport.setCity("city");
        airport.setAirportName("airportName");
        final Flight flight = new Flight();
        flight.setId(0);
        airport.setDepartureFlights(List.of(flight));
        final List<Airport> airports = List.of(airport);
        when(mockAirportRepository.findAll()).thenReturn(airports);

        // Run the test
        final List<Airport> result = airportServiceUnderTest.getAllAirports();

        // Verify the results
    }

    @Test
    void testGetAllAirports_AirportRepositoryReturnsNoItems() {
        // Setup
        when(mockAirportRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<Airport> result = airportServiceUnderTest.getAllAirports();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testGetById() {
        // Setup
        // Configure AirportRepository.findById(...).
        final Airport airport1 = new Airport();
        airport1.setId(0);
        airport1.setCity("city");
        airport1.setAirportName("airportName");
        final Flight flight = new Flight();
        flight.setId(0);
        airport1.setDepartureFlights(List.of(flight));
        final Optional<Airport> airport = Optional.of(airport1);
        when(mockAirportRepository.findById(0)).thenReturn(airport);

        // Run the test
        final Airport result = airportServiceUnderTest.getById(0);

        // Verify the results
    }

    @Test
    void testGetById_AirportRepositoryReturnsAbsent() {
        // Setup
        when(mockAirportRepository.findById(0)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> airportServiceUnderTest.getById(0)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void testGetByCity() {
        // Setup
        // Configure AirportRepository.findByCity(...).
        final Airport airport = new Airport();
        airport.setId(0);
        airport.setCity("city");
        airport.setAirportName("airportName");
        final Flight flight = new Flight();
        flight.setId(0);
        airport.setDepartureFlights(List.of(flight));
        when(mockAirportRepository.findByCity("city")).thenReturn(airport);

        // Run the test
        final Airport result = airportServiceUnderTest.getByCity("city");

        // Verify the results
    }

    @Test
    void testSaveAirport() {
        // Setup
        final Airport airport = new Airport();
        airport.setId(0);
        airport.setCity("city");
        airport.setAirportName("airportName");
        final Flight flight = new Flight();
        flight.setId(0);
        airport.setDepartureFlights(List.of(flight));

        // Configure AirportRepository.save(...).
        final Airport airport1 = new Airport();
        airport1.setId(0);
        airport1.setCity("city");
        airport1.setAirportName("airportName");
        final Flight flight1 = new Flight();
        flight1.setId(0);
        airport1.setDepartureFlights(List.of(flight1));
        when(mockAirportRepository.save(any(Airport.class))).thenReturn(airport1);

        // Run the test
        final Airport result = airportServiceUnderTest.saveAirport(airport);

        // Verify the results
    }
}
