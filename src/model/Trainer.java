package model;

public class Trainer extends User implements HasId {

    private int id;
    private String specialisation;

    // Constructor
    public Trainer(String name, String password, String specialisation) {
        super(name, password);
        this.specialisation = specialisation;
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

    public String getSpecialisation() {
        return specialisation;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    // Setters

    public void setSpecialisation(String specialisation) {
        this.specialisation = specialisation;
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
    }

    @Override
    public String toString() {
        return "Trainer{" +
                "id=" + id +
                ", specialisation='" + specialisation + '\'' +
                '}';
    }
}
