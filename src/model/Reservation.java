package model;

import java.time.LocalDateTime;

public class Reservation {
    private int reservationID;
    private Member member;
    private FitnessClass fitnessClass;
    private LocalDateTime reservationDate;

    //Constructor
    public Reservation(int reservationID, Member member, FitnessClass fitnessClass, LocalDateTime reservationDate){
        this.reservationID = reservationID;
        this.member = member;
        this.fitnessClass = fitnessClass;
        this.reservationDate = reservationDate;
    }

    //Getters

    public int getReservationID() {
        return reservationID;
    }

    public Member getMember() {
        return member;
    }

    public FitnessClass getFitnessClass() {
        return fitnessClass;
    }

    public LocalDateTime getReservationDate() {
        return reservationDate;
    }

    //Setters

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setFitnessClass(FitnessClass fitnessClass) {
        this.fitnessClass = fitnessClass;
    }

    public void setReservationDate(LocalDateTime reservationDate) {
        this.reservationDate = reservationDate;
    }
}
