package model;

public class Trainer extends User implements Identifiable {

    private int trainerID;
    private String specialisation;

    // Constructor
    public Trainer(String name, String mail, String phone, int trainerID, String specialisation) {
        super(name, mail, phone);
        this.trainerID = trainerID;
        this.specialisation = specialisation;
    }

    @Override
    public int getID() {
        return trainerID;
    }

    // Getters

    public int getTrainerID() {
        return trainerID;
    }

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

    public void setTrainerID(int trainerID) {
        this.trainerID = trainerID;
    }

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
}
