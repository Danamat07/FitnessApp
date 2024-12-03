package model;

import java.util.List;

public class Equipment implements HasId {
    private int id;
    private String name;
    private int quantity;
    private List<FitnessClass> fitnessClasses;

    //Constructor
    public Equipment(String name, int quantity, List<FitnessClass> fitnessClasses){
        this.name = name;
        this.quantity = quantity;
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

    //Getters

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


    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setFitnessClasses(List<FitnessClass> fitnessClasses) {
        this.fitnessClasses = fitnessClasses;
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", fitnessClasses=" + fitnessClasses +
                '}';
    }
}
