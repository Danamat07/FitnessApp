package model;

public class Location implements Identifiable {
    private int id;
    private String name;
    private String adress;

    //Constructor

    public Location(String name, String adress){
        this.name = name;
        this.adress = adress;
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

    public String getAdress() {
        return adress;
    }

    //Setters

    public void setName(String name) {
        this.name = name;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", adress='" + adress + '\'' +
                '}';
    }
}
