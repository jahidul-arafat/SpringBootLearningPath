package com.jarotball.learningspringdemo.restfulcontroller;

import com.jarotball.learningspringdemo.business.GuestService;
import com.jarotball.learningspringdemo.business.ReservationService;
import com.jarotball.learningspringdemo.business.RoomReservation;
import com.jarotball.learningspringdemo.business.RoomService;
import com.jarotball.learningspringdemo.model.Guest;
import com.jarotball.learningspringdemo.model.Room;
import com.jarotball.learningspringdemo.util.DateUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class WebserviceController {
    private final DateUtils dateUtils;
    private final ReservationService reservationService;
    private final RoomService roomService;
    private final GuestService guestService;
    public WebserviceController(DateUtils dateUtils, ReservationService reservationService,
                                RoomService roomService, GuestService guestService) {
        this.dateUtils = dateUtils;
        this.reservationService = reservationService;
        this.roomService = roomService;
        this.guestService = guestService;
    }
    @GetMapping("/reservations")
    public List<RoomReservation> getReservations(@RequestParam(value = "date", required = false) String dateString) {
        Date date = dateUtils.createDateFromDateString(dateString);
        return reservationService.getRoomReservationsForDate(date);
    }

    @GetMapping("/rooms")
    public List<Room> getRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/guests")
    public List<Guest> getGuests(@RequestParam(value="name",required = false) String firstName) {
        if (firstName != null) {
            return guestService.getGuestByFirstName(firstName);
        } else {
            return guestService.getAllGuests();
        }
    }

    @PostMapping("/guests")
    @ResponseStatus(HttpStatus.CREATED)
    public void addGuest(@RequestBody Guest guest) {
        this.guestService.addGuest(guest);
    }
}
