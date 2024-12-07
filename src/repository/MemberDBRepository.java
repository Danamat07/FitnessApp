package repository;

import model.FitnessClass;
import model.Member;
import model.Membership;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * The MemberDBRepository class provides CRUD operations for the `Member` model using a relational database.
 * It extends the generic DBRepository class, which provides a base implementation for managing database connections.
 */
public class MemberDBRepository extends DBRepository<Member>{

    // Instance of MembershipDBRepository to interact with membership-related data in the database
    MembershipDBRepository membershipDBRepository;
    // Instance of FitnessClassDBRepository to interact with membership-related data in the database
    FitnessClassDBRepository fitnessClassDBRepository;

    /**
     * Constructs a DBRepository instance with the provided database connection details.
     * It also initializes the MembershipDBRepository and FitnessClassDBRepository to handle member data.
     * Establishes a connection to the database using the specified URL, user, and password.
     * @param dbUrl      The URL of the database to connect to.
     * @param dbUser     The username to use when connecting to the database.
     * @param dbPassword The password to use when connecting to the database.
     * @throws RuntimeException If the connection cannot be established due to an SQLException.
     */
    public MemberDBRepository(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
        this.membershipDBRepository = new MembershipDBRepository(dbUrl, dbUser, dbPassword);
        this.fitnessClassDBRepository = new FitnessClassDBRepository(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void create(Member obj) {
        String sql = "INSERT INTO Member (id, name, mail, phone, registrationDate, membership) VALUES(?, ?, ?, ?, ?, ?)";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,obj.getId());
            statement.setString(2,obj.getName());
            statement.setString(3,obj.getMail());
            statement.setString(4,obj.getPhone());
            statement.setTimestamp(5, Timestamp.valueOf(obj.getRegistrationDate()));
            statement.setInt(6,obj.getMembership().getId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create member", e);
        }
    }

    @Override
    public Member read(int id) {
        String sql = "SELECT * FROM Member WHERE id=?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return extractFromResultSet(resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to read member", e);
        }
    }

    @Override
    public void update(Member obj) {
        String sql = "UPDATE Member SET name=?, mail=?, phone=?, registrationDate=?, membership=? WHERE id=?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,obj.getName());
            statement.setString(2,obj.getMail());
            statement.setString(3,obj.getPhone());
            statement.setTimestamp(4, Timestamp.valueOf(obj.getRegistrationDate()));
            statement.setInt(5,obj.getMembership().getId());
            statement.setInt(6,obj.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update Member", e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM Member WHERE id=?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete room", e);
        }
        String sql2 = "DELETE FROM MemberFitnessClass WHERE memberID=?";
        try(PreparedStatement statement = connection.prepareStatement(sql2)){
            statement.setInt(1,id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete Member", e);
        }
    }

    @Override
    public List<Member> getAll() {
        String sql = "SELECT * FROM Member";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery();
            List<Member> members = new ArrayList<>();
            while(resultSet.next()){
                members.add(extractFromResultSet(resultSet));
            }
            return members;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve all members", e);
        }
    }

    public Member extractFromResultSet(ResultSet resultSet) throws SQLException {
        Membership membership = membershipDBRepository.read(resultSet.getInt("membership"));
        ArrayList<FitnessClass> fitnessClasses = getMemberFitnessClasses(resultSet.getInt("id"));
        Member member = new Member(
                resultSet.getString("name"),
                resultSet.getString("mail"),
                resultSet.getString("phone"),
                resultSet.getTimestamp("registrationDate").toLocalDateTime(),
                membership,
                new ArrayList<>()
        );
        member.setFitnessClasses(fitnessClasses);
        member.setId(resultSet.getInt("id"));
        return member;
    }

    private ArrayList<FitnessClass> getMemberFitnessClasses(Integer id) {
        ArrayList<FitnessClass> fitnessClasses = new ArrayList<>();
        String sqlMembersClasses =
                "SELECT fc.id, fc.name, fc.startTime, fc.endTime, fc.trainer, fc.room, fc.participantsCount, " +
                        "fc.location, fc.feedback, fc.members, fc.equipment" +
                        " FROM FitnessClass as fc "
                        + "where id in (SELECT MemberFitnessClass.classID FROM MemberFitnessClass WHERE memberID = ?)";
        try(PreparedStatement statementMembersClasses = connection.prepareStatement(sqlMembersClasses)){
            statementMembersClasses.setInt(1, id);
            ResultSet resultSetMembersClasses = statementMembersClasses.executeQuery();
            while(resultSetMembersClasses.next()){
                fitnessClasses.add(fitnessClassDBRepository.extractFromResultSet(resultSetMembersClasses));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return fitnessClasses;
    }
}
