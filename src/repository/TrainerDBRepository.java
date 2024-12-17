package repository;

import model.Trainer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The TrainerDBRepository class provides CRUD operations for the `Trainer` model using a relational database.
 * It extends the generic DBRepository class, which provides a base implementation for managing database connections.
 */
public class TrainerDBRepository extends DBRepository<Trainer> {

    /**
     * Constructs a TrainerDBRepository with the specified database connection details.
     * @param dbUrl      The database URL.
     * @param dbUser     The username for the database connection.
     * @param dbPassword The password for the database connection.
     * @throws RuntimeException If the connection cannot be established due to an SQLException.
     */
    public TrainerDBRepository(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }

    /**
     * Adds a new trainer to the database.
     * @param obj The Trainer object to be added to the database.
     * @throws RuntimeException If a SQL error occurs while inserting the data.
     */
    @Override
    public void create(Trainer obj) {
        String sql = "INSERT INTO trainer (id, name, password, specialisation) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, obj.getId());
            statement.setString(2, obj.getName());
            statement.setString(3, obj.getPassword());
            statement.setString(4, obj.getSpecialisation());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create trainer", e);
        }
    }

    /**
     * Reads a trainer from the database by its ID.
     * @param id The ID of the trainer to retrieve.
     * @return The Trainer object corresponding to the specified ID, or null if no trainer is found.
     * @throws RuntimeException If a SQL error occurs while querying the data.
     */
    @Override
    public Trainer read(int id) {
        String sql = "SELECT * FROM trainer WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return extractFromResultSet(resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to read trainer", e);
        }
    }

    /**
     * Updates an existing trainer in the database.
     * @param obj The Trainer object containing the updated data.
     * @throws RuntimeException If a SQL error occurs while updating the data.
     */
    @Override
    public void update(Trainer obj) {
        String sql = "UPDATE trainer SET name = ?, password = ?, specialisation = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, obj.getName());
            statement.setString(2, obj.getPassword());
            statement.setString(3, obj.getSpecialisation());
            statement.setInt(4, obj.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update trainer", e);
        }
    }

    /**
     * Deletes a trainer from the database by its ID.
     * @param id The ID of the trainer to be deleted.
     * @throws RuntimeException If a SQL error occurs while deleting the data.
     */
    @Override
    public void delete(int id) {
        String sql = "DELETE FROM trainer WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete trainer", e);
        }
    }

    /**
     * Retrieves all trainers from the database.
     * @return A list of all Trainer objects stored in the database.
     * @throws RuntimeException If a SQL error occurs while retrieving the data.
     */
    @Override
    public List<Trainer> getAll() {
        String sql = "SELECT * FROM trainer";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            List<Trainer> trainers = new ArrayList<>();
            while (resultSet.next()) {
                trainers.add(extractFromResultSet(resultSet));
            }
            return trainers;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve all trainers", e);
        }
    }

    /**
     * Extracts a Trainer object from a ResultSet row.
     * @param resultSet The ResultSet containing the database row.
     * @return A Trainer object constructed from the database row data.
     * @throws SQLException If an error occurs while accessing the ResultSet.
     */
    private static Trainer extractFromResultSet(ResultSet resultSet) throws SQLException {
        Trainer trainer = new Trainer(
                resultSet.getString("name"),
                resultSet.getString("password"),
                resultSet.getString("specialisation")
        );
        trainer.setId(resultSet.getInt("id"));
        return trainer;
    }
}
