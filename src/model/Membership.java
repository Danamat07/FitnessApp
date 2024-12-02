package model;
import java.util.ArrayList;
import java.util.List;

public class Membership implements HasId {

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

    public String getIDsOfMembers() {
        StringBuilder str = new StringBuilder();
        for (Member member1 : getMembers()) {
            if (member1 != null) {
                str.append(member1.getId()).append("-");
            }
        }
        if (str.length() > 0) {
            str.deleteCharAt(str.length() - 1);
        }
        return str.toString();
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
