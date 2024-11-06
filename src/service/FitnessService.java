package service;
import model.*;
import repository.IRepository;
import java.util.List;

public class FitnessService {

    private final IRepository<Equipment> equipmentRepository;
    private final IRepository<Feedback> feedbackRepository;
    private final IRepository<FitnessClass> fitnessClassRepository;
    private final IRepository<Location> locationRepository;
    private final IRepository<Member> memberRepository;
    private final IRepository<Membership> membershipRepository;
    private final IRepository<Reservation> reservationRepository;
    private final IRepository<Room> roomRepository;
    private final IRepository<Schedule> scheduleRepository;
    private final IRepository<Trainer> trainerRepository;
    private final IRepository<Attendance> attendanceRepository;
    private final IRepository<FitnessClassEquipment> fitnessClassEquipmentRepository;

    public FitnessService(IRepository<Equipment> equipmentRepository, IRepository<Feedback> feedbackRepository, IRepository<FitnessClass> fitnessClassRepository, IRepository<Location> locationRepository, IRepository<Member> memberRepository, IRepository<Membership> membershipRepository, IRepository<Reservation> reservationRepository, IRepository<Room> roomRepository, IRepository<Schedule> scheduleRepository, IRepository<Trainer> trainerRepository, IRepository<Attendance> attendanceRepository, IRepository<FitnessClassEquipment> fitnessClassEquipmentRepository) {
        this.equipmentRepository = equipmentRepository;
        this.feedbackRepository = feedbackRepository;
        this.fitnessClassRepository = fitnessClassRepository;
        this.locationRepository = locationRepository;
        this.memberRepository = memberRepository;
        this.membershipRepository = membershipRepository;
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
        this.scheduleRepository = scheduleRepository;
        this.trainerRepository = trainerRepository;
        this.attendanceRepository = attendanceRepository;
        this.fitnessClassEquipmentRepository = fitnessClassEquipmentRepository;
    }

    public Equipment getEquipment(int id){
        return equipmentRepository.read(id);
    }

    public void addEquipment(Equipment Equipment){
        if (equipmentRepository.read(Equipment.getID()) != null){
            throw new IllegalArgumentException("Equipment with ID" + Equipment.getID() + " already exists");
        }
        equipmentRepository.create(Equipment);
    }

    public void updateEquipment(Equipment Equipment){
        if(equipmentRepository.read((Equipment.getID())) == null){
            throw new IllegalArgumentException("Equipment with ID" + Equipment.getID() + " does not exist");
        }
        equipmentRepository.update(Equipment);
    }

    public void deleteEquipment(int id){
        if (equipmentRepository.read(id) == null){
            throw new IllegalArgumentException("Equipment with ID" + id + " does not exist");
        }
        equipmentRepository.delete(id);
    }

    public List<Equipment> getAllEquipments(){
        return equipmentRepository.getAll();
    }

    public Feedback getFeedback(int id){
        return feedbackRepository.read(id);
    }

    public void addFeedback(Feedback Feedback){
        if (feedbackRepository.read(Feedback.getID()) != null){
            throw new IllegalArgumentException("Feedback with ID" + Feedback.getID() + " already exists");
        }
        feedbackRepository.create(Feedback);
    }

    public void updateFeedback(Feedback Feedback){
        if(feedbackRepository.read((Feedback.getID())) == null){
            throw new IllegalArgumentException("Feedback with ID" + Feedback.getID() + " does not exist");
        }
        feedbackRepository.update(Feedback);
    }

    public void deleteFeedback(int id){
        if (feedbackRepository.read(id) == null){
            throw new IllegalArgumentException("Feedback with ID" + id + " does not exist");
        }
        feedbackRepository.delete(id);
    }

    public List<Feedback> getAllFeedbacks(){
        return feedbackRepository.getAll();
    }

    public FitnessClass getFitnessClass(int id){
        return fitnessClassRepository.read(id);
    }

    public void addFitnessClass(FitnessClass FitnessClass){
        if (fitnessClassRepository.read(FitnessClass.getID()) != null){
            throw new IllegalArgumentException("FitnessClass with ID" + FitnessClass.getID() + " already exists");
        }
        fitnessClassRepository.create(FitnessClass);
    }

    public void updateFitnessClass(FitnessClass FitnessClass){
        if(fitnessClassRepository.read((FitnessClass.getID())) == null){
            throw new IllegalArgumentException("FitnessClass with ID" + FitnessClass.getID() + " does not exist");
        }
        fitnessClassRepository.update(FitnessClass);
    }

    public void deleteFitnessClass(int id){
        if (fitnessClassRepository.read(id) == null){
            throw new IllegalArgumentException("FitnessClass with ID" + id + " does not exist");
        }
        fitnessClassRepository.delete(id);
    }

    public List<FitnessClass> getAllFitnessClasses(){
        return fitnessClassRepository.getAll();
    }

    public Location getLocation(int id){
        return locationRepository.read(id);
    }

    public void addLocation(Location Location){
        if (locationRepository.read(Location.getID()) != null){
            throw new IllegalArgumentException("Location with ID" + Location.getID() + " already exists");
        }
        locationRepository.create(Location);
    }

    public void updateLocation(Location Location){
        if(locationRepository.read((Location.getID())) == null){
            throw new IllegalArgumentException("Location with ID" + Location.getID() + " does not exist");
        }
        locationRepository.update(Location);
    }

