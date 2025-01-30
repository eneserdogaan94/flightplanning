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
    public List<Flight> searchDepartureAirportById(Integer departureAirportId) {
        return flightRepository.findByDepartureAirportId(departureAirportId);
    }


    public Flight saveFlight(Flight flight) {
        if (checkFlightOverlap(flight)) {
            throw new RuntimeException("Uçuşlarda çakışma var.Lütfen işlem yaptığınız havaalanlarında iniş ve varış sürelerine dikkat ediniz. Yarım saatten fazla olması gerekmektedir.");
        }
        return flightRepository.save(flight);
    }

    private boolean checkFlightOverlap(Flight flight) {
        LocalDateTime departureStart = flight.getDepartureTime().minusMinutes(30);
        LocalDateTime departureEnd = flight.getDepartureTime().plusMinutes(30);
        LocalDateTime arrivalStart = flight.getArrivalTime().minusMinutes(30);
        LocalDateTime arrivalEnd = flight.getArrivalTime().plusMinutes(30);

        List<Flight> overlappingFlights = flightRepository.findOverlappingFlights(
                flight.getDepartureAirport().getId(), departureStart, departureEnd);

        overlappingFlights.addAll(flightRepository.findOverlappingFlights(
                flight.getArrivalAirport().getId(), arrivalStart, arrivalEnd));

        List<Flight> cityOverlaps = flightRepository.findFlightsFromSameCity(
                flight.getDepartureAirport().getCity(), departureStart, departureEnd);

        return !overlappingFlights.isEmpty() || !cityOverlaps.isEmpty();
    }

}
