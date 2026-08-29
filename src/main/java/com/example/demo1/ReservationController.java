package com.example.demo1;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


// аннотация с помощью которой мы говорим spring boot что этот класс обрабатывает http запрос
@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private static final Logger log = LoggerFactory.getLogger(ReservationController.class);
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        log.info("Получен запрос на бронирование с id={}", id);
        return ResponseEntity.status(200)
            .header("idk", "124")
            .body(reservationService.getReservationById(id));
    }

    @GetMapping()
    public ResponseEntity<List<Reservation>> getAllReservationById() {
        log.info("Call getAllReservationById");
        return ResponseEntity.ok()
        .header("test-header", "122")
        .body(reservationService.findAllReservations());
        // return reservationService.findAllReservations();
    }

    @GetMapping("/info")
    public String getMethodName() {
        return reservationService.showInfo();
    }
    
    // @RequestBody — это аннотация, которая позволяет удобно принимать данные из тела HTTP‑запроса и автоматически преобразовывать их в Java‑объекты. Это основа работы REST API в Spring Boot.
    // реализует метод POST, который принимает объект Reservation в теле запроса и создает новое бронирование, используя сервисный слой ReservationService. Логирование используется для отслеживания вызова метода.
    // ResponseEntity — это класс, который позволяет нам возвращать HTTP‑ответ с определенным статусом и телом. В данном случае мы возвращаем созданное бронирование с HTTP‑статусом 200 OK.
    @PostMapping("/create")
    public ResponseEntity<Reservation> createReservation(
        @RequestBody Reservation reservationToCreate
    ) {
        log.info("Called createReservation");
        return ResponseEntity.status(201)
            .header("test-header", "123")
            .body(reservationService.createReservation(reservationToCreate));
        // return reservationService.createReservation(reservationToCreate);
    }

    // @PostMapping
    // public Long showResult(
    //     @RequestBody Long number
    // ) {
    //     return reservationService.resultFunction(number);
    // }
}
