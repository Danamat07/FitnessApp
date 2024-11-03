package model;

import java.time.LocalDate;
import java.util.List;

public class Member extends User {

    private int memberID;
    private LocalDate registrationDate;
    private String membershipType;

    // Constructor
    public Member(String name, String mail, String phone, int memberID, LocalDate registrationDate, String membershipType) {
        super(name, mail, phone);
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
