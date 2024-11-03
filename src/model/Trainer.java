package model;

public class Trainer extends User {

    private int trainerID;
    private String specialisation;

    // Constructor
    public Trainer(String name, String mail, String phone, int trainerID, String specialisation) {
        super(name, mail, phone);
        this.trainerID = trainerID;
        this.specialisation = specialisation;
    }

    // Getters

    public int getTrainerID() {
        return trainerID;
    }

    public String getSpecialisation() {
        return specialisation;
    }

    // Setters

    public void setTrainerID(int trainerID) {
        this.trainerID = trainerID;
    }

    public void setSpecialisation(String specialisation) {
        this.specialisation = specialisation;
    }
}
