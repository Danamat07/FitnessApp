package service;

import model.Equipment;
import repository.IRepository;
import java.util.List;

public class EquipmentService {

    private final IRepository<Equipment> equipmentRepository;

    public EquipmentService(IRepository<Equipment> equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    public Equipment getEquipment(int id){
        return equipmentRepository.read(id);
    }

    public void addEquipment(Equipment Equipment){
        if (equipmentRepository.read(Equipment.getID()) != null){
            throw new IllegalArgumentException("Equipment with ID" + Equipment.getID() + " alredy exists");
        }
        equipmentRepository.create(Equipment);
    }

    public void updateEquipment(Equipment Equipment){
        if(equipmentRepository.read((Equipment.getID())) == null){
            throw new IllegalArgumentException("Equipment with ID" + Equipment.getID() + " does not exist");
        }
        equipmentRepository.update(Equipment);
    }

    public void deleteEquipment(int id){
        if (equipmentRepository.read(id) == null){
            throw new IllegalArgumentException("Equipment with ID" + id + " does not exist");
        }
    }

    public List<Equipment> getAllEquipments(){
        return equipmentRepository.getAll();
    }
}
