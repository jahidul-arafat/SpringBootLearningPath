package com.jarotball.learningspringdemo.business;

import com.jarotball.learningspringdemo.model.Room;
import com.jarotball.learningspringdemo.model.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }
    public List<Room> getAllRooms() {
        List<Room> rooms= roomRepository.findAll();
        // sort the rooms by roomNumber
        rooms.sort((o1, o2) -> o1.getRoomNumber().compareTo(o2.getRoomNumber()));
        return rooms;
    }

}
