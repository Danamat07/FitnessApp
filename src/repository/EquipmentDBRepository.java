package repository;

import model.Equipment;
import model.FitnessClass;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The EquipmentDBRepository class provides CRUD operations for the `Equipment` model using a relational database.
 * It extends the generic DBRepository class, which provides a base implementation for managing database connections.
 */
public class EquipmentDBRepository extends DBRepository<Equipment> {

    // Instance of FitnessClassDBRepository to handle FitnessClass-related data
    FitnessClassDBRepository fitnessClassDBRepository;

    /**
     * Constructs an EquipmentDBRepository instance with the provided database connection details.
     * It also initializes the FitnessClassDBRepository to handle fitness class data.
     * @param dbUrl      The URL of the database to connect to.
     * @param dbUser     The username to use when connecting to the database.
     * @param dbPassword The password to use when connecting to the database.
     * @throws RuntimeException If the connection cannot be established due to an SQLException.
     */
    public EquipmentDBRepository(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
        this.fitnessClassDBRepository = new FitnessClassDBRepository(dbUrl, dbUser, dbPassword);
    }

    /**
     * Adds new equipment to the database.
     * @param obj The Equipment object to be added to the database.
     * @throws RuntimeException If a SQL error occurs while inserting the data.
     */
    @Override
    public void create(Equipment obj) {
        String sql = "INSERT INTO equipment (id ,name, quantity) VALUES(?, ?, ?)";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,obj.getId());
            statement.setString(2,obj.getName());
            statement.setInt(3,obj.getQuantity());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create equipment", e);
        }
    }

    /**
     * Reads equipment from the database by its ID.
     * @param id The ID of the equipment to retrieve.
     * @return The Equipment object corresponding to the specified ID, or null if no equipment is found.
     * @throws RuntimeException If a SQL error occurs while querying the data.
     */
    @Override
    public Equipment read(int id) {
        String sql = "SELECT * FROM equipment WHERE id=?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return extractFromResultSet(resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to read equipment", e);
        }
    }

    /**
     * Updates existing equipment in the database.
     * @param obj The Equipment object containing the updated data.
     * @throws RuntimeException If a SQL error occurs while updating the data.
     */
    @Override
    public void update(Equipment obj) {
        String sql = "UPDATE equipment SET name=?, quantity=? WHERE id=?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,obj.getName());
            statement.setInt(2,obj.getQuantity());
            statement.setInt(3,obj.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update equipment", e);
        }
    }

    /**
     * Deletes equipment from the database by its ID.
     * @param id The ID of the equipment to be deleted.
     * @throws RuntimeException If a SQL error occurs while deleting the data.
     */
    @Override
    public void delete(int id) {
        String sql = "DELETE FROM equipment WHERE id=?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete equipment", e);
        }
        String sql2 = "DELETE FROM equipment_fitnessClass WHERE equipmentID=?";
        try(PreparedStatement statement = connection.prepareStatement(sql2)){
            statement.setInt(1,id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves all equipment from the database.
     * @return A list of all Equipment objects stored in the database.
     * @throws RuntimeException If a SQL error occurs while retrieving the data.
     */
    @Override
    public List<Equipment> getAll() {
        String sql = "SELECT * FROM equipment";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            List<Equipment> equipmentList = new ArrayList<>();
            while (resultSet.next()) {
                ArrayList<FitnessClass> fitnessClasses = getEquipmentFitnessClasses(resultSet.getInt("id"));
                equipmentList.add(extractFromResultSet(resultSet));
            }
            return equipmentList;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve all equipment", e);
        }
    }

    /**
     * Extracts an Equipment object from a ResultSet row.
     * @param resultSet The ResultSet containing the database row.
     * @return An Equipment object constructed from the database row data.
     * @throws SQLException If an error occurs while accessing the ResultSet.
     */
    public Equipment extractFromResultSet(ResultSet resultSet) throws SQLException {
        try {
            ArrayList<FitnessClass> fitnessClasses = getEquipmentFitnessClasses(resultSet.getInt("id"));
            Equipment equipment = new Equipment(
                    resultSet.getString("name"),
                    resultSet.getInt("quantity"),
                    new ArrayList<>()
            );
            equipment.setFitnessClasses(fitnessClasses);
            equipment.setId(resultSet.getInt("id"));
            return equipment;
        } catch (SQLException e) {
            throw new RuntimeException("Error extracting equipment from ResultSet", e);
        }
    }

    private ArrayList<FitnessClass> getEquipmentFitnessClasses(Integer id) {
        ArrayList<FitnessClass> fitnessClasses = new ArrayList<>();
        String sqlClasses =
                "SELECT fc.id, fc.name, fc.startTime, fc.endTime, fc.trainer, fc.room, fc.participantsCount, " +
                        "fc.location" +
                        " FROM fitnessClass as fc "
                        + "where id in (SELECT equipment_fitnessClass.classID FROM equipment_fitnessClass WHERE equipmentID = ?)";
        try(PreparedStatement statementClasses = connection.prepareStatement(sqlClasses)){
            statementClasses.setInt(1, id);
            ResultSet resultClasses = statementClasses.executeQuery();
            while(resultClasses.next()){
                fitnessClasses.add(fitnessClassDBRepository.extractFromResultSet(resultClasses));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return fitnessClasses;
    }
}
