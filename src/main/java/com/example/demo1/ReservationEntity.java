package com.example.demo1;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/*
Entity - это аннотация, которая указывает, что данный класс 
является сущностью (сущность это объект, который отображается в таблице базы данных) 
и будет отображаться в таблицу базы данных. 
Каждое поле класса будет соответствовать столбцу в таблице.
*/

@Table(name = "reservations")
@Entity
public class ReservationEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user id")
    private Long userId;

    @Column(name = "room id")
    private Long roomId;

    @Column(name = "start date")
    private LocalDate startDate; 

    @Column(name = "end date")
    private LocalDate endDate; 

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ReservationStatus status;

    public ReservationEntity() {}

    public ReservationEntity (
        Long id,
        Long userId, 
        Long roomId,
        LocalDate startDate,
        LocalDate endDate,
        ReservationStatus status
    ) {
        this.id = id;
        this.userId = userId;
        this.roomId = roomId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getUserId() {
        return userId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }
    public Long getRoomId() {
        return roomId;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }

    
    public void setStatus(ReservationStatus status) {
        this.status = status;
    }
    public ReservationStatus getStatus() {
        return status;
    }
}
