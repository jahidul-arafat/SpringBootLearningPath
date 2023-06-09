package com.jarotball.learningspringdemo.util;

import com.jarotball.learningspringdemo.business.ReservationService;
import com.jarotball.learningspringdemo.business.RoomReservation;
import com.jarotball.learningspringdemo.model.*;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

/*
Overall, the AppStartupEvent class with the @Component annotation and
ApplicationListener implementation allows you to define custom logic to be executed
when the application is fully initialized and ready to run.
 */
@Component
public class AppStartupEvent implements ApplicationListener<ApplicationReadyEvent> {
    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationService reservationService;
    private final DateUtils dateUtils;

    public AppStartupEvent(RoomRepository roomRepository, GuestRepository guestRepository, ReservationRepository reservationRepository, ReservationService reservationService, DateUtils dateUtils) {
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.reservationRepository = reservationRepository;
        this.reservationService = reservationService;
        this.dateUtils = dateUtils;
    }

    // method to print all rooms
    public void printAllRooms() {
        Iterable<Room> rooms = roomRepository.findAll();
        rooms.forEach(System.out::println);
    }

    // method to print all guests
    public void printAllGuests() {
        Iterable<Guest> guests = guestRepository.findAll();
        guests.forEach(System.out::println);
    }

    // method to print all reservations
    public void printAllReservations() {
        Iterable<Reservation> reservations = reservationRepository.findAll();
        reservations.forEach(System.out::println);
    }

    // method to print all guests of a specific country
    public void printAllGuestsByCountry(String country) {
        Iterable<Guest> guestsByCountry = guestRepository.findGuestByCountry(country);
        guestsByCountry.forEach(System.out::println);
    }

    public void printAllRoomReservationsByDate(){
        Date date = dateUtils.createDateFromDateString("2022-01-01");
        System.out.println(date);
        List<RoomReservation> reservations = reservationService.getRoomReservationsForDate(date);
        reservations.forEach(System.out::println);

    }


    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Scanner scanner = new Scanner(System.in);
        String response;

        while (true) {
            System.out.println("Welcome to Reservation Service");
            System.out.println("1. Room List\n2. Guest List\n3. Reservations" +
                    "\n4. Guests by Country\n5. ReservationService" +
                    "\ne.Exit the Loop");
            System.out.print("Enter your Choice: ");
            response = scanner.next().toLowerCase();

            // switch case to print all rooms
            switch (response) {
                case "1" -> printAllRooms();
                case "2" -> printAllGuests();
                case "3" -> printAllReservations();
                case "4"-> {
                    System.out.println("Enter the Country Name: ");
                    var country = scanner.next();
                    printAllGuestsByCountry(country);

                }
                case "5" -> printAllRoomReservationsByDate();
                case "e"-> {
                    System.out.println("Exiting ....");
                    return;
                }
                default -> System.out.println("Invalid Choice");
            } // switch end
        } // while end
    }
}
