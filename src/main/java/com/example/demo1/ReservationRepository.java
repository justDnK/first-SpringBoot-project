package com.example.demo1;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/* 
repository - это слой, который отвечает за 
взаимодействие с базой данных. Он предоставляет 
методы для выполнения операций CRUD (создание, чтение, обновление, удаление) 
и других запросов к базе данных. В Spring Data JPA репозитории обычно создаются 
как интерфейсы, которые расширяют JpaRepository или другие интерфейсы 
репозиториев. Spring автоматически генерирует реализацию этих 
интерфейсов во время выполнения приложения.
*/

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    List<ReservationEntity> findAllByStatusIs(ReservationStatus status);

    @Modifying 
    @Query("""
            update ReservationEntity r
            set r.status = :status
        where r.id = :id
    """) 
    public void setStatus(
        @Param("id") Long id, 
        @Param("status") ReservationStatus CANCELLED);
}
