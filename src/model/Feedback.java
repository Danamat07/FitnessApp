package model;

public class Feedback implements Identifiable {
    private int feedbackID;
    private Member member;
    private FitnessClass fitnessClass;
    private int rating;
    private String comment;

    //Constructor
    public Feedback(int feedbackID, Member member, FitnessClass fitnessClass, int rating, String comment){
        this.feedbackID = this.feedbackID;
        this.member = member;
        this.fitnessClass = fitnessClass;
        this.rating = rating;
        this. comment = comment;
    }

    @Override
    public int getID() {
        return feedbackID;
    }

    //Getters

    public int getFeedbackID() {
        return feedbackID;
    }

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

    public void setFeedbackID(int feedbackId) {
        this.feedbackID = feedbackID;
    }

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
}
