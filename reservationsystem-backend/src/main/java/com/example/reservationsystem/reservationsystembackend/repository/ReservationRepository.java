package com.example.reservationsystem.reservationsystembackend.repository;

import com.example.reservationsystem.reservationsystembackend.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
