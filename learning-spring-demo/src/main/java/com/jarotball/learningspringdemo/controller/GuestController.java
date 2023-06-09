package com.jarotball.learningspringdemo.controller;

import com.jarotball.learningspringdemo.business.GuestService;
import com.jarotball.learningspringdemo.model.Guest;
import com.jarotball.learningspringdemo.model.GuestRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/guests")
public class GuestController {
    private final GuestService guestService;
    // constructor
    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    // Create the GET API
    @RequestMapping(method = RequestMethod.GET)
    public String getGuests(Model model) {
        List<Guest> guests = guestService.getAllGuests();
        model.addAttribute("guests", guests);
        return "guests";
    }
}
