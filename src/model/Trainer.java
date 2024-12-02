package model;

public class Trainer extends User implements HasId {

    private int id;
    private String specialisation;

    // Constructor
    public Trainer(String name, String mail, String phone, String specialisation) {
        super(name, mail, phone);
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
    public String getMail() {
        return super.getMail();
    }

    @Override
    public String getPhone() {
        return super.getPhone();
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
    public void setMail(String mail) {
        super.setMail(mail);
    }

    @Override
    public void setPhone(String phone) {
        super.setPhone(phone);
    }

    @Override
    public String toString() {
        return "Trainer{" +
                "id=" + id +
                ", specialisation='" + specialisation + '\'' +
                '}';
    }
}
