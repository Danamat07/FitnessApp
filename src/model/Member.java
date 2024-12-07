package model;

import java.time.LocalDateTime;
import java.util.List;

public class Member extends User implements HasId {

    private int id;
    private LocalDateTime registrationDate;
    private Membership membership;
    private List<FitnessClass> fitnessClasses;

    // Constructor
    public Member(String name, String mail, String phone, LocalDateTime registrationDate, Membership membership, List<FitnessClass> fitnessClasses) {
        super(name, mail, phone);
        this.registrationDate = registrationDate;
        this.membership = membership;
        this.fitnessClasses = fitnessClasses;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    // Getters

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public Membership getMembership() {
        return membership;
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

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
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
        return "Member{" +
                "registrationDate=" + registrationDate +
                ", membership='" + membership + '\'' +
                ", fitnessClasses=" + fitnessClasses +
                '}';
    }
}
