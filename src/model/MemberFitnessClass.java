package model;

import java.time.LocalDateTime;

public class MemberFitnessClass {
    private int memberID;
    private int classID;
    private LocalDateTime reservationDate;

    //Constructor
    public MemberFitnessClass( int memberID, int classID, LocalDateTime reservationDate){
        this.memberID = memberID;
        this.classID = classID;
        this.reservationDate = reservationDate;
    }

    //Getters

    public int getMemberID(){
        return memberID;
    }

    public int getClassID() {
        return classID;
    }

    public LocalDateTime getReservationDate() {
        return reservationDate;
    }

    //Setters

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
