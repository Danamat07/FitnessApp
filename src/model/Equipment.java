package model;

import java.util.List;

public class Equipment {
    private int equipmentID;
    private String name;
    private int quantity;
    private List<FitnessClass> fitnessClasses;

    //Constructor
    public Equipment(int equipmentID, String name, int quantity, List<FitnessClass> fitnessClasses){
        this.equipmentID = equipmentID;
        this.name = name;
        this.quantity = quantity;
        this.fitnessClasses = fitnessClasses;
    }

    //Getters

    public int getEquipmentID() {
        return equipmentID;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public List<FitnessClass> getFitnessClasses() {
        return fitnessClasses;
    }
    //Setters

    public void setEquipmentID(int equipmentID) {
        this.equipmentID = equipmentID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setFitnessClasses(List<FitnessClass> fitnessClasses) {
        this.fitnessClasses = fitnessClasses;
    }
}
