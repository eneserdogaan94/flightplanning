package com.example.flightplanning.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class AirportTest {

    @Mock
    private List<Flight> mockDepartureFlights;
    @Mock
    private List<Flight> mockArrivalFlights;

    private Airport airportUnderTest;

    @BeforeEach
    void setUp() {
        airportUnderTest = new Airport();
        airportUnderTest.setDepartureFlights(mockDepartureFlights);
        airportUnderTest.setArrivalFlights(mockArrivalFlights);
    }

    @Test
    void testIdGetterAndSetter() {
        final Integer id = 0;
        airportUnderTest.setId(id);
        assertThat(airportUnderTest.getId()).isEqualTo(id);
    }

    @Test
    void testCityGetterAndSetter() {
        final String city = "city";
        airportUnderTest.setCity(city);
        assertThat(airportUnderTest.getCity()).isEqualTo(city);
    }

    @Test
    void testAirportNameGetterAndSetter() {
        final String airportName = "airportName";
        airportUnderTest.setAirportName(airportName);
        assertThat(airportUnderTest.getAirportName()).isEqualTo(airportName);
    }

    @Test
    void testGetDepartureFlights() {
        assertThat(airportUnderTest.getDepartureFlights()).isEqualTo(mockDepartureFlights);
    }

    @Test
    void testGetArrivalFlights() {
        assertThat(airportUnderTest.getArrivalFlights()).isEqualTo(mockArrivalFlights);
    }
}
