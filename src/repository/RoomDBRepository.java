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

    // Instance of LocationDBRepository to interact with location-related data in the database
    LocationDBRepository locationDBRepository;

    /**
     * Constructs a DBRepository instance with the provided database connection details.
     * It also initializes the LocationDBRepository to handle location data.
     * Establishes a connection to the database using the specified URL, user, and password.
     * @param dbUrl      The URL of the database to connect to.
     * @param dbUser     The username to use when connecting to the database.
     * @param dbPassword The password to use when connecting to the database.
     * @throws RuntimeException If the connection cannot be established due to an SQLException.
     */
    public RoomDBRepository(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
        this.locationDBRepository = new LocationDBRepository(dbUrl, dbUser, dbPassword);
    }

    /**
     * Creates a new room in the database.
     * This method inserts a new Room object into the database with details including id, name, max capacity, and associated location.
     * @param obj The Room object to be added to the database.
     * @throws RuntimeException If an SQL error occurs during the insert operation.
     */
    @Override
    public void create(Room obj) {
        String sql = "INSERT INTO Room (id, name, maxCapacity, location) VALUES(?, ?, ?, ?)";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,obj.getId());
            statement.setString(2,obj.getName());
            statement.setInt(3,obj.getMaxCapacity());
            statement.setInt(4,obj.getLocation().getId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create room", e);
        }
    }

    /**
     * Retrieves a room from the database by its ID.
     * This method queries the database for a room with the specified ID and returns the corresponding Room object.
     * It also fetches the location associated with the room.
     * @param id The ID of the room to be retrieved.
     * @return The Room object corresponding to the specified ID, or null if no room is found.
     * @throws RuntimeException If an SQL error occurs during the read operation.
     */
    @Override
    public Room read(int id) {
        String sql = "SELECT * FROM Room WHERE id=?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                Location location = locationDBRepository.read(resultSet.getInt("location"));
                return extractFromResultSet(resultSet, location);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to read room", e);
        }
    }

    /**
     * Updates an existing room in the database.
     * This method updates the details of the specified Room object in the database (name, max capacity, location).
     * @param obj The Room object containing the updated information.
     * @throws RuntimeException If an SQL error occurs during the update operation.
     */
    @Override
    public void update(Room obj) {
        String sql = "UPDATE Room SET name=?, maxCapacity=?, locationID=? WHERE id=?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,obj.getName());
            statement.setInt(2,obj.getMaxCapacity());
            statement.setInt(3,obj.getLocation().getId());
            statement.setInt(4,obj.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update room", e);
        }
    }

    /**
     * Deletes a room from the database by its ID.
     * This method removes the room with the specified ID from the database.
     * @param id The ID of the room to be deleted.
     * @throws RuntimeException If an SQL error occurs during the delete operation.
     */
    @Override
    public void delete(int id) {
        String sql = "DELETE FROM Room WHERE id=?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete room", e);
        }
    }

    /**
     * Retrieves all rooms from the database.
     * This method fetches all rooms present in the database and returns them as a list of Room objects.
     * Each room object is populated with its associated location.
     * @return A list of all rooms in the database.
     * @throws RuntimeException If an SQL error occurs during the fetch operation.
     */
    @Override
    public List<Room> getAll() {
        String sql = "SELECT * FROM Room";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery();
            List<Room> rooms = new ArrayList<>();
            while(resultSet.next()){
                Location location = locationDBRepository.read(resultSet.getInt("location"));
                rooms.add(extractFromResultSet(resultSet, location));
            }
            return rooms;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve all rooms", e);
        }
    }

    /**
     * Extracts the Room object from a ResultSet.
     * This helper method creates a Room object from a row in the ResultSet and populates it with the necessary details.
     * @param resultSet The ResultSet object containing the room data.
     * @param location  The Location object associated with the room.
     * @return A Room object populated with the data from the ResultSet.
     * @throws SQLException If an error occurs while reading data from the ResultSet.
     */
    private static Room extractFromResultSet(ResultSet resultSet, Location location) throws SQLException {
        Room room = new Room(
                resultSet.getString("name"),
                resultSet.getInt("maxCapacity"),
                location
        );
        room.setId(resultSet.getInt("id"));
        return room;
    }
}