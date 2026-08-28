package com.example.demo1;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

// аннотация которая задает бизнес логику приложения
@Service
public class ReservationService {

    private final Map<Long, Reservation> reservationMap = Map.of(
        3L, new Reservation(
            3L, 
            44L,
            90L,
            LocalDate.now(),
            LocalDate.now().plusDays(5),
            ReservationStatus.APPROVED
        ),4L, new Reservation(
            4L, 
            24L,
            100L,
            LocalDate.now(),
            LocalDate.now().plusDays(5),
            ReservationStatus.APPROVED
        ),7L, new Reservation(
            5L, 
            88L,
            101L,
            LocalDate.now(),
            LocalDate.now().plusDays(5),
            ReservationStatus.APPROVED
        )
    );
    
    public Reservation getReservationById(Long id) {
        if(!reservationMap.containsKey(id)) {
            throw new NoSuchElementException("Not found reservation !");
        }

        return reservationMap.get(id);
    }

    public List<Reservation> findAllReservations() {
        return reservationMap.values().stream().toList();
    }


}