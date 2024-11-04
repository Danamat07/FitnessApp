package model;

public class Location implements Identifiable {
    private int locationID;
    private String name;
    private String adress;

    //Constructor

    public Location(int locationId, String name, String adress){
        this.locationID = locationID;
        this.name = name;
        this.adress = adress;
    }

    @Override
    public int getID() {
        return locationID;
    }

    //Getters

    public int getLocationID() {
        return locationID;
    }

    public String getName() {
        return name;
    }

    public String getAdress() {
        return adress;
    }

    //Setters

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}
