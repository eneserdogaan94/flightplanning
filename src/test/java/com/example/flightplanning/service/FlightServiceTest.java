package com.example.flightplanning.service;

import com.example.flightplanning.dto.request.FlightSaveRequest;
import com.example.flightplanning.entity.Airport;
import com.example.flightplanning.entity.Flight;
import com.example.flightplanning.entity.User;
import com.example.flightplanning.exception.FlightException;
import com.example.flightplanning.repository.FlightRepository;
import com.example.flightplanning.security.JwtUtil;
import org.jose4j.jwt.MalformedClaimException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FlightServiceTest {

    @Mock
    private FlightRepository mockFlightRepository;
    @Mock
    private AirportService mockAirportService;
    @Mock
    private UserService mockUserService;
    @Mock
    private JwtUtil mockJwtUtil;

    @InjectMocks
    private FlightService flightServiceUnderTest;

    @Test
    void testGetAllFlights() {
        // Setup
        // Configure FlightRepository.findAll(...).
        final Flight flight = new Flight();
        final Airport departureAirport = new Airport();
        departureAirport.setId(0);
        departureAirport.setCity("city");
        flight.setDepartureAirport(departureAirport);
        final Airport arrivalAirport = new Airport();
        arrivalAirport.setId(0);
        arrivalAirport.setCity("city");
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        flight.setArrivalTime(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Page<Flight> flights = new PageImpl<>(List.of(flight));
        when(mockFlightRepository.findAll(any(Pageable.class))).thenReturn(flights);

        // Run the test
        final Page<Flight> result = flightServiceUnderTest.getAllFlights(PageRequest.of(0, 1));

        // Verify the results
    }

    @Test
    void testGetAllFlights_FlightRepositoryReturnsNoItems() {
        // Setup
        when(mockFlightRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(Collections.emptyList()));

        // Run the test
        final Page<Flight> result = flightServiceUnderTest.getAllFlights(PageRequest.of(0, 1));

        // Verify the results
    }

    @Test
    void testSearchFlights() {
        // Setup
        // Configure FlightRepository.findByDepartureAirportIdAndArrivalAirportIdAndDepartureTimeBetween(...).
        final Flight flight = new Flight();
        final Airport departureAirport = new Airport();
        departureAirport.setId(0);
        departureAirport.setCity("city");
        flight.setDepartureAirport(departureAirport);
        final Airport arrivalAirport = new Airport();
        arrivalAirport.setId(0);
        arrivalAirport.setCity("city");
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        flight.setArrivalTime(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final List<Flight> flights = List.of(flight);
        when(mockFlightRepository.findByDepartureAirportIdAndArrivalAirportIdAndDepartureTimeBetween(0, 0,
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), LocalDateTime.of(2020, 1, 1, 0, 0, 0))).thenReturn(flights);

        // Run the test
        final List<Flight> result = flightServiceUnderTest.searchFlights(0, 0, LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0));

        // Verify the results
    }

    @Test
    void testSearchFlights_FlightRepositoryReturnsNoItems() {
        // Setup
        when(mockFlightRepository.findByDepartureAirportIdAndArrivalAirportIdAndDepartureTimeBetween(0, 0,
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), LocalDateTime.of(2020, 1, 1, 0, 0, 0)))
                .thenReturn(Collections.emptyList());

        // Run the test
        final List<Flight> result = flightServiceUnderTest.searchFlights(0, 0, LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0));

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testSearchDepartureAirportById() throws Exception {
        // Setup
        when(mockJwtUtil.getUsernameFromToken("token")).thenReturn("username");

        // Configure UserService.getUserByUsername(...).
        final User user = new User();
        user.setId(0);
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setCity("city");
        user.setUsername("username");
        when(mockUserService.getUserByUsername("username")).thenReturn(user);

        // Configure AirportService.getByCity(...).
        final Airport airport = new Airport();
        airport.setId(0);
        airport.setCity("city");
        airport.setAirportName("airportName");
        final Flight flight = new Flight();
        flight.setId(0);
        airport.setDepartureFlights(List.of(flight));
        when(mockAirportService.getByCity("city")).thenReturn(airport);

        // Configure FlightRepository.findByAirportIdOrderByDepartureTime(...).
        final Flight flight1 = new Flight();
        final Airport departureAirport = new Airport();
        departureAirport.setId(0);
        departureAirport.setCity("city");
        flight1.setDepartureAirport(departureAirport);
        final Airport arrivalAirport = new Airport();
        arrivalAirport.setId(0);
        arrivalAirport.setCity("city");
        flight1.setArrivalAirport(arrivalAirport);
        flight1.setDepartureTime(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        flight1.setArrivalTime(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final List<Flight> flights = List.of(flight1);
        when(mockFlightRepository.findByAirportIdOrderByDepartureTime(0)).thenReturn(flights);

        // Run the test
        final List<Flight> result = flightServiceUnderTest.searchDepartureAirportById("token");

        // Verify the results
    }

    @Test
    void testSearchDepartureAirportById_JwtUtilThrowsMalformedClaimException() throws Exception {
        // Setup
        when(mockJwtUtil.getUsernameFromToken("token")).thenThrow(MalformedClaimException.class);

        // Run the test
        assertThatThrownBy(() -> flightServiceUnderTest.searchDepartureAirportById("token"))
                .isInstanceOf(MalformedClaimException.class);
    }

    @Test
    void testSearchDepartureAirportById_FlightRepositoryReturnsNoItems() throws Exception {
        // Setup
        when(mockJwtUtil.getUsernameFromToken("token")).thenReturn("username");

        // Configure UserService.getUserByUsername(...).
        final User user = new User();
        user.setId(0);
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setCity("city");
        user.setUsername("username");
        when(mockUserService.getUserByUsername("username")).thenReturn(user);

        // Configure AirportService.getByCity(...).
        final Airport airport = new Airport();
        airport.setId(0);
        airport.setCity("city");
        airport.setAirportName("airportName");
        final Flight flight = new Flight();
        flight.setId(0);
        airport.setDepartureFlights(List.of(flight));
        when(mockAirportService.getByCity("city")).thenReturn(airport);

        when(mockFlightRepository.findByAirportIdOrderByDepartureTime(0)).thenReturn(Collections.emptyList());

        // Run the test
        final List<Flight> result = flightServiceUnderTest.searchDepartureAirportById("token");

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }


    @Test
    void testSaveFlight_ThrowsFlightException() {
        // Setup
        final FlightSaveRequest flightSaveRequest = new FlightSaveRequest();
        flightSaveRequest.setDepartureAirportId(0);
        flightSaveRequest.setArrivalAirportId(0);
        flightSaveRequest.setDepartureTime(LocalDateTime.of(2020, 1, 1, 10, 0, 0)); // 10:00
        flightSaveRequest.setArrivalTime(LocalDateTime.of(2020, 1, 1, 10, 30, 0)); // 10:30 (30 dk)

        // Configure AirportService.getById(...)
        final Airport airport = new Airport();
        airport.setId(0);
        airport.setCity("city");
        airport.setAirportName("airportName");
        final Flight flight = new Flight();
        flight.setId(0);
        airport.setDepartureFlights(List.of(flight));
        when(mockAirportService.getById(0)).thenReturn(airport);

        // Configure FlightRepository.findOverlappingFlights(...)
        final Flight flight1 = new Flight();
        final Airport departureAirport = new Airport();
        departureAirport.setId(0);
        departureAirport.setCity("city");
        flight1.setDepartureAirport(departureAirport);
        final Airport arrivalAirport = new Airport();
        arrivalAirport.setId(0);
        arrivalAirport.setCity("city");
        flight1.setArrivalAirport(arrivalAirport);
        flight1.setDepartureTime(LocalDateTime.of(2020, 1, 1, 10, 0, 0)); // 10:00
        flight1.setArrivalTime(LocalDateTime.of(2020, 1, 1, 10, 30, 0)); // 10:30
        final List<Flight> flights = List.of(flight1);
        lenient().when(mockFlightRepository.findOverlappingFlights(0, LocalDateTime.of(2020, 1, 1, 10, 0, 0),
                LocalDateTime.of(2020, 1, 1, 10, 30, 0))).thenReturn(flights);

        // Configure FlightRepository.findFlightsFromSameCity(...)
        final Flight flight2 = new Flight();
        final Airport departureAirport1 = new Airport();
        departureAirport1.setId(0);
        departureAirport1.setCity("city");
        flight2.setDepartureAirport(departureAirport1);
        final Airport arrivalAirport1 = new Airport();
        arrivalAirport1.setId(0);
        arrivalAirport1.setCity("city");
        flight2.setArrivalAirport(arrivalAirport1);
        flight2.setDepartureTime(LocalDateTime.of(2020, 1, 1, 10, 0, 0)); // 10:00
        flight2.setArrivalTime(LocalDateTime.of(2020, 1, 1, 10, 30, 0)); // 10:30
        final List<Flight> flights1 = List.of(flight2);
        lenient().when(mockFlightRepository.findFlightsFromSameCity("city", LocalDateTime.of(2020, 1, 1, 10, 0, 0),
                LocalDateTime.of(2020, 1, 1, 10, 30, 0))).thenReturn(flights1);

        // Run the test
        assertThatThrownBy(() -> flightServiceUnderTest.saveFlight(flightSaveRequest))
                .isInstanceOf(FlightException.class);
    }



    @Test
    void testSaveFlight_FlightRepositoryFindOverlappingFlightsReturnsNoItems() {
        // Setup
        final FlightSaveRequest flightSaveRequest = new FlightSaveRequest();
        flightSaveRequest.setDepartureAirportId(0);
        flightSaveRequest.setArrivalAirportId(0);
        flightSaveRequest.setDepartureTime(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        flightSaveRequest.setArrivalTime(LocalDateTime.of(2020, 1, 1, 0, 31, 0));

        // Configure AirportService.getById(...).
        final Airport airport = new Airport();
        airport.setId(0);
        airport.setCity("city");
        airport.setAirportName("airportName");
        final Flight flight = new Flight();
        flight.setId(0);
        airport.setDepartureFlights(List.of(flight));
        when(mockAirportService.getById(0)).thenReturn(airport);

        // Configure FlightRepository.findFlightsFromSameCity(...).
        final Flight flight1 = new Flight();
        final Airport departureAirport = new Airport();
        departureAirport.setId(0);
        departureAirport.setCity("city");
        flight1.setDepartureAirport(departureAirport);
        final Airport arrivalAirport = new Airport();
        arrivalAirport.setId(0);
        arrivalAirport.setCity("city");
        flight1.setArrivalAirport(arrivalAirport);
        flight1.setDepartureTime(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        flight1.setArrivalTime(LocalDateTime.of(2020, 1, 1, 0, 31, 0));

        // Run the test
        assertThatThrownBy(() -> flightServiceUnderTest.saveFlight(flightSaveRequest))
                .isInstanceOf(FlightException.class);
    }
}
