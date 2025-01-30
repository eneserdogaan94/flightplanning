package com.example.flightplanning.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FlightException extends RuntimeException {
    public FlightException(String message) {
        super(message);
    }
}