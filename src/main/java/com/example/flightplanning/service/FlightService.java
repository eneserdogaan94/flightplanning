package com.example.flightplanning.service;

import com.example.flightplanning.dto.request.FlightSaveRequest;
import com.example.flightplanning.dto.request.FlightSearchRequest;
import com.example.flightplanning.entity.Airport;
import com.example.flightplanning.entity.Flight;
import com.example.flightplanning.exception.FlightException;
import com.example.flightplanning.repository.FlightRepository;
import com.example.flightplanning.security.JwtUtil;
import com.example.flightplanning.specification.FlightSpecification;
import org.jose4j.jwt.MalformedClaimException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private AirportService airportService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    public Page<Flight> getAllFlights(Pageable pageable) {
        return flightRepository.findAll(pageable);
    }

    public List<Flight> searchFlights(Integer departureAirportId, Integer arrivalAirportId, LocalDateTime start, LocalDateTime end) {
        return flightRepository.findByDepartureAirportIdAndArrivalAirportIdAndDepartureTimeBetween(departureAirportId, arrivalAirportId, start, end);
    }

    public List<Flight> searchDepartureAirportById(String token) throws MalformedClaimException {
        String username = jwtUtil.getUsernameFromToken(token.replace("Bearer ", ""));
        String city = userService.getUserByUsername(username).getCity();
        Integer airportId = airportService.getByCity(city).getId();
        return flightRepository.findByAirportIdOrderByDepartureTime(airportId);
    }

    public List<Flight> searchFlights(String token, FlightSearchRequest flightSearchRequest) throws MalformedClaimException {
        String username = jwtUtil.getUsernameFromToken(token.replace("Bearer ", ""));
        String city = userService.getUserByUsername(username).getCity();
        Integer userAirportId = airportService.getByCity(city).getId();
        Integer departureAirportId = 0;
        Integer arrivalAirportId = 0;
        if (!flightSearchRequest.getDepartureCity().isEmpty()) {
            departureAirportId = airportService.getByCity(flightSearchRequest.getDepartureCity()).getId();
        }
        if (!flightSearchRequest.getArrivalCity().isEmpty()) {
            arrivalAirportId = airportService.getByCity(flightSearchRequest.getArrivalCity()).getId();
        }
        Specification<Flight> spec = FlightSpecification.filterFlights(
                departureAirportId,
                arrivalAirportId,
                flightSearchRequest.getDepartureDate(),
                userAirportId
        );
        return flightRepository.findAll(spec, Sort.by(Sort.Direction.ASC, "departureTime"));
    }

    public Flight saveFlight(FlightSaveRequest flightSaveRequest) {
        Airport departureAirport = airportService.getById(flightSaveRequest.getDepartureAirportId());
        Airport arrivalAirport = airportService.getById(flightSaveRequest.getArrivalAirportId());
        Flight flight = new Flight();
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalTime(flightSaveRequest.getArrivalTime());
        flight.setDepartureTime(flightSaveRequest.getDepartureTime());
        checkFlightOverlap(flight);
        return flightRepository.save(flight);
    }

    private void checkFlightOverlap(Flight flight) {
        LocalDateTime departureStart = flight.getDepartureTime().minusMinutes(30);
        LocalDateTime departureEnd = flight.getDepartureTime().plusMinutes(30);
        LocalDateTime arrivalStart = flight.getArrivalTime().minusMinutes(30);
        LocalDateTime arrivalEnd = flight.getArrivalTime().plusMinutes(30);
        LocalDateTime departureTime = flight.getDepartureTime();
        LocalDateTime arrivalTime = flight.getArrivalTime();

        if (departureTime == null || arrivalTime == null) {
            throw new FlightException("Kalkış ve varış zamanı belirtilmelidir.");
        }

        // 1. Kural: Varış zamanı, kalkış zamanından küçük olamaz.
        if (arrivalTime.isBefore(departureTime)) {
            throw new FlightException("Varış zamanı, kalkış zamanından küçük olamaz.");
        }

        // 2. Kural: Kalkış ve varış havalimanı aynı olamaz.
        if (flight.getDepartureAirport().getId().equals(flight.getArrivalAirport().getId())) {
            throw new FlightException("Kalkış ve varış havalimanı aynı olamaz.");
        }

        // 3. Kural: Kalkış zamanı geçmişte olamaz.
        if (departureTime.isBefore(LocalDateTime.now())) {
            throw new FlightException("Kalkış zamanı geçmişte olamaz.");
        }
        List<Flight> overlappingFlights = flightRepository.findOverlappingFlights(
                flight.getDepartureAirport().getId(), departureStart, departureEnd);

        overlappingFlights.addAll(flightRepository.findOverlappingFlights(
                flight.getArrivalAirport().getId(), arrivalStart, arrivalEnd));

        List<Flight> cityOverlaps = flightRepository.findFlightsFromSameCity(
                flight.getDepartureAirport().getCity(), departureStart, departureEnd);

        // 4. Kural: İniş ve kalkış sürelerinin arası 30 dakika olmalıdır.
        if (!overlappingFlights.isEmpty() || !cityOverlaps.isEmpty()) {
            throw new FlightException("Uçuşlarda çakışma var. Lütfen iniş ve kalkış sürelerinin arası 30 dakika olmalıdır.");
        }

    }

}
