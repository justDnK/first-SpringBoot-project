package com.example.demo1;

import org.springframework.data.jpa.repository.JpaRepository;


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
    
}
