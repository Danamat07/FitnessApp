package model;

public class Room implements HasId {
    private int id;
    private String name;
    private int maxCapacity;
    private Location location;

    //Constructor
    public Room(String name, int maxCapacity, Location location){
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.location = location;
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

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public Location getLocation() {
        return location;
    }

    //Setters

    public void setName(String name) {
        this.name = name;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", maxCapacity=" + maxCapacity +
                ", location=" + location +
                '}';
    }
}
