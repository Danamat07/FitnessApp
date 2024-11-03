package model;

public class Location {
    private int locationId;
    private String name;
    private String adress;

    //Constructor

    public Location(int locationId, String name, String adress){
        this.locationId = locationId;
        this.name = name;
        this.adress = adress;
    }

    //Getters

    public int getLocationId() {
        return locationId;
    }

    public String getName() {
        return name;
    }

    public String getAdress() {
        return adress;
    }

    //Setters

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}
