package model;

public class Room implements Identifiable {
    private int roomID;
    private String name;
    private int maxCapacity;
    private Location location;

    //Constructor
    public Room(int roomID, String name, int maxCapacity, Location location){
        this.roomID = roomID;
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.location = location;
    }

    @Override
    public int getID() {
        return roomID;
    }

    //Getters

    public int getRoomID() {
        return roomID;
    }

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

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

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
                "roomID=" + roomID +
                ", name='" + name + '\'' +
                ", maxCapacity=" + maxCapacity +
                ", location=" + location +
                '}';
    }
}