    public void deleteLocation(int id){
        if (locationRepository.read(id) == null){
            throw new IllegalArgumentException("Location with ID" + id + " does not exist");
        }
        locationRepository.delete(id);
    }

    public List<Location> getAllLocations(){
        return locationRepository.getAll();
    }

    public Member getMember(int id) {
        return memberRepository.read(id);
    }

    public void addMember(Member member) {
        if (memberRepository.read(member.getID()) != null) {
            throw  new IllegalArgumentException("Member with ID " + member.getID() + " already exists.");
        }
        memberRepository.create(member);
    }

    public void updateMember(Member member) {
        if (memberRepository.read(member.getID()) == null) {
            throw new IllegalArgumentException("Member with ID " + member.getID() + " does not exist.");
        }
        memberRepository.update(member);
    }

    public void deleteMember(int id) {
        if (memberRepository.read(id) == null) {
            throw new IllegalArgumentException("Member with ID " + id + " does not exist.");
        }
        memberRepository.delete(id);
    }

    public List<Member> getAllMembers() {
        return memberRepository.getAll();
    }

    public Membership getMembership(int id) {
        return membershipRepository.read(id);
    }

    public void addMembership(Membership membership) {
        if (membershipRepository.read(membership.getID()) != null) {
            throw  new IllegalArgumentException("Membership with ID " + membership.getID() + " already exists.");
        }
        membershipRepository.create(membership);
    }

    public void updateMembership(Membership membership) {
        if (membershipRepository.read(membership.getID()) == null) {
            throw new IllegalArgumentException("Membership with ID " + membership.getID() + " does not exist.");
        }
        membershipRepository.update(membership);
    }

    public void deleteMembership(int id) {
        if (membershipRepository.read(id) == null) {
            throw new IllegalArgumentException("Membership with ID " + id + " does not exist.");
        }
        membershipRepository.delete(id);
    }

    public List<Membership> getAllMemberships() {
        return membershipRepository.getAll();
    }

    public Reservation getReservation(int id){
        return reservationRepository.read(id);
    }

    public void addReservation(Reservation reservation){
        if (reservationRepository.read(reservation.getID()) != null){
            throw new IllegalArgumentException("Reservation with ID" + reservation.getID() + " already exists");
        }
        reservationRepository.create(reservation);
    }

    public void updateReservation(Reservation reservation){
        if(reservationRepository.read((reservation.getID())) == null){
            throw new IllegalArgumentException("Reservation with ID" + reservation.getID() + " does not exist");
        }
        reservationRepository.update(reservation);
    }

    public void deleteReservation(int id){
        if (reservationRepository.read(id) == null){
            throw new IllegalArgumentException("Reservation with ID" + id + " does not exist");
        }
        reservationRepository.delete(id);
    }

    public List<Reservation> getAllReservations(){
        return reservationRepository.getAll();
    }

    public Room getRoom(int id){
        return roomRepository.read(id);
    }

    public void addRoom(Room room){
        if (roomRepository.read(room.getID()) != null){
            throw new IllegalArgumentException("Room with ID" + room.getID() + " already exists");
        }
        roomRepository.create(room);
    }

    public void updateRoom(Room room){
        if(roomRepository.read((room.getID())) == null){
            throw new IllegalArgumentException("Room with ID" + room.getID() + " does not exist");
        }
        roomRepository.update(room);
    }

    public void deleteRoom(int id){
        if (roomRepository.read(id) == null){
            throw new IllegalArgumentException("Room with ID" + id + " does not exist");
        }
        roomRepository.delete(id);
    }

    public List<Room> getAllRooms(){
        return roomRepository.getAll();
    }

    public Schedule getSchedule(int id){
        return scheduleRepository.read(id);
    }

    public void addSchedule(Schedule schedule){
        if (scheduleRepository.read(schedule.getID()) != null){
            throw new IllegalArgumentException("Schedule with ID" + schedule.getID() + " already exists");
        }
        scheduleRepository.create(schedule);
    }

    public void updateSchedule(Schedule schedule){
        if(scheduleRepository.read((schedule.getID())) == null){
            throw new IllegalArgumentException("Schedule with ID" + schedule.getID() + " does not exist");
        }
        scheduleRepository.update(schedule);
    }

    public void deleteSchedule(int id){
        if (scheduleRepository.read(id) == null){
            throw new IllegalArgumentException("Schedule with ID" + id + " does not exist");
        }
        scheduleRepository.delete(id);
    }

    public List<Schedule> getAllSchedules(){
        return scheduleRepository.getAll();
    }

    public Trainer getTrainer(int id){
        return trainerRepository.read(id);
    }

    public void addTrainer(Trainer trainer){
        if (trainerRepository.read(trainer.getID()) != null){
            throw new IllegalArgumentException("Trainer with ID" + trainer.getID() + " already exists");
        }
        trainerRepository.create(trainer);
    }

    public void updateTrainer(Trainer trainer){
        if(trainerRepository.read((trainer.getID())) == null){
            throw new IllegalArgumentException("Trainer with ID" + trainer.getID() + " does not exist");
        }
        trainerRepository.update(trainer);
    }

    public void deleteTrainer(int id){
        if (trainerRepository.read(id) == null){
            throw new IllegalArgumentException("Trainer with ID" + id + " does not exist");
        }
        trainerRepository.delete(id);
    }

    public List<Trainer> getAllTrainers(){
        return trainerRepository.getAll();
    }
}
