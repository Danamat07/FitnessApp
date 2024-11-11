package model;

import java.time.LocalDate;
import java.util.List;

public class Member extends User implements Identifiable {

    private int memberID;
    private LocalDate registrationDate;
    private String membershipType;
    private List<FitnessClass> fitnessClasses;

    // Constructor
    public Member(String name, String mail, String phone, int memberID, LocalDate registrationDate, String membershipType, List<FitnessClass> fitnessClasses) {
        super(name, mail, phone);
        this.memberID = memberID;
        this.registrationDate = registrationDate;
        this.membershipType = membershipType;
        this.fitnessClasses = fitnessClasses;
    }

    @Override
    public int getID() {
        return memberID;
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

    public List<FitnessClass> getFitnessClasses() {
        return fitnessClasses;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public String getMail() {
        return super.getMail();
    }

    @Override
    public String getPhone() {
        return super.getPhone();
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

    public void setFitnessClasses(List<FitnessClass> fitnessClasses) {
        this.fitnessClasses = fitnessClasses;

    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public void setMail(String mail) {
        super.setMail(mail);
    }

    @Override
    public void setPhone(String phone) {
        super.setPhone(phone);
    }

    @Override
    public String toString() {
        return super.toString() + " -> Member{" +
                "memberID=" + memberID +
                ", registrationDate=" + registrationDate +
                ", membershipType='" + membershipType + '\'' +
                ", fitnessClasses=" + fitnessClasses +
                '}';
    }
}
