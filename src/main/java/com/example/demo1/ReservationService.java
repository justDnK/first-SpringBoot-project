package com.example.demo1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

// аннотация которая задает бизнес логику приложения
@Service
public class ReservationService {

    private final Map<Long, Reservation> reservationMap;
    private final AtomicLong idCounter;

    public ReservationService() {
        reservationMap = new HashMap<>();
        idCounter = new AtomicLong();
    }
    
    public Reservation getReservationById(Long id) {
        if(!reservationMap.containsKey(id)) {
            throw new NoSuchElementException("Not found reservation !");
        }

        return reservationMap.get(id);
    }

    public List<Reservation> findAllReservations() {
        return reservationMap.values().stream().toList();
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
        
        var newReservation = new Reservation(
            idCounter.incrementAndGet(),
            reservationToCreate.userId(),
            reservationToCreate.roomId(),
            reservationToCreate.startDate(),
            reservationToCreate.endDate(),
            ReservationStatus.PENDING
        );
        reservationMap.put(newReservation.id(), newReservation);
        return newReservation;
    }

    public Long resultFunction(Long number) {
        return number * 100;
    }

    public Reservation updateReservation(Long id, Reservation reservationToUpdate) {
        if (!reservationMap.containsKey(id)) {
            throw new NoSuchElementException("id doesnt exist");
        }

        var reservation = reservationMap.get(id);

        if(reservation.status() != ReservationStatus.PENDING) {
            throw new IllegalStateException("Cannot modify reservation");
        }

        var updatedReservation = new Reservation(
            reservation.id(),
            reservationToUpdate.userId(),
            reservationToUpdate.roomId(),
            reservationToUpdate.startDate(),
            reservationToUpdate.endDate(),
            ReservationStatus.PENDING
        );

        reservationMap.put(reservation.id(), updatedReservation);
        return updatedReservation;
    }

    public void deleteReservation(Long id) {
        if (!reservationMap.containsKey(id)) {
            throw new NoSuchElementException("id doesnt exist");
        }
        
        reservationMap.remove(id);
    }

    // потверждение резервации 
    public Reservation approvedReservation(Long id) {
        if(!reservationMap.containsKey(id)) {
            throw new NoSuchElementException("this id doesnt exist");
        }
        var reservation = reservationMap.get(id);

        if(reservation.status() != ReservationStatus.PENDING) {
            throw new IllegalStateException("Cannot modify reservation");
        }
        var isConflict = isReservationConflict(reservation);

        if (isConflict) {
            throw new IllegalStateException("Cannot approve reservation because of conflict");
        }

        var reservationApproved = new Reservation(
            reservation.id(),
            reservation.userId(),
            reservation.roomId(),
            reservation.startDate(),
            reservation.endDate(),
            ReservationStatus.APPROVED
        );
        reservationMap.put(id, reservationApproved);

        return reservationApproved;
    }

    private boolean isReservationConflict(
        Reservation reservation
    ) {
        for (Reservation ExistingReservation : reservationMap.values()) {
            if(reservation.id().equals(ExistingReservation.id())) {
                continue;
            }

            if(reservation.roomId().equals(ExistingReservation.roomId())) {
                continue;
            }

            if (!ExistingReservation.status().equals(ReservationStatus.APPROVED)) {
                continue;
            }

            if(reservation.startDate().isBefore(ExistingReservation.endDate())
            && ExistingReservation.startDate().isBefore(reservation.endDate())) {
                return true;
            }
        }


        return false;
    }

}

