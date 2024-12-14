package repository;

import model.Membership;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MembershipDBRepository extends DBRepository<Membership> {

    /**
     * Constructs a DBRepository instance with the provided database connection details.
     * Establishes a connection to the database using the specified URL, user, and password.
     * @param dbUrl      The URL of the database to connect to.
     * @param dbUser     The username to use when connecting to the database.
     * @param dbPassword The password to use when connecting to the database.
     */
    public MembershipDBRepository(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }

    /**
     * Adds a new membership to the database.
     * @param obj The Membership object to be added to the database.
     * @throws RuntimeException If a SQL error occurs while inserting the data.
     * @throws RuntimeException If the connection cannot be established due to an SQLException.
     */
    @Override
    public void create(Membership obj) {
        String sql = "INSERT INTO membership (id, type, price) VALUES (?, ?, ?)";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,obj.getId());
            statement.setString(2,obj.getType());
            statement.setFloat(3,obj.getPrice());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create membership", e);
        }
    }

    /**
     * Reads a membership from the database by its ID.
     * @param id The ID of the membership to retrieve.
     * @return The Membership object corresponding to the specified ID, or null if no location is found.
     * @throws RuntimeException If a SQL error occurs while querying the data.
     */
    @Override
    public Membership read(int id) {
        String sql = "SELECT * FROM membership WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return extractFromResultSet(resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to read membership", e);
        }
    }

    /**
     * Updates an existing membership in the database.
     * @param obj The Membership object containing the updated data.
     * @throws RuntimeException If a SQL error occurs while updating the data.
     */
    @Override
    public void update(Membership obj) {
        String sql = "UPDATE membership SET type = ?, price = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, obj.getType());
            statement.setFloat(2, obj.getPrice());
            statement.setInt(3, obj.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update membership", e);
        }
    }

    /**
     * Deletes a membership from the database by its ID.
     * @param id The ID of the membership to be deleted.
     * @throws RuntimeException If a SQL error occurs while deleting the data.
     */
    @Override
    public void delete(int id) {
        String sql = "DELETE FROM membership WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete membership", e);
        }
    }

    /**
     * Retrieves all memberships from the database.
     * @return A list of all Membership objects stored in the database.
     * @throws RuntimeException If a SQL error occurs while retrieving the data.
     */
    @Override
    public List<Membership> getAll() {
        String sql = "SELECT * FROM membership";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            List<Membership> memberships = new ArrayList<>();
            while (resultSet.next()) {
                memberships.add(extractFromResultSet(resultSet));
            }
            return memberships;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve all memberships", e);
        }
    }

    /**
     * Extracts a Membership object from a ResultSet row.
     * @param resultSet The ResultSet containing the database row.
     * @return A Membership object constructed from the database row data.
     * @throws SQLException If an error occurs while accessing the ResultSet.
     */
    private static Membership extractFromResultSet(ResultSet resultSet) throws SQLException {
        Membership membership = new Membership(
                resultSet.getString("type"),
                resultSet.getFloat("price")
        );
        membership.setId(resultSet.getInt("id"));
        return membership;
    }
}