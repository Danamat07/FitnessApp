package model;

public class Room {
    private int roomId;
    private String name;
    private int maxCapacity;
    private Location location;

    //Constructor
    public Room(int roomId, String name, int maxCapacity, Location location){
        this.roomId = roomId;
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.location = location;
    }

    //Getters

    public int getRoomId() {
        return roomId;
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

    public void setRoomId(int roomId) {
        this.roomId = roomId;
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
}
