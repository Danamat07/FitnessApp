package model;

import java.time.LocalDateTime;

public class Attendance {

    private int memberID;
    private int classID;
    public LocalDateTime reservationDate;

    // Constructor
    public Attendance(int memberID, int classID, LocalDateTime reservationDate) {
        this.memberID = memberID;
        this.classID = classID;
        this.reservationDate = reservationDate;
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

    @Override
    public String toString() {
        return "Attendance{" +
                "memberID=" + memberID +
                ", classID=" + classID +
                ", reservationDate=" + reservationDate +
                '}';
    }
}
