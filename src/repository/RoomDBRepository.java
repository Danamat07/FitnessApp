package repository;

import model.Location;
import model.Room;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The RoomDBRepository class provides CRUD operations for the `Room` model using a relational database.
 * It extends the generic DBRepository class, which provides a base implementation for managing database connections.
 */
public class RoomDBRepository extends DBRepository<Room> {

    /**
     * Constructs a RoomDBRepository with the specified database connection details.
     * @param dbUrl      The database URL.
     * @param dbUser     The username for the database connection.
     * @param dbPassword The password for the database connection.
     */
    public RoomDBRepository(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }

    /**
     * Adds a new room to the database.
     * @param obj The Room object to be added to the database.
     * @throws RuntimeException If a SQL error occurs while inserting the data.
     */
    @Override
    public void create(Room obj) {
        String sql = "INSERT INTO Room (id, name, maxCapacity, locationId) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, obj.getId());
            statement.setString(2, obj.getName());
            statement.setInt(3, obj.getMaxCapacity());
            statement.setInt(4, obj.getLocation().getId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create room", e);
        }
    }

    /**
     * Reads a room from the database by its ID.
     * @param id The ID of the room to retrieve.
     * @return The Room object corresponding to the specified ID, or null if no room is found.
     * @throws RuntimeException If a SQL error occurs while querying the data.
     */
    @Override
    public Room read(int id) {
        String sql = "SELECT r.*, l.name AS locationName, l.address AS locationAddress FROM Room r " +
                "INNER JOIN Location l ON r.locationId = l.id WHERE r.id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return extractFromResultSet(resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to read room", e);
        }
    }

    /**
     * Updates an existing room in the database.
     * @param obj The Room object containing the updated data.
     * @throws RuntimeException If a SQL error occurs while updating the data.
     */
    @Override
    public void update(Room obj) {
        String sql = "UPDATE Room SET name = ?, maxCapacity = ?, locationId = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, obj.getName());
            statement.setInt(2, obj.getMaxCapacity());
            statement.setInt(3, obj.getLocation().getId());
            statement.setInt(4, obj.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update room", e);
        }
    }

    /**
     * Deletes a room from the database by its ID.
     * @param id The ID of the room to be deleted.
     * @throws RuntimeException If a SQL error occurs while deleting the data.
     */
    @Override
    public void delete(int id) {
        String sql = "DELETE FROM Room WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete room", e);
        }
    }

    /**
     * Retrieves all rooms from the database.
     * @return A list of all Room objects stored in the database.
     * @throws RuntimeException If a SQL error occurs while retrieving the data.
     */
    @Override
    public List<Room> getAll() {
        String sql = "SELECT r.*, l.name AS locationName, l.address AS locationAddress FROM Room r " +
                "INNER JOIN Location l ON r.locationId = l.id";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            List<Room> rooms = new ArrayList<>();
            while (resultSet.next()) {
                rooms.add(extractFromResultSet(resultSet));
            }
            return rooms;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve all rooms", e);
        }
    }

    /**
     * Extracts a Room object from a ResultSet row.
     * @param resultSet The ResultSet containing the database row.
     * @return A Room object constructed from the database row data.
     * @throws SQLException If an error occurs while accessing the ResultSet.
     */
    private static Room extractFromResultSet(ResultSet resultSet) throws SQLException {
        Location location = new Location(
                resultSet.getString("locationName"),
                resultSet.getString("locationAddress")
        );
        location.setId(resultSet.getInt("locationId"));
        Room room = new Room(
                resultSet.getString("name"),
                resultSet.getInt("maxCapacity"),
                location
        );
        room.setId(resultSet.getInt("id"));
        return room;
    }
}
