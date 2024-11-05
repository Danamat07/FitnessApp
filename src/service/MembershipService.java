package service;
import model.Membership;
import repository.IRepository;
import java.util.List;

public class MembershipService {

    private final IRepository<Membership> membershipRepository;

    // Constructor
    public MembershipService(IRepository<Membership> membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    public Membership getMembership(int id) {
        return membershipRepository.read(id);
    }

    public void addMembership(Membership membership) {
        if (membershipRepository.read(membership.getID()) != null) {
            throw  new IllegalArgumentException("Membership with ID " + membership.getID() + " already exists.");
        }
        membershipRepository.create(membership);
    }

    public void updateMembership(Membership membership) {
        if (membershipRepository.read(membership.getID()) == null) {
            throw new IllegalArgumentException("Membership with ID " + membership.getID() + " does not exist.");
        }
        membershipRepository.update(membership);
    }

    public void deleteMembership(int id) {
        if (membershipRepository.read(id) == null) {
            throw new IllegalArgumentException("Membership with ID " + id + " does not exist.");
        }
        membershipRepository.delete(id);
    }

    public List<Membership> getAllMemberships() {
        return membershipRepository.getAll();
    }
}
