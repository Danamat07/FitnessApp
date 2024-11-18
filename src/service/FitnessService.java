package service;
import model.*;
import repository.IRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FitnessService {

    private final IRepository<Equipment> equipmentRepository;
    private final IRepository<Feedback> feedbackRepository;
    private final IRepository<FitnessClass> fitnessClassRepository;
    private final IRepository<Location> locationRepository;
    private final IRepository<Member> memberRepository;
    private final IRepository<Membership> membershipRepository;
    private final IRepository<Reservation> reservationRepository;
    private final IRepository<Room> roomRepository;
    private final IRepository<Trainer> trainerRepository;

    // Service constructor
    public FitnessService(IRepository<Equipment> equipmentRepository, IRepository<Feedback> feedbackRepository, IRepository<FitnessClass> fitnessClassRepository, IRepository<Location> locationRepository, IRepository<Member> memberRepository, IRepository<Membership> membershipRepository, IRepository<Reservation> reservationRepository, IRepository<Room> roomRepository, IRepository<Trainer> trainerRepository) {
        this.equipmentRepository = equipmentRepository;
        this.feedbackRepository = feedbackRepository;
        this.fitnessClassRepository = fitnessClassRepository;
        this.locationRepository = locationRepository;
        this.memberRepository = memberRepository;
        this.membershipRepository = membershipRepository;
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
        this.trainerRepository = trainerRepository;
    }

    // ----- Equipment -----

    // Get equipment by ID
    public Equipment getEquipment(int id) {
        Equipment equipment = equipmentRepository.read(id);
        if (equipment == null) {
            throw new IllegalArgumentException("Equipment with ID " + id + " does not exist.");
        }
        return equipment;
    }

    // Add new equipment
    public void addEquipment(String name, int quantity, List<FitnessClass> fitnessClasses) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Equipment name cannot be null or empty.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        int newID = 1;
        List<Equipment> allEquipment = equipmentRepository.getAll();
        for (Equipment equipment : allEquipment) {
            if (equipment.getId() >= newID) {
                newID = equipment.getId() + 1;
            }
        }
        Equipment newEquipment = new Equipment(name, quantity, fitnessClasses);
        newEquipment.setId(newID);
        equipmentRepository.create(newEquipment);
    }

    // Update existing equipment
    public void updateEquipment(int id, String name, int quantity, List<FitnessClass> fitnessClasses) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Equipment name cannot be null or empty.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        Equipment existingEquipment = getEquipment(id);
        existingEquipment.setName(name);
        existingEquipment.setQuantity(quantity);
        existingEquipment.setFitnessClasses(fitnessClasses);
        equipmentRepository.update(existingEquipment);
    }

    // Delete equipment by ID
    public void deleteEquipment(int id) {
        Equipment existingEquipment = getEquipment(id);
        if (existingEquipment == null) {
            throw new IllegalArgumentException("Equipment with ID " + id + " does not exist.");
        }
        equipmentRepository.delete(id);
    }

    // Get all equipment
    public List<Equipment> getAllEquipment() {
        List<Equipment> equipmentList = equipmentRepository.getAll();
        if (equipmentList.isEmpty()) {
            throw new IllegalStateException("No equipment available.");
        }
        return equipmentList;
    }

    // ----- Feedback -----

    // Get feedback by ID
    public Feedback getFeedback(int id) {
        Feedback feedback = feedbackRepository.read(id);
        if (feedback == null) {
            throw new IllegalArgumentException("Feedback with ID " + id + " does not exist.");
        }
        return feedback;
    }

    // Add new feedback
    public void addFeedback(Member member, FitnessClass fitnessClass, int rating, String comment) {
        if (member == null) {
            throw new IllegalArgumentException("Member cannot be null.");
        }
        if (fitnessClass == null) {
            throw new IllegalArgumentException("Fitness class cannot be null.");
        }
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }
        if (comment == null || comment.trim().isEmpty()) {
            throw new IllegalArgumentException("Comment cannot be null or empty.");
        }
        int newID = 1;
        List<Feedback> allFeedbacks = feedbackRepository.getAll();
        for (Feedback feedback : allFeedbacks) {
            if (feedback.getId() >= newID) {
                newID = feedback.getId() + 1;
            }
        }
        Feedback newFeedback = new Feedback(member, fitnessClass, rating, comment);
        newFeedback.setId(newID);
        feedbackRepository.create(newFeedback);
    }

    // Update existing feedback
    public void updateFeedback(int id, Member member, FitnessClass fitnessClass, int rating, String comment) {
        if (member == null) {
            throw new IllegalArgumentException("Member cannot be null.");
        }
        if (fitnessClass == null) {
            throw new IllegalArgumentException("Fitness class cannot be null.");
        }
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }
        if (comment == null || comment.trim().isEmpty()) {
            throw new IllegalArgumentException("Comment cannot be null or empty.");
        }
        Feedback existingFeedback = getFeedback(id);
        existingFeedback.setMember(member);
        existingFeedback.setFitnessClass(fitnessClass);
        existingFeedback.setRating(rating);
        existingFeedback.setComment(comment);
        feedbackRepository.update(existingFeedback);
    }

    // Delete feedback by ID
    public void deleteFeedback(int id) {
        Feedback existingFeedback = getFeedback(id);
        if (existingFeedback == null) {
            throw new IllegalArgumentException("Feedback with ID " + id + " does not exist.");
        }
        feedbackRepository.delete(id);
    }

    // Get all feedback
    public List<Feedback> getAllFeedback() {
        List<Feedback> feedbackList = feedbackRepository.getAll();
        if (feedbackList.isEmpty()) {
            throw new IllegalStateException("No feedback available.");
        }
        return feedbackList;
    }

    // ----- Fitness Class -----

    // Get fitness class by ID
    public FitnessClass getFitnessClass(int id) {
        FitnessClass fitnessClass = fitnessClassRepository.read(id);
        if (fitnessClass == null) {
            throw new IllegalArgumentException("Fitness class with ID " + id + " does not exist.");
        }
        return fitnessClass;
    }

    // Add new fitness class
    public void addFitnessClass(String name, LocalDateTime startTime, LocalDateTime endTime, Trainer trainer, Room room, int participantsCount,
                                Location location, List<Feedback> feedback,
                                List<Member> members, List<Equipment> equipment) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Class name cannot be null or empty.");
        }
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Start time can't be after end time.");
        }
        if (trainer == null) {
            throw new IllegalArgumentException("Trainer cannot be null.");
        }
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null.");
        }
        if (participantsCount < 0) {
            throw new IllegalArgumentException("Participants count cannot be negative.");
        }
        int newID = 1;
        List<FitnessClass> allClasses = fitnessClassRepository.getAll();
        for (FitnessClass fitnessClass : allClasses) {
            if (fitnessClass.getId() >= newID) {
                newID = fitnessClass.getId() + 1;
            }
        }
        FitnessClass newFitnessClass = new FitnessClass(name, startTime, endTime, trainer, room, participantsCount, location, feedback, members, equipment);
        newFitnessClass.setId(newID);
        fitnessClassRepository.create(newFitnessClass);
    }

    // Update an existing fitness class
    public void updateFitnessClass(int id, String name, LocalDateTime startTime, LocalDateTime endTime, Trainer trainer, Room room,
                                   int participantsCount, Location location,
                                   List<Feedback> feedback, List<Member> members, List<Equipment> equipment) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Class name cannot be null or empty.");
        }
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Start time can't be after end time.");
        }
        if (trainer == null) {
            throw new IllegalArgumentException("Trainer cannot be null.");
        }
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null.");
        }
        if (participantsCount < 0) {
            throw new IllegalArgumentException("Participants count cannot be negative.");
        }
        FitnessClass existingFitnessClass = getFitnessClass(id);
        existingFitnessClass.setName(name);
        existingFitnessClass.setStartTime(startTime);
        existingFitnessClass.setEndTime(endTime);
        existingFitnessClass.setTrainer(trainer);
        existingFitnessClass.setRoom(room);
        existingFitnessClass.setParticipantsCount(participantsCount);
        existingFitnessClass.setLocation(location);
        existingFitnessClass.setFeedback(feedback);
        existingFitnessClass.setMembers(members);
        existingFitnessClass.setEquipment(equipment);
        fitnessClassRepository.update(existingFitnessClass);
    }

    // Delete fitness class by ID
    public void deleteFitnessClass(int id) {
        FitnessClass existingFitnessClass = getFitnessClass(id);
        if (existingFitnessClass == null) {
            throw new IllegalArgumentException("Fitness class with ID " + id + " does not exist.");
        }
        fitnessClassRepository.delete(id);
    }

    // Get all fitness classes
    public List<FitnessClass> getAllFitnessClasses() {
        List<FitnessClass> fitnessClasses = fitnessClassRepository.getAll();
        if (fitnessClasses.isEmpty()) {
            throw new IllegalStateException("No fitness classes available.");
        }
        return fitnessClasses;
    }

    // ----- Location -----

    // Get a location by ID
    public Location getLocation(int id) {
        Location location = locationRepository.read(id);
        if (location == null) {
            throw new IllegalArgumentException("Location with ID " + id + " does not exist.");
        }
        return location;
    }

    // Add a new location
    public void addLocation(String name, String address) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Location name cannot be null or empty.");
        }
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Location address cannot be null or empty.");
        }
        int newID = 1;
        List<Location> allLocations = locationRepository.getAll();
        for (Location location : allLocations) {
            if (location.getId() >= newID) {
                newID = location.getId() + 1;
            }
        }
        Location newLocation = new Location(name, address);
        newLocation.setId(newID);
        locationRepository.create(newLocation);
    }

    // Update an existing location
    public void updateLocation(int id, String name, String address) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Location name cannot be null or empty.");
        }
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Location address cannot be null or empty.");
        }
        Location existingLocation = getLocation(id);
        existingLocation.setName(name);
        existingLocation.setAddress(address);
        locationRepository.update(existingLocation);
    }

    // Delete a location by ID
    public void deleteLocation(int id) {
        Location existingLocation = getLocation(id);
        if (existingLocation == null) {
            throw new IllegalArgumentException("Location with ID " + id + " does not exist.");
        }
        locationRepository.delete(id);
    }

    // Get all locations
    public List<Location> getAllLocations() {
        List<Location> locations = locationRepository.getAll();
        if (locations.isEmpty()) {
            throw new IllegalStateException("No locations available.");
        }
        return locations;
    }

    // ----- Member -----

    // Get a member by ID
    public Member getMember(int id) {
        Member member = memberRepository.read(id);
        if (member == null) {
            throw new IllegalArgumentException("Member with ID " + id + " does not exist.");
        }
        return member;
    }

    // Add a new member
    public void addMember(String name, String mail, String phone, LocalDate registrationDate, String membershipType, List<FitnessClass> fitnessClasses) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        if (mail == null || mail.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty.");
        }
        if (phone == null || phone.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone cannot be null or empty.");
        }
        if (registrationDate == null) {
            throw new IllegalArgumentException("Registration date cannot be null.");
        }
        if (membershipType == null || membershipType.trim().isEmpty()) {
            throw new IllegalArgumentException("Membership type cannot be null or empty.");
        }
        int newID = 1;
        List<Member> allMembers = memberRepository.getAll();
        for (Member member : allMembers) {
            if (member.getId() >= newID) {
                newID = member.getId() + 1;
            }
        }
        Member newMember = new Member(name, mail, phone, registrationDate, membershipType, fitnessClasses);
        newMember.setId(newID);
        memberRepository.create(newMember);
    }

    // Update an existing member
    public void updateMember(int id, String name, String mail, String phone, LocalDate registrationDate, String membershipType, List<FitnessClass> fitnessClasses) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        if (mail == null || mail.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty.");
        }
        if (phone == null || phone.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone cannot be null or empty.");
        }
        if (registrationDate == null) {
            throw new IllegalArgumentException("Registration date cannot be null.");
        }
        if (membershipType == null || membershipType.trim().isEmpty()) {
            throw new IllegalArgumentException("Membership type cannot be null or empty.");
        }
        Member existingMember = getMember(id);
        existingMember.setName(name);
        existingMember.setMail(mail);
        existingMember.setPhone(phone);
        existingMember.setRegistrationDate(registrationDate);
        existingMember.setMembershipType(membershipType);
        existingMember.setFitnessClasses(fitnessClasses);
        memberRepository.update(existingMember);
    }

    // Delete a member by ID
    public void deleteMember(int id) {
        Member existingMember = getMember(id);
        if (existingMember == null) {
            throw new IllegalArgumentException("Member with ID " + id + " does not exist.");
        }
        memberRepository.delete(id);
    }

    // Get all members
    public List<Member> getAllMembers() {
        List<Member> members = memberRepository.getAll();
        if (members.isEmpty()) {
            throw new IllegalStateException("No members available.");
        }
        return members;
    }

    // ----- Membership -----

    // Retrieve a membership by ID
    public Membership getMembership(int id) {
        Membership membership = membershipRepository.read(id);
        if (membership == null) {
            throw new IllegalArgumentException("Membership with ID " + id + " does not exist.");
        }
        return membership;
    }

    // Add a new membership
    public void addMembership(String type, ArrayList<Member> members, float price) {
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Membership type cannot be null or empty.");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero.");
        }
        int newID = 1;
        List<Membership> allMemberships = membershipRepository.getAll();
        for (Membership membership : allMemberships) {
            if (membership.getId() >= newID) {
                newID = membership.getId() + 1;
            }
        }
        Membership newMembership = new Membership(type, members, price);
        newMembership.setId(newID);
        membershipRepository.create(newMembership);
    }

    // Update an existing membership
    public void updateMembership(int id, String type, ArrayList<Member> members, float price) {
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Membership type cannot be null or empty.");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero.");
        }
        Membership existingMembership = getMembership(id);
        existingMembership.setType(type);
        existingMembership.setMembers(members);
        existingMembership.setPrice(price);
        membershipRepository.update(existingMembership);
    }

    // Delete a membership by ID
    public void deleteMembership(int id) {
        Membership existingMembership = getMembership(id);
        if (existingMembership == null) {
            throw new IllegalArgumentException("Membership with ID " + id + " does not exist.");
        }
        membershipRepository.delete(id);
    }

    // Get all memberships
    public List<Membership> getAllMemberships() {
        List<Membership> memberships = membershipRepository.getAll();
        if (memberships.isEmpty()) {
            throw new IllegalStateException("No memberships available.");
        }
        return memberships;
    }

    // ----- Reservation -----

    // Retrieve a reservation by ID
    public Reservation getReservation(int id) {
        Reservation reservation = reservationRepository.read(id);
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation with ID " + id + " does not exist.");
        }
        return reservation;
    }

    // Add a new reservation
    public void addReservation(Member member, FitnessClass fitnessClass, LocalDateTime reservationDate) {
        if (member == null) {
            throw new IllegalArgumentException("Member cannot be null.");
        }
        if (fitnessClass == null) {
            throw new IllegalArgumentException("Fitness class cannot be null.");
        }
        if (reservationDate == null || reservationDate.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Reservation date must be a future date and cannot be null.");
        }
        int newID = 1;
        List<Reservation> allReservations = reservationRepository.getAll();
        for (Reservation reservation : allReservations) {
            if (reservation.getId() >= newID) {
                newID = reservation.getId() + 1;
            }
        }
        Reservation newReservation = new Reservation(member, fitnessClass, reservationDate);
        newReservation.setId(newID);
        reservationRepository.create(newReservation);
    }

    // Update an existing reservation
    public void updateReservation(int id, Member member, FitnessClass fitnessClass, LocalDateTime reservationDate) {
        if (member == null) {
            throw new IllegalArgumentException("Member cannot be null.");
        }
        if (fitnessClass == null) {
            throw new IllegalArgumentException("Fitness class cannot be null.");
        }
        if (reservationDate == null || reservationDate.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Reservation date must be a future date and cannot be null.");
        }
        Reservation existingReservation = getReservation(id);
        existingReservation.setMember(member);
        existingReservation.setFitnessClass(fitnessClass);
        existingReservation.setReservationDate(reservationDate);
        reservationRepository.update(existingReservation);
    }

    // Delete a reservation by ID
    public void deleteReservation(int id) {
        Reservation existingReservation = getReservation(id);
        if (existingReservation == null) {
            throw new IllegalArgumentException("Reservation with ID " + id + " does not exist.");
        }
        reservationRepository.delete(id);
    }

    // Get all reservations
    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = reservationRepository.getAll();
        if (reservations.isEmpty()) {
            throw new IllegalStateException("No reservations available.");
        }
        return reservations;
    }

    // ----- Room -----

    // Retrieve a room by ID
    public Room getRoom(int id) {
        Room room = roomRepository.read(id);
        if (room == null) {
            throw new IllegalArgumentException("Room with ID " + id + " does not exist.");
        }
        return room;
    }

    // Add a new room
    public void addRoom(String name, int maxCapacity, Location location) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Room name cannot be null or empty.");
        }
        if (maxCapacity <= 0) {
            throw new IllegalArgumentException("Max capacity must be greater than zero.");
        }
        if (location == null) {
            throw new IllegalArgumentException("Location cannot be null.");
        }
        int newID = 1;
        List<Room> allRooms = roomRepository.getAll();
        for (Room room : allRooms) {
            if (room.getId() >= newID) {
                newID = room.getId() + 1;
            }
        }
        Room newRoom = new Room(name, maxCapacity, location);
        newRoom.setId(newID);
        roomRepository.create(newRoom);
    }

    // Update an existing room
    public void updateRoom(int id, String name, int maxCapacity, Location location) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Room name cannot be null or empty.");
        }
        if (maxCapacity <= 0) {
            throw new IllegalArgumentException("Max capacity must be greater than zero.");
        }
        if (location == null) {
            throw new IllegalArgumentException("Location cannot be null.");
        }
        Room existingRoom = getRoom(id);
        existingRoom.setName(name);
        existingRoom.setMaxCapacity(maxCapacity);
        existingRoom.setLocation(location);
        roomRepository.update(existingRoom);
    }

    // Delete a room by ID
    public void deleteRoom(int id) {
        Room existingRoom = getRoom(id);
        if (existingRoom == null) {
            throw new IllegalArgumentException("Room with ID " + id + " does not exist.");
        }
        roomRepository.delete(id);
    }

    // Get all rooms
    public List<Room> getAllRooms() {
        List<Room> rooms = roomRepository.getAll();
        if (rooms.isEmpty()) {
            throw new IllegalStateException("No rooms available.");
        }
        return rooms;
    }

    // ----- Trainer -----

    // Retrieve a trainer by ID
    public Trainer getTrainer(int id) {
        Trainer trainer = trainerRepository.read(id);
        if (trainer == null) {
            throw new IllegalArgumentException("Trainer with ID " + id + " does not exist.");
        }
        return trainer;
    }

    // Add a new trainer
    public void addTrainer(String name, String mail, String phone, String specialisation) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Trainer name cannot be null or empty.");
        }
        if (mail == null || mail.trim().isEmpty()) {
            throw new IllegalArgumentException("Trainer mail cannot be null or empty.");
        }
        if (phone == null || phone.trim().isEmpty()) {
            throw new IllegalArgumentException("Trainer phone cannot be null or empty.");
        }
        if (specialisation == null || specialisation.trim().isEmpty()) {
            throw new IllegalArgumentException("Trainer specialisation cannot be null or empty.");
        }
        int newID = 1;
        List<Trainer> allTrainers = trainerRepository.getAll();
        for (Trainer trainer : allTrainers) {
            if (trainer.getId() >= newID) {
                newID = trainer.getId() + 1;
            }
        }
        Trainer newTrainer = new Trainer(name, mail, phone, specialisation);
        newTrainer.setId(newID);
        trainerRepository.create(newTrainer);
    }

    // Update an existing trainer
    public void updateTrainer(int id, String name, String mail, String phone, String specialisation) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Trainer name cannot be null or empty.");
        }
        if (mail == null || mail.trim().isEmpty()) {
            throw new IllegalArgumentException("Trainer mail cannot be null or empty.");
        }
        if (phone == null || phone.trim().isEmpty()) {
            throw new IllegalArgumentException("Trainer phone cannot be null or empty.");
        }
        if (specialisation == null || specialisation.trim().isEmpty()) {
            throw new IllegalArgumentException("Trainer specialisation cannot be null or empty.");
        }
        Trainer existingTrainer = getTrainer(id);
        existingTrainer.setName(name);
        existingTrainer.setMail(mail);
        existingTrainer.setPhone(phone);
        existingTrainer.setSpecialisation(specialisation);
        trainerRepository.update(existingTrainer);
    }

    // Delete a trainer by ID
    public void deleteTrainer(int id) {
        Trainer existingTrainer = getTrainer(id);
        if (existingTrainer == null) {
            throw new IllegalArgumentException("Trainer with ID " + id + " does not exist.");
        }
        trainerRepository.delete(id);
    }

    // Get all trainers
    public List<Trainer> getAllTrainers() {
        List<Trainer> trainers = trainerRepository.getAll();
        if (trainers.isEmpty()) {
            throw new IllegalStateException("No trainers available.");
        }
        return trainers;
    }

    // --------------------------------------------------------------------------------------

    // Method to get upcoming classes for a trainer
    public List<FitnessClass> getTrainerUpcomingClasses(int trainerId) {
        List<FitnessClass> upcomingClasses = new ArrayList<>();
        List<FitnessClass> allClasses = fitnessClassRepository.getAll();
        // Loop through all classes to find the ones for the given trainer and not yet started
        for (FitnessClass fitnessClass : allClasses) {
            // Check if the class is managed by the trainer and hasn't started yet
            if(fitnessClass.getTrainer().getId() == trainerId);{
                // Make sure the start time is after the current time
                if (fitnessClass.getStartTime().isAfter(LocalDateTime.now())) {
                    upcomingClasses.add(fitnessClass);
                }
            }
        }
        if (upcomingClasses != null) {return upcomingClasses;}
        else throw new IllegalArgumentException("No existing upcoming classes at the moment.");
    }

    // Method to get all upcoming classes
    public List<FitnessClass> getAllUpcomingClasses() {
        List<FitnessClass> upcomingClasses = new ArrayList<>();
        List<FitnessClass> allClasses = fitnessClassRepository.getAll();
        // Loop through all classes to find the ones not yet started
        for (FitnessClass fitnessClass : allClasses) {
                // Make sure the start time is after the current time
                if (fitnessClass.getStartTime().isAfter(LocalDateTime.now())) {
                    upcomingClasses.add(fitnessClass);
                }
        }
        if (upcomingClasses != null) {return upcomingClasses;}
        else throw new IllegalArgumentException("No existing upcoming classes at the moment.");
    }

    // Helper method to check for schedule collision
    public void checkForScheduleCollision(FitnessClass fitnessClass){
        List<FitnessClass> existingClasses = fitnessClassRepository.getAll();
        for (FitnessClass existingClass : existingClasses) {
            if (existingClass.getRoom().getId() == fitnessClass.getRoom().getId() &&
                    (fitnessClass.getStartTime().isBefore(existingClass.getEndTime()) &&
                            fitnessClass.getEndTime().isAfter(existingClass.getStartTime()))) {
                throw new IllegalStateException("The room is already booked for this time slot.");
            }
        }
    }

    // Method to schedule a new fitness class
    public void scheduleNewClass(String className, LocalDateTime startTime, LocalDateTime endTime, int trainerId,
                                 int roomId, int participantsCount, int locationId,List<Equipment> equipment) {
        // Validate room
        Room room = roomRepository.read(roomId);
        if (room == null) {
            throw new IllegalArgumentException("Room with ID " + roomId + " does not exist.");
        }
        // Validate location
        Location location = locationRepository.read(locationId);
        if (location == null) {
            throw new IllegalArgumentException("Location with ID " + locationId + " does not exist.");
        }
        // Validate start and end times
        if (startTime == null || endTime == null) {
            throw new IllegalArgumentException("Start time and end time cannot be null.");
        }
        if (endTime.isBefore(startTime)) {
            throw new IllegalArgumentException("End time cannot be before start time.");
        }
        Trainer trainer = trainerRepository.read(trainerId);
        List<Feedback> feedback = new ArrayList<>();
        List<Member> members = new ArrayList<>();
        // Create a new FitnessClass object
        FitnessClass newFitnessClass = new FitnessClass(className, startTime, endTime, trainer, room, participantsCount,
                location, feedback, members, equipment);
        newFitnessClass.setId(getClassNextId());
        // Check for schedule collision
        checkForScheduleCollision(newFitnessClass);
        // Persist the new class in the repository
        fitnessClassRepository.create(newFitnessClass);
    }

    // Helper method to find fitness class next id
    public int getClassNextId() {
        List<FitnessClass> fitnessClasses = fitnessClassRepository.getAll();
        return fitnessClasses.size() + 1;
    }

    // Method to view a schedule
    public void viewSchedule() {
        List<FitnessClass> upcomingClasses = getAllUpcomingClasses();
        for(FitnessClass fitnessClass : upcomingClasses) {
            fitnessClass.toStringLessInfo();
        }
    }

    // Helper method to determine if two classes are similar based on trainer and equipment
    private boolean findSimilarClasses(FitnessClass fitnessClass, FitnessClass targetClass) {
        // Check if the trainers are the same
        if (!fitnessClass.getTrainer().equals(targetClass.getTrainer())) {
            return false;
        }
        // Check if there is any common equipment
        Set<Equipment> targetEquipmentSet = new HashSet<>(targetClass.getEquipment());
        for (Equipment equipment : fitnessClass.getEquipment()) {
            if (targetEquipmentSet.contains(equipment)) {
                return true; // At least one common equipment found
            }
        }
        return false; // No common equipment found
    }

    // Method to get similar fitness classes based on both trainer and equipment
    public List<FitnessClass> getSimilarClasses(FitnessClass targetClass) {
        List<FitnessClass> allClasses = fitnessClassRepository.getAll();
        List<FitnessClass> similarClasses = new ArrayList<>();
        // Iterate over all fitness classes to find similar ones
        for (FitnessClass fitnessClass : allClasses) {
            // Skip the target class itself
            if (fitnessClass.getId() == targetClass.getId()) {
                continue;
            }
            // Use helper method to check if this class is similar
            if (findSimilarClasses(fitnessClass, targetClass)) {
                similarClasses.add(fitnessClass);
            }
        }
        if (similarClasses != null) {return similarClasses;}
        else throw new IllegalArgumentException("No similar classes found.");
    }

    // Method to register a member to a class
    public void registerToClass(int memberId, int classId) {
        // Retrieve the fitness class by ID
        FitnessClass fitnessClass = fitnessClassRepository.read(classId);
        if (fitnessClass == null) {
            throw new IllegalArgumentException("Fitness class with ID " + classId + " does not exist.");
        }
        Member member = memberRepository.read(memberId);
        // Check if the member is already registered
        if (fitnessClass.getMembers().contains(member)) {
            throw new IllegalStateException("Member is already registered for this class.");
        }
        // Check if the class has available slots
        if (fitnessClass.getParticipantsCount() >= fitnessClass.getRoom().getMaxCapacity()) {
            throw new IllegalStateException("The class is already full.");
        }
        // Register the member by adding them to the class's member list
        fitnessClass.getMembers().add(member);
        // Update the participant count
        fitnessClass.setParticipantsCount(fitnessClass.getParticipantsCount() + 1);
        // Persist the changes to the repository
        fitnessClassRepository.update(fitnessClass);
    }

    // Method to drop a member from a class
    public void dropClass(int memberId, int classId) {
        // Retrieve the fitness class by ID
        FitnessClass fitnessClass = fitnessClassRepository.read(classId);
        if (fitnessClass == null) {
            throw new IllegalArgumentException("Fitness class with ID " + classId + " does not exist.");
        }
        Member member = memberRepository.read(memberId);
        // Check if the member is actually registered in the class
        if (!fitnessClass.getMembers().contains(member)) {
            throw new IllegalStateException("Member is not registered for this class.");
        }
        // Remove the member from the class's member list
        fitnessClass.getMembers().remove(member);
        // Update the participant count
        fitnessClass.setParticipantsCount(fitnessClass.getParticipantsCount() - 1);
        // Persist the changes to the repository
        fitnessClassRepository.update(fitnessClass);
    }

    //get Feedback of a fitnessclass
    public List<Feedback> getClassFeedback (int classId) {
        //retrieve the fitness class by its Id
        FitnessClass fitnessClass = fitnessClassRepository.read(classId);
        if (fitnessClass == null) {
            throw new IllegalArgumentException("Fitness class with ID" + classId + " does not exist.");
        }
        //retrieve the feedback list from the fitness class
        List<Feedback> feedbackList = fitnessClass.getFeedback();
        if (feedbackList == null || feedbackList.isEmpty()) {
            throw new IllegalArgumentException("No feedback available for this fitness class");
        }
        return feedbackList;
    }

    // Method to get all classes taught by a trainer
    public ArrayList<FitnessClass> getAllClassesByTrainer(int trainerId) {
        List<FitnessClass> classes = getAllFitnessClasses();
        return filterByTrainerID(trainerId, classes);
    }

    // Helper method to filter classes by trainer id
    public ArrayList<FitnessClass> filterByTrainerID(int id, List<FitnessClass> classList) {
        ArrayList<FitnessClass> filteredList = new ArrayList<>();
        for (FitnessClass fitnessClass : classList) {
            if(fitnessClass.getTrainer().getId() == id) {
                filteredList.add(fitnessClass);
            }
        }
        return filteredList;
    }
}
