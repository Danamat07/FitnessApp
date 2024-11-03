package model;

public class Trainer {

    private int trainerID;
    private String specialisation;

    // Constructor
    public Trainer(int trainerID, String specialisation) {
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
