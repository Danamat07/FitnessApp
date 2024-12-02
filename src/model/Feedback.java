package model;

public class Feedback implements HasId {
    private int id;
    private Member member;
    private FitnessClass fitnessClass;
    private int rating;
    private String comment;

    //Constructor
    public Feedback(Member member, FitnessClass fitnessClass, int rating, String comment){
        this.member = member;
        this.fitnessClass = fitnessClass;
        this.rating = rating;
        this. comment = comment;
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

    public Member getMember() {
        return member;
    }

    public FitnessClass getFitnessClass() {
        return fitnessClass;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    //Setters

    public void setMember(Member member) {
        this.member = member;
    }

    public void setFitnessClass(FitnessClass fitnessClass) {
        this.fitnessClass = fitnessClass;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", member=" + member +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                '}';
    }
}
