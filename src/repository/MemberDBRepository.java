package repository;

import model.Member;

import java.util.List;

public class MemberDBRepository extends DBRepository<Member>{

    public MemberDBRepository(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void create(Member obj) {

    }

    @Override
    public Member read(int id) {
        return null;
    }

    @Override
    public void update(Member obj) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Member> getAll() {
        return List.of();
    }
}
