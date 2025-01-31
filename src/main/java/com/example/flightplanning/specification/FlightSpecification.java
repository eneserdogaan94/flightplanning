package com.example.flightplanning.specification;

import com.example.flightplanning.entity.Flight;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FlightSpecification {

    public static Specification<Flight> filterFlights(int departureAirportId, int arrivalAirportId, LocalDate departureDate) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (departureAirportId != 0) {
                predicates.add(criteriaBuilder.equal(root.get("departureAirport").get("id"), departureAirportId));
            }
            if (arrivalAirportId != 0) {
                predicates.add(criteriaBuilder.equal(root.get("arrivalAirport").get("id"), arrivalAirportId));
            }

            if (departureDate != null) {
                LocalDate startOfDay = LocalDate.from(departureDate.atStartOfDay());
                LocalDate endOfDay = LocalDate.from(departureDate.atTime(23, 59, 59));
                predicates.add(criteriaBuilder.between(root.get("departureTime"), startOfDay, endOfDay));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
