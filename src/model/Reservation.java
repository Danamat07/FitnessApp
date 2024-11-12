package model;

import java.time.LocalDateTime;

public class Reservation implements Identifiable {
    private int id;
    private Member member;
    private FitnessClass fitnessClass;
    private LocalDateTime reservationDate;

    //Constructor
    public Reservation(Member member, FitnessClass fitnessClass, LocalDateTime reservationDate){
        this.member = member;
        this.fitnessClass = fitnessClass;
        this.reservationDate = reservationDate;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    //Getters

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

    public void setMember(Member member) {
        this.member = member;
    }

    public void setFitnessClass(FitnessClass fitnessClass) {
        this.fitnessClass = fitnessClass;
    }

    public void setReservationDate(LocalDateTime reservationDate) {
        this.reservationDate = reservationDate;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", member=" + member +
                ", fitnessClass=" + fitnessClass +
                ", reservationDate=" + reservationDate +
                '}';
    }
}
