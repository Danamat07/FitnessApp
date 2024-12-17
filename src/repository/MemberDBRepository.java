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

    /**
     * Creates a new member in the database.
     * <p>This method inserts a new member's information, including their ID, name, password, registration date,
     * and associated membership, into the `member` table.</p>
     * @param obj The `Member` object containing the information to be added to the database.
     * @throws RuntimeException If there is an issue executing the SQL query or processing the database.
     */
    @Override
    public void create(Member obj) {
        String sql = "INSERT INTO member (id, name, password, registrationDate, membership) VALUES(?, ?, ?, ?, ?)";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,obj.getId());
            statement.setString(2,obj.getName());
            statement.setString(3,obj.getPassword());
            statement.setTimestamp(4, Timestamp.valueOf(obj.getRegistrationDate()));
            statement.setInt(5,obj.getMembership().getId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create member", e);
        }
    }

    /**
     * Retrieves a member from the database by their ID.
     * <p>This method queries the `member` table for a member with the specified ID and returns the corresponding
     * `Member` object, or `null` if no such member is found.</p>
     * @param id The ID of the member to be retrieved from the database.
     * @return The `Member` object with the specified ID, or `null` if no member with that ID exists.
     * @throws RuntimeException If there is an issue executing the SQL query or processing the database.
     */
    @Override
    public Member read(int id) {
        String sql = "SELECT * FROM member WHERE id=?";
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

    /**
     * Updates an existing member's information in the database.
     * <p>This method updates the member's details such as name, password, registration date, and membership
     * in the `member` table using the provided `Member` object.</p>
     * @param obj The `Member` object containing the updated information for the member.
     * @throws RuntimeException If there is an error executing the SQL query or updating the database.
     */
    @Override
    public void update(Member obj) {
        String sql = "UPDATE member SET name=?, password=?, registrationDate=?, membership=? WHERE id=?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,obj.getName());
            statement.setString(2,obj.getPassword());
            statement.setTimestamp(3, Timestamp.valueOf(obj.getRegistrationDate()));
            statement.setInt(4,obj.getMembership().getId());
            statement.setInt(5,obj.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update member", e);
        }
    }

    /**
     * Deletes a member and their associated data from the database.
     * <p>This method deletes the member from the `member` table and also removes any associations in
     * the `member_FitnessClass` table that link the member to fitness classes.</p>
     * @param id The ID of the member to be deleted.
     * @throws RuntimeException If there is an error executing the SQL queries or deleting the data.
     */
    @Override
    public void delete(int id) {
        String sql = "DELETE FROM member WHERE id=?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete member", e);
        }
        String sql2 = "DELETE FROM member_FitnessClass WHERE memberID=?";
        try(PreparedStatement statement = connection.prepareStatement(sql2)){
            statement.setInt(1,id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete member", e);
        }
    }

    /**
     * Retrieves all members from the database.
     * <p>This method queries the `member` table and returns a list of all members. Each member is
     * constructed using the data retrieved from the database.</p>
     * @return A list of all `Member` objects from the database.
     * @throws RuntimeException If there is an error executing the SQL query or retrieving the data.
     */
    @Override
    public List<Member> getAll() {
        String sql = "SELECT * FROM member";
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

    /**
     * Extracts a `Member` object from the provided `ResultSet`.
     * <p>This method reads data from a `ResultSet` and constructs a `Member` object using the values
     * from the database. It also retrieves the related `Membership` and `FitnessClass` objects for
     * the member.</p>
     * @param resultSet The `ResultSet` containing the member data.
     * @return A `Member` object populated with the data from the `ResultSet`.
     * @throws SQLException If there is an error accessing the data in the `ResultSet`.
     */
    public Member extractFromResultSet(ResultSet resultSet) throws SQLException {
        Membership membership = membershipDBRepository.read(resultSet.getInt("membership"));
        ArrayList<FitnessClass> fitnessClasses = getMemberFitnessClasses(resultSet.getInt("id"));
        Member member = new Member(
                resultSet.getString("name"),
                resultSet.getString("password"),
                resultSet.getTimestamp("registrationDate").toLocalDateTime(),
                membership,
                new ArrayList<>()
        );
        member.setFitnessClasses(fitnessClasses);
        member.setId(resultSet.getInt("id"));
        return member;
    }

    /**
     * Retrieves a list of `FitnessClass` objects that a specific member is enrolled in, based on their member ID.
     * <p>This method queries the database for all fitness classes associated with a given member by using the
     * member's ID. The results are returned as a list of `FitnessClass` objects.</p>
     * @param id The ID of the member whose fitness classes are to be retrieved.
     * @return A list of `FitnessClass` objects associated with the given member.
     * @throws RuntimeException If there is an error accessing the database or executing the SQL query.
     */
    private ArrayList<FitnessClass> getMemberFitnessClasses(Integer id) {
        ArrayList<FitnessClass> fitnessClasses = new ArrayList<>();
        String sqlMembersClasses =
                "SELECT fc.id, fc.name, fc.startTime, fc.endTime, fc.trainer, fc.room, fc.participantsCount, " +
                        "fc.location" +
                        " FROM fitnessClass as fc "
                        + "where id in (SELECT member_fitnessClass.classID FROM member_fitnessClass WHERE memberID = ?)";
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
