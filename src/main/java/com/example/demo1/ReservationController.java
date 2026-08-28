package com.example.demo1;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

// аннотация с помощью которой мы говорим spring boot что этот класс обрабатывает http запрос
@RestController
public class ReservationController {

    private static final Logger log = LoggerFactory.getLogger(ReservationController.class);
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/{id}")
    public Reservation getReservationById(@PathVariable Long id) {
        log.info("Получен запрос на бронирование с id={}", id);
        return reservationService.getReservationById(id);
    }

    @GetMapping()
    public List<Reservation> getAllReservationById() {
        log.info("Call getAllReservationById");
        return reservationService.findAllReservations();
    }
}
