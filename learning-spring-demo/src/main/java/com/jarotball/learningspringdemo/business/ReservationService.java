package com.jarotball.learningspringdemo.business;

import com.jarotball.learningspringdemo.model.*;
import org.springframework.stereotype.Service;

import java.util.*;
/* @Service It enables the class to be registered as a bean and participate in dependency injection.*/
@Service
public class ReservationService {
    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final ReservationRepository reservationRepository;

    // Constructor Injection - a part of Dependency Injection
    public ReservationService(RoomRepository roomRepository, GuestRepository guestRepository, ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.reservationRepository = reservationRepository;
    }

    /*Custom Method - Business Logic*/
    /*
    * Expected to return a List of RoomReservations for a given date.
    * Output
    * ------
    *   RoomReservation{roomId=6, guestId=0, roomName='Piccadilly', roomNumber='P6', firstName='null', lastName='null', date=null}
        RoomReservation{roomId=7, guestId=0, roomName='Cambridge', roomNumber='C1', firstName='null', lastName='null', date=null}
        RoomReservation{roomId=8, guestId=200, roomName='Cambridge', roomNumber='C2', firstName='Judith', lastName='Young', date=Sat Jan 01 00:00:00 BDT 2022}
    *
    * */
    public List<RoomReservation> getRoomReservationsForDate(Date date) {
        Iterable<Room> rooms = this.roomRepository.findAll();
        Map<Long, RoomReservation> roomReservationMap = new HashMap();
        // for each room create a room reservation object and add the room details into this object
        // later, add this roomReservation into the Map with key RoomId
        rooms.forEach(room -> {
            RoomReservation roomReservation = new RoomReservation();
            roomReservation.setRoomId(room.getRoomId());
            roomReservation.setRoomName(room.getName());
            roomReservation.setRoomNumber(room.getRoomNumber());
            roomReservationMap.put(room.getRoomId(), roomReservation);
        });

        // get the reservations for the date
        // and for each reservation, check if this revervation is exists in the roomReservationMap
        Iterable<Reservation> reservations = this.reservationRepository.findReservationByReservationDate(new java.sql.Date(date.getTime()));
        reservations.forEach(reservation -> {
            RoomReservation roomReservation = roomReservationMap.get(reservation.getRoomId());
            roomReservation.setDate(date);
            Guest guest = this.guestRepository.findById(reservation.getGuestId()).get();
            roomReservation.setFirstName(guest.getFirstName());
            roomReservation.setLastName(guest.getLastName());
            roomReservation.setGuestId(guest.getId());
        });
        List<RoomReservation> roomReservations = new ArrayList<>();
        for (Long id : roomReservationMap.keySet()) {
            roomReservations.add(roomReservationMap.get(id));
        }

        return roomReservations;
    }

}
