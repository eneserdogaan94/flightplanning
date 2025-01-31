package com.example.flightplanning.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class FlightTest {

    @Mock
    private Airport mockDepartureAirport;
    @Mock
    private Airport mockArrivalAirport;

    private Flight flightUnderTest;

    @BeforeEach
    void setUp() {
        flightUnderTest = new Flight();
        flightUnderTest.setDepartureAirport(mockDepartureAirport);
        flightUnderTest.setArrivalAirport(mockArrivalAirport);
    }

    @Test
    void testIdGetterAndSetter() {
        final Integer id = 0;
        flightUnderTest.setId(id);
        assertThat(flightUnderTest.getId()).isEqualTo(id);
    }

    @Test
    void testGetDepartureAirport() {
        assertThat(flightUnderTest.getDepartureAirport()).isEqualTo(mockDepartureAirport);
    }

    @Test
    void testGetArrivalAirport() {
        assertThat(flightUnderTest.getArrivalAirport()).isEqualTo(mockArrivalAirport);
    }

    @Test
    void testDepartureTimeGetterAndSetter() {
        final LocalDateTime departureTime = LocalDateTime.of(2020, 1, 1, 0, 0, 0);
        flightUnderTest.setDepartureTime(departureTime);
        assertThat(flightUnderTest.getDepartureTime()).isEqualTo(departureTime);
    }

    @Test
    void testArrivalTimeGetterAndSetter() {
        final LocalDateTime arrivalTime = LocalDateTime.of(2020, 1, 1, 0, 0, 0);
        flightUnderTest.setArrivalTime(arrivalTime);
        assertThat(flightUnderTest.getArrivalTime()).isEqualTo(arrivalTime);
    }
}
