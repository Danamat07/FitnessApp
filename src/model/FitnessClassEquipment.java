package model;

public class FitnessClassEquipment {

    private int equpmentID;
    private int classID;
    private int quantity;

    // Constructor
    public FitnessClassEquipment(int equpmentID, int classID, int quantity) {
        this.classID = classID;
        this.equpmentID = equpmentID;
        this.quantity = quantity;
    }

    public Tuple<Integer, Integer> getID(int equpmentID, int classID) {
        return new Tuple<>(equpmentID, classID);
    }

    // Getters

    public int getClassID() {
        return classID;
    }

    public int getEqupmentID() {
        return equpmentID;
    }

    public int getQuantity() {
        return quantity;
    }

    // Setters


    public void setClassID(int classID) {
        this.classID = classID;
    }

    public void setEqupmentID(int equpmentID) {
        this.equpmentID = equpmentID;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
