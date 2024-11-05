package service;

import model.FitnessClass;
import repository.IRepository;

import java.util.List;

public class FitnessClassService {

    private final IRepository<FitnessClass> fitnessClassRepository;

    public FitnessClassService(IRepository<FitnessClass> fitnessClassRepository) {
        this.fitnessClassRepository = fitnessClassRepository;
    }

    public FitnessClass getFitnessClass(int id){
        return fitnessClassRepository.read(id);
    }

    public void addFitnessClass(FitnessClass FitnessClass){
        if (fitnessClassRepository.read(FitnessClass.getID()) != null){
            throw new IllegalArgumentException("FitnessClass with ID" + FitnessClass.getID() + " alredy exists");
        }
        fitnessClassRepository.create(FitnessClass);
    }

    public void updateFitnessClass(FitnessClass FitnessClass){
        if(fitnessClassRepository.read((FitnessClass.getID())) == null){
            throw new IllegalArgumentException("FitnessClass with ID" + FitnessClass.getID() + " does not exist");
        }
        fitnessClassRepository.update(FitnessClass);
    }

    public void deleteFitnessClass(int id){
        if (fitnessClassRepository.read(id) == null){
            throw new IllegalArgumentException("FitnessClass with ID" + id + " does not exist");
        }
    }

    public List<FitnessClass> getAllFitnessClasses(){
        return fitnessClassRepository.getAll();
    }
}
