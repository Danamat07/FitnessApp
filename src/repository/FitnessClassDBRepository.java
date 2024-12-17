package repository;

import model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * The FitnessClassDBRepository class provides CRUD operations for the `FitnessClass` model using a relational database.
 * It extends the generic {@code DBRepository} class, which provides a base implementation for managing database connections.
 * This class handles operations such as creating, retrieving, updating, and deleting fitness class data in the database.
 */
public class FitnessClassDBRepository extends DBRepository<FitnessClass> {

    // Repositories for related data models
    TrainerDBRepository trainerDBRepository;
    RoomDBRepository roomDBRepository;
    LocationDBRepository locationDBRepository;
    FeedbackDBRepository feedbackDBRepository;
    MemberDBRepository memberDBRepository;
    EquipmentDBRepository equipmentDBRepository;

    /**
     * Constructs a {@code FitnessClassDBRepository} instance with the provided database connection details.
     * @param dbUrl      The URL of the database to connect to.
     * @param dbUser     The username to use when connecting to the database.
     * @param dbPassword The password to use when connecting to the database.
     * @throws RuntimeException If the connection cannot be established due to an {@code SQLException}.
     */
    public FitnessClassDBRepository(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
        this.trainerDBRepository = new TrainerDBRepository(dbUrl, dbUser, dbPassword);
        this.roomDBRepository = new RoomDBRepository(dbUrl, dbUser, dbPassword);
        this.locationDBRepository = new LocationDBRepository(dbUrl, dbUser, dbPassword);
        this.feedbackDBRepository = new FeedbackDBRepository(dbUrl, dbUser, dbPassword);
        this.memberDBRepository = new MemberDBRepository(dbUrl, dbUser, dbPassword);
        this.equipmentDBRepository = new EquipmentDBRepository(dbUrl, dbUser, dbPassword);
    }

