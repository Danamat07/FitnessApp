package model;
import java.util.ArrayList;
import java.util.List;

public class Membership implements Identifiable {

    private int id;
    private String type;
    private ArrayList<Member> members;
    private float price;

    // Constructor
    public Membership(String type, ArrayList<Member> members, float price) {
        this.type = type;
        this.members = members;
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

    public List<Member> getMembers() {
        return members;
    }

    public float getPrice() {
        return price;
    }

    // Setters

    public void setType(String type) {
        this.type = type;
    }

    public void setMembers(ArrayList<Member> members) {
        this.members = members;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Membership{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", members=" + members +
                ", price=" + price +
                '}';
    }
}
