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
        String sql = "INSERT INTO feedback (id, member, fitnessClass, rating, comment) VALUES(?, ?, ?, ?, ?)";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,obj.getId());
            statement.setInt(2,obj.getMember().getId());
            statement.setInt(3,obj.getFitnessClass().getId());
            statement.setInt(4,obj.getRating());
            statement.setString(5,obj.getComment());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Error while inserting feedback", e);
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
        String sql = "SELECT * FROM feedback WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return extractFromResultSet(resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while fetching feedback", e);
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
        String sql = "UPDATE feedback SET member=?, fitnessClass=?, rating=?, comment=? WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, obj.getMember().getId());
            statement.setInt(2, obj.getFitnessClass().getId());
            statement.setInt(3, obj.getRating());
            statement.setString(4, obj.getComment());
            statement.setInt(5, obj.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Error while updating feedback", e);
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
        String sql = "DELETE FROM feedback WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Error while deleting feedback", e);
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
        String sql = "SELECT * FROM feedback";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            List<Feedback> feedbacks = new ArrayList<>();
            while (resultSet.next()) {
                feedbacks.add(extractFromResultSet(resultSet));
            }
            return feedbacks;
        } catch (SQLException e) {
            throw new RuntimeException("Error while fetching all feedback", e);
        }
    }

    /**
     * Extracts the Feedback object from a ResultSet.
     * This helper method creates a Feedback object from a row in the ResultSet and populates it with the necessary details,
     * including the associated Member and FitnessClass.
     * @param resultSet The ResultSet object containing the feedback data.
     * @return A Feedback object populated with the data from the ResultSet.
     * @throws SQLException If an error occurs while reading data from the ResultSet.
     */
    private Feedback extractFromResultSet(ResultSet resultSet) throws SQLException {
        Member member = memberDBRepository.read(resultSet.getInt("member"));
        FitnessClass fitnessClass = fitnessClassDBRepository.read(resultSet.getInt("fitnessClass"));
        Feedback feedback = new Feedback(
                member,
                fitnessClass,
                resultSet.getInt("rating"),
                resultSet.getString("comment")
        );
        feedback.setId(resultSet.getInt("id"));
        return feedback;
    }

    /**
     * Retrieves a list of feedback for a specific fitness class identified by its ID.
     * The method performs a SQL query to fetch all feedback entries associated with the given fitness class ID.
     * @param classId The ID of the fitness class for which feedback is to be retrieved.
     * @return A list of Feedback objects associated with the given fitness class ID.
     * @throws SQLException If there is an error executing the SQL query or processing the result set.
     * @throws RuntimeException If any unexpected runtime exceptions occur during the method execution.
     */
    public ArrayList<Feedback> getFeedbackByClassId(int classId) {
        ArrayList<Feedback> feedbackList = new ArrayList<>();
        String sql = "SELECT * FROM feedback WHERE fitnessClass = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, classId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                feedbackList.add(extractFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve feedback for fitness class with id " + classId, e);
        }
        return feedbackList;
    }
}
