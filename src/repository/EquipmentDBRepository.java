package repository;

import model.Equipment;

import java.util.List;

public class EquipmentDBRepository extends DBRepository<Equipment> {

    public EquipmentDBRepository(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void create(Equipment obj) {

    }

    @Override
    public Equipment read(int id) {
        return null;
    }

    @Override
    public void update(Equipment obj) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Equipment> getAll() {
        return List.of();
    }
}
