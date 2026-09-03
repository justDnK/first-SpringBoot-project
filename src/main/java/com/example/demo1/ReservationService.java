package com.example.demo1;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

// аннотация которая задает бизнес логику приложения
@Service
public class ReservationService {

    private final Demo1Application demo1Application;


    // DB
    private final ReservationRepository repository;
    //
    
    public ReservationService(ReservationRepository repository, Demo1Application demo1Application) {
        this.repository = repository;

        this.demo1Application = demo1Application;
    }
    
    public Reservation getReservationById(Long id) {
        // if(!reservationMap.containsKey(id)) {
        //     throw new NoSuchElementException("Not found reservation !");
        // }

        /*  функции jparepository такие как findById() возвращают тип данных Optional, 
        так как запрос может взять значения из несуществуещего субъекта базы данных и вернуть empty,
        для этого и нужен Optional, который к тому же имеет встроенные функции для проверки переменный.
        То есть Optional это оберточный класс для переменной который имеет свои встроенные методы, 
        такие как orElseThrow() 
        */

        ReservationEntity reservationEntity = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(
                "Not found reservation !"
            ));

        return toDomainReservation(reservationEntity);
    }

    // updated
    public List<Reservation> findAllReservations() {
        List<ReservationEntity> allEnteties = repository.findAll();

        /*
        reservationList - это список объектов Reservation, который 
        создается путем преобразования каждого объекта ReservationEntity 
        из списка allEnteties в объект Reservation с помощью метода map().
        */
        List<Reservation> reservationList = allEnteties.stream()
            .map(this::toDomainReservation)
            .toList();

        return reservationList;
    }

    public String showInfo() {
        return "Hello Daniel !";
    }

    public Reservation createReservation(Reservation reservationToCreate) {
        if(reservationToCreate.id() != null) {
            throw new IllegalArgumentException("Id must be null when creating a new reservation.");
        }

        if(reservationToCreate.status() != null) {
            throw new IllegalArgumentException("Status must be null when creating a new reservation.");
        }
        
        var newReservation = new ReservationEntity(
            null,
            reservationToCreate.userId(),
            reservationToCreate.roomId(),
            reservationToCreate.startDate(),
            reservationToCreate.endDate(),
            ReservationStatus.PENDING
        );
        // reservationMap.put(newReservation.id(), newReservation);
        var savedEntity = repository.save(newReservation);
        return toDomainReservation(savedEntity);
    }

    public Long resultFunction(Long number) {
        return number * 100;
    }



    public Reservation updateReservation(Long id, Reservation reservationToUpdate) {

        if(!repository.existsById(id)) {
            throw new NoSuchElementException("id doesnt exist");
        }

        var reservationEntity = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("id doesnt exist"));


        if(reservationEntity.getStatus() != ReservationStatus.PENDING) {
            throw new IllegalStateException("Cannot modify reservation");
        }

        var ReservationToSave = new ReservationEntity(
            reservationEntity.getId(),
            reservationToUpdate.userId(),
            reservationToUpdate.roomId(),
            reservationToUpdate.startDate(),
            reservationToUpdate.endDate(),
            ReservationStatus.PENDING
        );
        var updatedReservation = repository.save(ReservationToSave);
        
        return toDomainReservation(updatedReservation);
    }



    public void deleteReservation(Long id) {
        if (!repository.existsById(id)) {
            throw new NoSuchElementException("id doesnt exist");
        }
        
        repository.deleteById(id);
    }

    // потверждение резервации 
    public Reservation approvedReservation(Long id) {
        var reservationEntity = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("id doesnt exist"));


        if(reservationEntity.getStatus() != ReservationStatus.PENDING) {
            throw new IllegalStateException("Cannot modify reservation");
        }
        var isConflict = isReservationConflict(reservationEntity);

        if (isConflict) {
            throw new IllegalStateException("Cannot approve reservation because of conflict");
        }

        reservationEntity.setStatus(ReservationStatus.APPROVED);
        repository.save(reservationEntity);

        return toDomainReservation(reservationEntity);
    }

    private boolean isReservationConflict(
        ReservationEntity reservation
    ) {
        var allReservations = repository.findAll();

        for (ReservationEntity ExistingReservation : allReservations) {
            if(reservation.getId().equals(ExistingReservation.getId())) {
                continue;
            }

            if(reservation.getRoomId().equals(ExistingReservation.getRoomId())) {
                continue;
            }

            if (!ExistingReservation.getStatus().equals(ReservationStatus.APPROVED)) {
                continue;
            }

            if(reservation.getStartDate().isBefore(ExistingReservation.getEndDate())
            && ExistingReservation.getStartDate().isBefore(reservation.getEndDate())) {
                return true;
            }
        }


        return false;
    }



    private Reservation toDomainReservation(
        ReservationEntity reservation
    ) {
        return new Reservation(
                reservation.getId(),
                reservation.getUserId(),
                reservation.getRoomId(),
                reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.getStatus()
        );
    }

}

