package com.jarotball.learningspringdemo.controller;

import com.jarotball.learningspringdemo.business.ReservationService;
import com.jarotball.learningspringdemo.business.RoomReservation;
import com.jarotball.learningspringdemo.util.DateUtils;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/reservations")
public class RoomReservationController {
    private final DateUtils dateUtils;
    private final ReservationService reservationsService;

    public RoomReservationController(DateUtils dateUtils, ReservationService reservationsService) {
        this.dateUtils = dateUtils;
        this.reservationsService = reservationsService;
    }

    // Create the GET API
    @RequestMapping(method = RequestMethod.GET)
    public String getReservations(@RequestParam(value = "date", required = false) String dateString, Model model){
        Date date = dateUtils.createDateFromDateString(dateString);
        List<RoomReservation> roomReservations = reservationsService.getRoomReservationsForDate(date);
        model.addAttribute("roomReservations", roomReservations);
        return "roomres";
    }

}
