package com.jarotball.learningspringdemo.business;

import com.jarotball.learningspringdemo.model.Guest;
import com.jarotball.learningspringdemo.model.GuestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestService {
    private final GuestRepository guestRepository;
    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    // method to get all guests
    public List<Guest> getAllGuests() {
        List<Guest> guestList= guestRepository.findAll();
        // sort the guests by their last name, then by their first name
        guestList.sort((o1, o2) -> {
            if (o1.getLastName().equals(o2.getLastName())) {
                return o1.getFirstName().compareTo(o2.getFirstName());
            }
            return o1.getLastName().compareTo(o2.getLastName());
        });
        return  guestList;
    }

    // method to add a new guest
    public void addGuest(Guest guest) {
        if (null ==guest)
            throw new RuntimeException("Guest cannot be null");
        guestRepository.save(guest);
    }

    // method to find a guest by first name
    public List<Guest> getGuestByFirstName(String firstName) {
        return guestRepository.findGuestByFirstName(firstName);
    }
}
