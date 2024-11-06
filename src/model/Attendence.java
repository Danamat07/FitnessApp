package model;

import java.time.LocalDateTime;

public class Attendence {

    private int memberID;
    private int classID;
    public LocalDateTime reservationDate;

    // Constructor
    public Attendence(int memberID, int classID, LocalDateTime reservationDate) {
        this.memberID = memberID;
        this.classID = classID;
        this.reservationDate = reservationDate;
    }

    public Tuple<Integer, Integer> getID(int memberID, int classID) {
        return new Tuple<>(memberID, classID);
    }

    // Getters

    public int getMemberID() {
        return memberID;
    }

    public int getClassID() {
        return classID;
    }

    public LocalDateTime getReservationDate() {
        return reservationDate;
    }

    // Setters

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public void setReservationDate(LocalDateTime reservationDate) {
        this.reservationDate = reservationDate;
    }
}
