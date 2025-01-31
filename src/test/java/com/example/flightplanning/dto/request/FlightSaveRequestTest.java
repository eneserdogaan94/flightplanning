package com.example.flightplanning.dto.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class FlightSaveRequestTest {

    private FlightSaveRequest flightSaveRequestUnderTest;

    @BeforeEach
    void setUp() {
        flightSaveRequestUnderTest = new FlightSaveRequest();
    }

    @Test
    void testDepartureAirportIdGetterAndSetter() {
        final int departureAirportId = 0;
        flightSaveRequestUnderTest.setDepartureAirportId(departureAirportId);
        assertThat(flightSaveRequestUnderTest.getDepartureAirportId()).isEqualTo(departureAirportId);
    }

    @Test
    void testArrivalAirportIdGetterAndSetter() {
        final int arrivalAirportId = 0;
        flightSaveRequestUnderTest.setArrivalAirportId(arrivalAirportId);
        assertThat(flightSaveRequestUnderTest.getArrivalAirportId()).isEqualTo(arrivalAirportId);
    }

    @Test
    void testDepartureTimeGetterAndSetter() {
        final LocalDateTime departureTime = LocalDateTime.of(2020, 1, 1, 0, 0, 0);
        flightSaveRequestUnderTest.setDepartureTime(departureTime);
        assertThat(flightSaveRequestUnderTest.getDepartureTime()).isEqualTo(departureTime);
    }

    @Test
    void testArrivalTimeGetterAndSetter() {
        final LocalDateTime arrivalTime = LocalDateTime.of(2020, 1, 1, 0, 0, 0);
        flightSaveRequestUnderTest.setArrivalTime(arrivalTime);
        assertThat(flightSaveRequestUnderTest.getArrivalTime()).isEqualTo(arrivalTime);
    }
}
