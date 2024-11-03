package model;
import java.util.List;

public class Membership {

    private int membershipID;
    private String type;
    private List<Member> members;
    private float price;

    // Constructor
    public Membership(int membershipID, String type, List<Member> members, float price) {
        this.membershipID = membershipID;
        this.type = type;
        this.members = members;
        this.price = price;
    }

    // Getters

    public int getMembershipID() {
        return membershipID;
    }

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

    public void setMembershipID(int membershipID) {
        this.membershipID = membershipID;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
