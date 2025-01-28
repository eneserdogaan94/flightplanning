package com.example.flightplanning.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "airports")
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false, unique = true)
    private String airportName;

    @OneToMany(mappedBy = "departureAirport", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Flight> departureFlights;

    @OneToMany(mappedBy = "arrivalAirport", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Flight> arrivalFlights;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    public List<Flight> getDepartureFlights() {
        return departureFlights;
    }

    public void setDepartureFlights(List<Flight> departureFlights) {
        this.departureFlights = departureFlights;
    }

    public List<Flight> getArrivalFlights() {
        return arrivalFlights;
    }

    public void setArrivalFlights(List<Flight> arrivalFlights) {
        this.arrivalFlights = arrivalFlights;
    }
}
