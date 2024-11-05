package service;
import model.Member;
import repository.IRepository;
import java.util.List;

public class MemberService {

    private final IRepository<Member> memberRepository;

    // Constructor
    public MemberService(IRepository<Member> memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member getMember(int id) {
        return memberRepository.read(id);
    }

    public void addMember(Member member) {
        if (memberRepository.read(member.getID()) != null) {
            throw  new IllegalArgumentException("Member with ID " + member.getID() + " already exists.");
        }
        memberRepository.create(member);
    }

    public void updateMember(Member member) {
        if (memberRepository.read(member.getID()) == null) {
            throw new IllegalArgumentException("Member with ID " + member.getID() + " does not exist.");
        }
        memberRepository.update(member);
    }

    public void deleteMember(int id) {
        if (memberRepository.read(id) == null) {
            throw new IllegalArgumentException("Member with ID " + id + " does not exist.");
        }
        memberRepository.delete(id);
    }

    public List<Member> getAllMembers() {
        return memberRepository.getAll();
    }

}
