package model;

import java.time.LocalDateTime;
import java.util.List;

public class Member extends User implements HasId {

    private int id;
    private LocalDateTime registrationDate;
    private Membership membership;
    private List<FitnessClass> fitnessClasses;

    // Constructor
    public Member(String name, String password, LocalDateTime registrationDate, Membership membership, List<FitnessClass> fitnessClasses) {
        super(name, password);
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
    public String getPassword() {
        return super.getPassword();
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
    public void setPassword(String password) {
        super.setPassword(password);
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
