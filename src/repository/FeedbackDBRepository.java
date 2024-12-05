package repository;

import model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The FeedbackDBRepository class provides CRUD operations for the `Feedback` model using a relational database.
 * It extends the generic DBRepository class, which provides a base implementation for managing database connections.
 */
public class FeedbackDBRepository extends DBRepository<Feedback> {

    // Instances of related repositories to interact with Member and FitnessClass data
    MemberDBRepository memberDBRepository;
    FitnessClassDBRepository fitnessClassDBRepository;

    /**
     * Constructs a FeedbackDBRepository instance with the provided database connection details.
     * This also initializes the MemberDBRepository and FitnessClassDBRepository to handle member and class data.
     * Establishes a connection to the database using the specified URL, user, and password.
     * @param dbUrl      The URL of the database to connect to.
     * @param dbUser     The username to use when connecting to the database.
     * @param dbPassword The password to use when connecting to the database.
     * @throws RuntimeException If the connection cannot be established due to an SQLException.
     */
    public FeedbackDBRepository(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
        this.memberDBRepository = new MemberDBRepository(dbUrl, dbUser, dbPassword);
        this.fitnessClassDBRepository = new FitnessClassDBRepository(dbUrl, dbUser, dbPassword);
    }

    /**
     * Creates a new feedback record in the database.
     * This method inserts a new Feedback object into the database, including the feedback ID, the member and fitness class IDs,
     * the rating, and the comment associated with the feedback.
     * @param obj The Feedback object to be added to the database.
     * @throws RuntimeException If an SQL error occurs during the insert operation.
     */
    @Override
    public void create(Feedback obj) {
        String sql = "INSERT INTO Feedback (id, member, fitnessClass, rating, comment) VALUES(?, ?, ?, ?, ?)";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,obj.getId());
            statement.setInt(2,obj.getMember().getId());
            statement.setInt(3,obj.getFitnessClass().getId());
            statement.setInt(4,obj.getRating());
            statement.setString(5,obj.getComment());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves feedback from the database by its ID.
     * This method queries the database for a feedback record with the specified ID and returns the corresponding Feedback object.
     * It also fetches the associated Member and FitnessClass objects.
     * @param id The ID of the feedback to be retrieved.
     * @return The Feedback object corresponding to the specified ID, or null if no feedback is found.
     * @throws RuntimeException If an SQL error occurs during the read operation.
     */
    @Override
    public Feedback read(int id) {
        String sql = "SELECT * FROM Feedback WHERE id=?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                Member member = memberDBRepository.read(resultSet.getInt("id"));
                FitnessClass fitnessClass = fitnessClassDBRepository.read(resultSet.getInt("id"));
                return extractFromResultSet(resultSet, member, fitnessClass);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates an existing feedback record in the database.
     * This method updates the details of the specified Feedback object in the database, including the member ID, fitness class ID,
     * rating, and comment.
     * @param obj The Feedback object containing the updated information.
     * @throws RuntimeException If an SQL error occurs during the update operation.
     */
    @Override
    public void update(Feedback obj) {
        String sql = "UPDATE Feedback SET memberID=?, fitnessClassID=?, rating=?, comment=? WHERE id=?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,obj.getMember().getId());
            statement.setInt(2,obj.getFitnessClass().getId());
            statement.setInt(3,obj.getRating());
            statement.setString(4,obj.getComment());
            statement.setInt(5,obj.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes a feedback record from the database by its ID.
     * This method removes the feedback with the specified ID from the database.
     * @param id The ID of the feedback to be deleted.
     * @throws RuntimeException If an SQL error occurs during the delete operation.
     */
    @Override
    public void delete(int id) {
        String sql = "DELETE FROM Feedback WHERE id=?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves all feedback records from the database.
     * This method fetches all feedback entries present in the database and returns them as a list of Feedback objects.
     * Each Feedback object is populated with the associated Member and FitnessClass.
     * @return A list of all feedbacks in the database.
     * @throws RuntimeException If an SQL error occurs during the fetch operation.
     */
    @Override
    public List<Feedback> getAll() {
        String sql = "SELECT * FROM Feedback";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery();
            List<Feedback> feedbacks = new ArrayList<>();
            while(resultSet.next()){
                Member member = memberDBRepository.read(resultSet.getInt("id"));
                FitnessClass fitnessClass = fitnessClassDBRepository.read(resultSet.getInt("id"));
                feedbacks.add(extractFromResultSet(resultSet, member, fitnessClass));
            }
            return feedbacks;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Extracts the Feedback object from a ResultSet.
     * This helper method creates a Feedback object from a row in the ResultSet and populates it with the necessary details,
     * including the associated Member and FitnessClass.
     * @param resultSet The ResultSet object containing the feedback data.
     * @param member    The Member object associated with the feedback.
     * @param fitnessClass The FitnessClass object associated with the feedback.
     * @return A Feedback object populated with the data from the ResultSet.
     * @throws SQLException If an error occurs while reading data from the ResultSet.
     */
    private static Feedback extractFromResultSet(ResultSet resultSet, Member member, FitnessClass fitnessClass) throws SQLException {
        Feedback feedback = new Feedback(
                member,
                fitnessClass,
                resultSet.getInt("rating"),
                resultSet.getString("comment")
        );
        feedback.setId(resultSet.getInt("id"));
        return feedback;
    }
}
