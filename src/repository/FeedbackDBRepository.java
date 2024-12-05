package repository;

import model.Feedback;

import java.util.List;

public class FeedbackDBRepository extends DBRepository<Feedback> {

    public FeedbackDBRepository(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void create(Feedback obj) {

    }

    @Override
    public Feedback read(int id) {
        return null;
    }

    @Override
    public void update(Feedback obj) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Feedback> getAll() {
        return List.of();
    }
}
