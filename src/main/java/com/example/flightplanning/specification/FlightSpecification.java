package com.example.flightplanning.specification;

import com.example.flightplanning.entity.Flight;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FlightSpecification {

    public static Specification<Flight> filterFlights(int departureAirportId, int arrivalAirportId, LocalDate departureDate, Integer userAirportId) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Eğer departureAirportId seçildiyse filtre uygula
            if (departureAirportId != 0) {
                predicates.add(criteriaBuilder.equal(root.get("departureAirport").get("id"), departureAirportId));
            }

            // Eğer arrivalAirportId seçildiyse filtre uygula
            if (arrivalAirportId != 0) {
                predicates.add(criteriaBuilder.equal(root.get("arrivalAirport").get("id"), arrivalAirportId));
            }

            // Eğer kullanıcı havaalanı ID'si verildiyse, ya kalkış ya da varış yeriyle eşleşmeli
            if (userAirportId != null && userAirportId != 0) {
                Predicate departureMatch = criteriaBuilder.equal(root.get("departureAirport").get("id"), userAirportId);
                Predicate arrivalMatch = criteriaBuilder.equal(root.get("arrivalAirport").get("id"), userAirportId);
                predicates.add(criteriaBuilder.or(departureMatch, arrivalMatch)); // Kullanıcının havaalanı ya kalkış ya da varış yeri olmalı
            }

            // Tarih aralığına göre filtreleme (Eğer tarih seçildiyse)
            if (departureDate != null) {
                LocalDateTime startOfDay = departureDate.atStartOfDay();
                LocalDateTime endOfDay = departureDate.atTime(23, 59, 59);
                predicates.add(criteriaBuilder.between(root.get("departureTime"), startOfDay, endOfDay));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
