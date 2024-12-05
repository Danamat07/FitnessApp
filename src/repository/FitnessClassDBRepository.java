package repository;

import model.FitnessClass;

import java.util.List;

public class FitnessClassDBRepository extends DBRepository<FitnessClass> {

    public FitnessClassDBRepository(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void create(FitnessClass obj) {

    }

    @Override
    public FitnessClass read(int id) {
        return null;
    }

    @Override
    public void update(FitnessClass obj) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<FitnessClass> getAll() {
        return List.of();
    }
}
