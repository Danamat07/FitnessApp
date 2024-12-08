package model;

public abstract class User implements HasId {

    private String name;
    private String password;

    // Constructor
    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    // Getters

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    // Setters

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
