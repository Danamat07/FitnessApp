package model;

public class Feedback {
    private int feedbackId;
    private Member member;
    private FitnessClass fitnessClass;
    private int rating;
    private String comment;

    //Constructor
    public Feedback(int feedbackId, Member member, FitnessClass fitnessClass, int rating, String comment){
        this.feedbackId = feedbackId;
        this.member = member;
        this.fitnessClass = fitnessClass;
        this.rating = rating;
        this. comment = comment;
    }

    //Getters

    public int getFeedbackId() {
        return feedbackId;
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

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
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