    /**
     * Inserts a new `FitnessClass` into the database.
     * <p>This method prepares an SQL `INSERT` statement to add the `FitnessClass`'s details (ID, name, times, trainer, room,
     * participants count, and location) to the `fitnessClass` table.</p>
     * @param obj The `FitnessClass` object to insert into the database.
     * @throws RuntimeException If an error occurs during the insertion process.
     */
    @Override
    public void create(FitnessClass obj) {
        String sql = "INSERT INTO fitnessClass (id ,name, startTime, endTime, trainer, room, participantsCount, location) VALUES(?, ?, ?)";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,obj.getId());
            statement.setString(2,obj.getName());
            statement.setTimestamp(3, Timestamp.valueOf(obj.getStartTime()));
            statement.setTimestamp(4, Timestamp.valueOf(obj.getEndTime()));
            statement.setInt(5,obj.getTrainer().getId());
            statement.setInt(6,obj.getRoom().getId());
            statement.setInt(7,obj.getParticipantsCount());
            statement.setInt(8,obj.getLocation().getId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create fitness class", e);
        }
    }

    /**
     * Retrieves a `FitnessClass` from the database by its ID.
     * <p>This method executes a SQL `SELECT` query to fetch a fitness class's details based on the provided ID.</p>
     * @param id The ID of the `FitnessClass` to retrieve.
     * @return The `FitnessClass` object if found, or `null` if not.
     * @throws RuntimeException If an error occurs during the database query.
     */
    @Override
    public FitnessClass read(int id) {
        String sql = "SELECT * FROM fitnessClass WHERE id=?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return extractFromResultSet(resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to read fitness class", e);
        }
    }

    /**
     * Updates the details of an existing `FitnessClass` in the database.
     * <p>This method executes an SQL `UPDATE` query to modify the properties of a fitness class using the provided `FitnessClass` object.</p>
     * @param obj The `FitnessClass` object containing the updated details.
     * @throws RuntimeException If an error occurs during the database query.
     */
    @Override
    public void update(FitnessClass obj) {
        String sql = "UPDATE fitnessClass SET name=?, startTime=?, endTime=?, trainer=?, room=?, " +
                "participantsCount=?, location=? WHERE id=?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,obj.getName());
            statement.setString(2,obj.getName());
            statement.setTimestamp(3, Timestamp.valueOf(obj.getStartTime()));
            statement.setTimestamp(4, Timestamp.valueOf(obj.getEndTime()));
            statement.setInt(5,obj.getTrainer().getId());
            statement.setInt(6,obj.getRoom().getId());
            statement.setInt(7,obj.getParticipantsCount());
            statement.setInt(8,obj.getLocation().getId());
            statement.setInt(9,obj.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update fitness class", e);
        }
    }

    /**
     * Deletes a `FitnessClass` and its related records from the database.
     * <p>This method first deletes the fitness class from the `fitnessClass` table, then removes its associations
     * in the `member_fitnessClass` and `equipment_fitnessClass` tables.</p>
     * @param id The ID of the `FitnessClass` to delete.
     * @throws RuntimeException If an error occurs during the deletion process.
     */
    @Override
    public void delete(int id) {
        String sql1 = "DELETE FROM fitnessClass WHERE id=?";
        try(PreparedStatement statement = connection.prepareStatement(sql1)){
            statement.setInt(1,id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String sql2 = "DELETE FROM member_fitnessClass WHERE classID=?";
        try(PreparedStatement statement = connection.prepareStatement(sql2)){
            statement.setInt(1,id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String sql3 = "DELETE FROM equipment_fitnessClass WHERE classID=?";
        try(PreparedStatement statement = connection.prepareStatement(sql3)){
            statement.setInt(1,id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves all fitness classes from the database.
     * <p>This method executes a query to fetch all records from the `fitnessClass` table and returns them as a list.</p>
     * @return A list of all `FitnessClass` objects from the database.
     * @throws RuntimeException If an error occurs while retrieving the fitness classes.
     */
    @Override
    public List<FitnessClass> getAll() {
        String sql = "SELECT * FROM fitnessClass";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery();
            List<FitnessClass> fitnessClasses = new ArrayList<>();
            while(resultSet.next()){
                fitnessClasses.add(extractFromResultSet(resultSet));
            }
            return fitnessClasses;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve all fitness classes", e);
        }
    }

    /**
     * Extracts a `FitnessClass` object from a `ResultSet` based on the current row.
     * <p>This method retrieves a fitness class' details, including its trainer, room, location, feedback, members, and equipment,
     * from the `ResultSet`, and returns a fully populated `FitnessClass` object.</p>
     * @param resultSet The `ResultSet` containing the data of a fitness class.
     * @return A `FitnessClass` object populated with data from the `ResultSet`.
     * @throws SQLException If an error occurs while reading data from the `ResultSet`.
     */
    public FitnessClass extractFromResultSet(ResultSet resultSet) throws SQLException {
        Trainer trainer = trainerDBRepository.read(resultSet.getInt("trainer"));
        Room room = roomDBRepository.read(resultSet.getInt("room"));
        Location location = locationDBRepository.read(resultSet.getInt("location"));
        ArrayList<Feedback> feedbacks = feedbackDBRepository.getFeedbackByClassId(resultSet.getInt("id"));
        ArrayList<Member> members = getFitnessClassMembers(resultSet.getInt("id"));
        ArrayList<Equipment> equipment = getFitnessClassEquipment(resultSet.getInt("id"));
        FitnessClass fitnessClass = new FitnessClass(
                resultSet.getString("name"),
                resultSet.getTimestamp("startTime").toLocalDateTime(),
                resultSet.getTimestamp("endTime").toLocalDateTime(),
                trainer,
                room,
                resultSet.getInt("participantsCount"),
                location,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );
        fitnessClass.setFeedback(feedbacks);
        fitnessClass.setMembers(members);
        fitnessClass.setEquipment(equipment);
        fitnessClass.setId(resultSet.getInt("id"));
        return fitnessClass;
    }

    /**
     * Retrieves the list of equipment associated with a specific fitness class.
     * <p>This method queries the database to get all equipment related to the provided fitness class ID.</p>
     * @param id The ID of the fitness class for which equipment is being retrieved.
     * @return An `ArrayList` of `Equipment` objects associated with the given fitness class.
     * @throws RuntimeException If there is an issue executing the query or processing the result.
     */
    private ArrayList<Equipment> getFitnessClassEquipment(Integer id) {
        ArrayList<Equipment> equipment = new ArrayList<>();
        String sqlEquipment =
                "SELECT eq.id, eq.name, eq.quantity" +
                        " FROM equipment as eq "
                        + "where id in (SELECT equipment_fitnessClass.equipmentID FROM equipment_fitnessClass WHERE classID = ?)";
        try(PreparedStatement statementEquipment = connection.prepareStatement(sqlEquipment)){
            statementEquipment.setInt(1, id);
            ResultSet resultEquipment = statementEquipment.executeQuery();
            while(resultEquipment.next()){
                equipment.add(equipmentDBRepository.extractFromResultSet(resultEquipment));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return equipment;
    }

    /**
     * Retrieves the list of members enrolled in a specific fitness class.
     * <p>This method queries the database to get all members associated with the provided fitness class ID.</p>
     * @param id The ID of the fitness class for which members are being retrieved.
     * @return An `ArrayList` of `Member` objects associated with the given fitness class.
     * @throws RuntimeException If there is an issue executing the query or processing the result.
     */
    private ArrayList<Member> getFitnessClassMembers(Integer id) {
        ArrayList<Member> members = new ArrayList<>();
        String sqlMembers =
                "SELECT m.id, m.name, m.password, m.registrationDate, m.membership" +
                        " FROM member as m "
                        + "where id in (SELECT member_fitnessClass.memberID FROM member_fitnessClass WHERE classID = ?)";
        try(PreparedStatement statementMembers = connection.prepareStatement(sqlMembers)){
            statementMembers.setInt(1, id);
            ResultSet resultMembers = statementMembers.executeQuery();
            while(resultMembers.next()){
                members.add(memberDBRepository.extractFromResultSet(resultMembers));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return members;
    }
}
