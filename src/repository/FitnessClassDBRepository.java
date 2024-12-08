package repository;

import model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class FitnessClassDBRepository extends DBRepository<FitnessClass> {

    TrainerDBRepository trainerDBRepository;
    RoomDBRepository roomDBRepository;
    LocationDBRepository locationDBRepository;
    FeedbackDBRepository feedbackDBRepository;
    MemberDBRepository memberDBRepository;
    EquipmentDBRepository equipmentDBRepository;

    public FitnessClassDBRepository(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
        this.trainerDBRepository = new TrainerDBRepository(dbUrl, dbUser, dbPassword);
        this.roomDBRepository = new RoomDBRepository(dbUrl, dbUser, dbPassword);
        this.locationDBRepository = new LocationDBRepository(dbUrl, dbUser, dbPassword);
        this.feedbackDBRepository = new FeedbackDBRepository(dbUrl, dbUser, dbPassword);
        this.memberDBRepository = new MemberDBRepository(dbUrl, dbUser, dbPassword);
        this.equipmentDBRepository = new EquipmentDBRepository(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void create(FitnessClass obj) {
        String sql = "INSERT INTO FitnessClass (id ,name, startTime, endTime, trainer, room, participantsCount, location) VALUES(?, ?, ?)";
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
            throw new RuntimeException("Failed to create FitnessClass", e);
        }
    }

    @Override
    public FitnessClass read(int id) {
        String sql = "SELECT * FROM FitnessClass WHERE id=?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return extractFromResultSet(resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to read FitnessClass", e);
        }
    }

    @Override
    public void update(FitnessClass obj) {
        String sql = "UPDATE FitnessClass SET name=?, startTime=?, endTime=?, trainer=?, room=?, " +
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
            throw new RuntimeException("Failed to update room", e);
        }
    }

    @Override
    public void delete(int id) {
        String sql1 = "DELETE FROM FitnessClass WHERE id=?";
        try(PreparedStatement statement = connection.prepareStatement(sql1)){
            statement.setInt(1,id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String sql2 = "DELETE FROM MemberFitnessClass WHERE classID=?";
        try(PreparedStatement statement = connection.prepareStatement(sql2)){
            statement.setInt(1,id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String sql3 = "DELETE FROM EquipmentFitnessClass WHERE classID=?";
        try(PreparedStatement statement = connection.prepareStatement(sql3)){
            statement.setInt(1,id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<FitnessClass> getAll() {
        String sql = "SELECT * FROM FitnessClass";
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

    private ArrayList<Equipment> getFitnessClassEquipment(Integer id) {
        ArrayList<Equipment> equipment = new ArrayList<>();
        String sqlEquipment =
                "SELECT eq.id, eq.name, eq.quantity, eq.fitnessClasses" +
                        " FROM Equipment as eq "
                        + "where id in (SELECT EquipmentFitnessClass.equipmentID FROM EquipmentFitnessClass WHERE classID = ?)";
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

    private ArrayList<Member> getFitnessClassMembers(Integer id) {
        ArrayList<Member> members = new ArrayList<>();
        String sqlMembers =
                "SELECT m.id, m.name, m.password, m.registrationDate, m.membership, m.fitnessClasses" +
                        " FROM Member as m "
                        + "where id in (SELECT MemberFitnessClass.memberID FROM MemberFitnessClass WHERE classID = ?)";
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
