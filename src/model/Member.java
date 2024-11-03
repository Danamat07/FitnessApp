package model;

import java.time.LocalDate;

public class Member {

    private int memberID;
    private LocalDate registrationDate;
    private String membershipType;

    // Constructor
    public Member(int memberID, LocalDate registrationDate, String membershipType) {
        this.memberID = memberID;
        this.registrationDate = registrationDate;
        this.membershipType = membershipType;
    }

    // Getters

    public int getMemberID() {
        return memberID;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public String getMembershipType() {
        return membershipType;
    }

    // Setters

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }
}
