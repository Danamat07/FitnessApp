package repository;

import model.Membership;

import java.util.List;

public class MembershipDBRepository extends DBRepository<Membership> {

    public MembershipDBRepository(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void create(Membership obj) {

    }

    @Override
    public Membership read(int id) {
        return null;
    }

    @Override
    public void update(Membership obj) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Membership> getAll() {
        return List.of();
    }
}
