package service;

import model.Trainer;
import repository.IRepository;
import java.util.List;

public class TrainerService {

    private final IRepository<Trainer> trainerRepository;

    public TrainerService(IRepository<Trainer> trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    public Trainer getTrainer(int id){
        return trainerRepository.read(id);
    }

    public void addTrainer(Trainer trainer){
        if (trainerRepository.read(trainer.getID()) != null){
            throw new IllegalArgumentException("Trainer with ID" + trainer.getID() + " alredy exists");
        }
        trainerRepository.create(trainer);
    }

    public void updateTrainer(Trainer trainer){
        if(trainerRepository.read((trainer.getID())) == null){
            throw new IllegalArgumentException("Trainer with ID" + trainer.getID() + " does not exist");
        }
        trainerRepository.update(trainer);
    }

    public void deleteTrainer(int id){
        if (trainerRepository.read(id) == null){
            throw new IllegalArgumentException("Trainer with ID" + id + " does not exist");
        }
    }

    public List<Trainer> getAllTrainers(){
        return trainerRepository.getAll();
    }
}
