package model;

public class Location implements HasId {
    private int id;
    private String name;
    private String address;

    //Constructor

    public Location(String name, String address){
        this.name = name;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    //Setters

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", adress='" + address + '\'' +
                '}';
    }
}
