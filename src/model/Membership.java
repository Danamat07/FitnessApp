package model;

public class Membership implements HasId {

    private int id;
    private String type;
    private float price;

    // Constructor
    public Membership(String type, float price) {
        this.type = type;
        this.price = price;
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

    public String getType() {
        return type;
    }

    public float getPrice() {
        return price;
    }

    // Setters

    public void setType(String type) {
        this.type = type;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Membership{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", price=" + price +
                '}';
    }
}
