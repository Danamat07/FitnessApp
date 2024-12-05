package repository;

import model.Location;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

/**
 * The LocationDBRepository class provides CRUD operations for the `Location` model using a relational database.
 * It extends the generic DBRepository class, which provides a base implementation for managing database connections.
 */
public class LocationDBRepository extends DBRepository<Location> {

    /**
     * Constructs a LocationDBRepository with the specified database connection details.
     * @param dbUrl      The database URL.
     * @param dbUser     The username for the database connection.
     * @param dbPassword The password for the database connection.
     */
    public LocationDBRepository(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }

    /**
     * Adds a new location to the database.
     * @param obj The Location object to be added to the database.
     * @throws RuntimeException If a SQL error occurs while inserting the data.
     */
    @Override
    public void create(Location obj) {
        String sql = "INSERT INTO Location (id, name, address) VALUES (?, ?, ?)";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,obj.getId());
            statement.setString(2,obj.getName());
            statement.setString(3,obj.getAddress());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create location", e);
        }
    }

    /**
     * Reads a location from the database by its ID.
     * @param id The ID of the location to retrieve.
     * @return The Location object corresponding to the specified ID, or null if no location is found.
     * @throws RuntimeException If a SQL error occurs while querying the data.
     */
    @Override
    public Location read(int id) {
        String sql = "SELECT * FROM Location WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return extractFromResultSet(resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to read location", e);
        }
    }

    /**
     * Updates an existing location in the database.
     * @param obj The Location object containing the updated data.
     * @throws RuntimeException If a SQL error occurs while updating the data.
     */
    @Override
    public void update(Location obj) {
        String sql = "UPDATE Location SET name = ?, address = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, obj.getName());
            statement.setString(2, obj.getAddress());
            statement.setInt(3, obj.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update location", e);
        }
    }

    /**
     * Deletes a location from the database by its ID.
     * @param id The ID of the location to be deleted.
     * @throws RuntimeException If a SQL error occurs while deleting the data.
     */
    @Override
    public void delete(int id) {
        String sql = "DELETE FROM Location WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete location", e);
        }
    }

    /**
     * Retrieves all locations from the database.
     * @return A list of all Location objects stored in the database.
     * @throws RuntimeException If a SQL error occurs while retrieving the data.
     */
    @Override
    public List<Location> getAll() {
        String sql = "SELECT * FROM Location";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            List<Location> locations = new ArrayList<>();
            while (resultSet.next()) {
                locations.add(extractFromResultSet(resultSet));
            }
            return locations;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve all locations", e);
        }
    }

    /**
     * Extracts a Location object from a ResultSet row.
     * @param resultSet The ResultSet containing the database row.
     * @return A Location object constructed from the database row data.
     * @throws SQLException If an error occurs while accessing the ResultSet.
     */
    private static Location extractFromResultSet(ResultSet resultSet) throws SQLException {
        Location location = new Location(
                resultSet.getString("name"),
                resultSet.getString("address")
        );
        location.setId(resultSet.getInt("id"));
        return location;
    }
}
