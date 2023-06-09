package com.jarotball.learningspringdemo.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
    Iterable<Guest> findGuestByCountry(String country);
    List<Guest> findGuestByFirstName(String firstName);
}
