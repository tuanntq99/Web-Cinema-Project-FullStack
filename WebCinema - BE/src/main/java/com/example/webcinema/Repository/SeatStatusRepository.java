package com.example.webcinema.Repository;

import com.example.webcinema.Entity.Enum.ESeatStatus;
import com.example.webcinema.Entity.SeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeatStatusRepository extends JpaRepository<SeatStatus, Integer> {
    Optional<SeatStatus> findByNameStatus(ESeatStatus name);
}