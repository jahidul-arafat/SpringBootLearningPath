package com.jarotball.learningspringdemo.business;

import java.util.Date;
/*This will be a DTO related to the Room, Guest and Reservation and specifically related to the view*/
public class RoomReservation {
    // room attributes
    private long roomId;
    private String roomName;
    private String roomNumber;

    // guest attributes
    private long guestId;
    private String firstName;
    private String lastName;

    // reservation attributes
    private Date date;

    // getter and setter
    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public long getGuestId() {
        return guestId;
    }

    public void setGuestId(long guestId) {
        this.guestId = guestId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    // toString method

    @Override
    public String toString() {
        return "RoomReservation{" +
                "roomId=" + roomId +
                ", guestId=" + guestId +
                ", roomName='" + roomName + '\'' +
                ", roomNumber='" + roomNumber + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", date=" + date +
                '}';
    }
}
