package service;

import model.Feedback;
import repository.IRepository;

import java.util.List;

public class ServiceFeedback {
    private final IRepository<Feedback> feedbackRepository;

    public ServiceFeedback(IRepository<Feedback> feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }


    public Feedback getFeedback(int id){
        return feedbackRepository.read(id);
    }

    public void addFeedback(Feedback Feedback){
        if (feedbackRepository.read(Feedback.getID()) != null){
            throw new IllegalArgumentException("Feedback with ID" + Feedback.getID() + " alredy exists");
        }
        feedbackRepository.create(Feedback);
    }

    public void updateFeedback(Feedback Feedback){
        if(feedbackRepository.read((Feedback.getID())) == null){
            throw new IllegalArgumentException("Feedback with ID" + Feedback.getID() + " does not exist");
        }
        feedbackRepository.update(Feedback);
    }

    public void deleteFeedback(int id){
        if (feedbackRepository.read(id) == null){
            throw new IllegalArgumentException("Feedback with ID" + id + " does not exist");
        }
    }

    public List<Feedback> getAllFeedbacks(){
        return feedbackRepository.getAll();
    }
}
