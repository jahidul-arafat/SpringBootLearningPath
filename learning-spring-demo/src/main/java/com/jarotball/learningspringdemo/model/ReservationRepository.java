package com.jarotball.learningspringdemo.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;


@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // method to find all reservations by reservation date
    Iterable<Reservation> findReservationByReservationDate(Date date);
}
