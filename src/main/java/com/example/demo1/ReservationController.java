package com.example.demo1;

import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    //PathVariable — это аннотация, которая позволяет извлекать значения из URL‑пути и передавать их в метод контроллера. В данном случае мы извлекаем значение id из пути /reservations/{id} и передаем его в метод getReservationById.
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
    // то есть с помощью ResponseEntity мы можем обработать каждый исход события при конкретном http статусе. Например в случае 200 происходит это, а в случае 400 это и тд.
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

    // PutMpping — это аннотация, которая указывает, что метод контроллера будет обрабатывать HTTP‑запросы с методом PUT. В данном случае мы используем ее для обновления существующего бронирования по его идентификатору (id). Метод принимает два параметра: id бронирования из пути URL и объект Reservation из тела запроса, который содержит новые данные для обновления. Логирование используется для отслеживания вызова метода, а ResponseEntity позволяет вернуть обновленное бронирование с HTTP‑статусом 200 OK.
    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(
        @PathVariable Long id,
        @RequestBody Reservation reservationToUpdate
    ) {
        log.info("Called updateReservation id = {}, reservationToUpdate = {}", id, reservationToUpdate);
        var updated = reservationService.updateReservation(id, reservationToUpdate);
        return ResponseEntity.status(HttpStatus.OK)
            .body(updated);
    }

    // DeleteMapping — это аннотация, которая указывает, что метод контроллера будет обрабатывать HTTP‑запросы с методом DELETE. В данном случае мы используем ее для удаления существующего бронирования по его идентификатору (id). Метод принимает параметр id из пути URL, который указывает, какое бронирование нужно удалить. Логирование используется для отслеживания вызова метода, а ResponseEntity позволяет вернуть HTTP‑статус 200 OK без тела ответа, что означает успешное удаление ресурса.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(
        @PathVariable Long id
    ) {
        log.info("Called deleteReservation");
        try {
            reservationService.deleteReservation(id);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(400).build();
        }
    }



    @PostMapping("/{id}/approve")
    public ResponseEntity<Reservation> approvedReservation(
        @PathVariable("id") Long id
    ) {
        log.info("called approve reservation !");
        var reservation = reservationService.approvedReservation(id);
        return ResponseEntity.ok(reservation);
    }
}
