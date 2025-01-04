package service;
import model.*;
import repository.IRepository;
import Helpers.*;

import java.time.LocalDateTime;
import java.util.*;

/**
 * The FitnessService class provides business logic and services for managing fitness-related operations.
 * It interacts with multiple repositories to handle entities such as members, trainers, reservations, and more.
 * This class acts as a service layer between the repositories and the application logic.
 */
public class FitnessService {

    private final IRepository<Equipment> equipmentRepository;
    private final IRepository<Feedback> feedbackRepository;
    private final IRepository<FitnessClass> fitnessClassRepository;
    private final IRepository<Location> locationRepository;
    private final IRepository<Member> memberRepository;
    private final IRepository<Membership> membershipRepository;
    private final IRepository<Room> roomRepository;
    private final IRepository<Trainer> trainerRepository;

    /**
     * Constructs a FitnessService instance with dependencies for all required repositories.
     * @param equipmentRepository     The repository managing Equipment entities.
     * @param feedbackRepository      The repository managing Feedback entities.
     * @param fitnessClassRepository  The repository managing FitnessClass entities.
     * @param locationRepository      The repository managing Location entities.
     * @param memberRepository        The repository managing Member entities.
     * @param membershipRepository    The repository managing Membership entities.
     * @param roomRepository          The repository managing Room entities.
     * @param trainerRepository       The repository managing Trainer entities.
     */
    public FitnessService(IRepository<Equipment> equipmentRepository, IRepository<Feedback> feedbackRepository, IRepository<FitnessClass> fitnessClassRepository, IRepository<Location> locationRepository, IRepository<Member> memberRepository, IRepository<Membership> membershipRepository, IRepository<Room> roomRepository, IRepository<Trainer> trainerRepository) {
        this.equipmentRepository = equipmentRepository;
        this.feedbackRepository = feedbackRepository;
        this.fitnessClassRepository = fitnessClassRepository;
        this.locationRepository = locationRepository;
        this.memberRepository = memberRepository;
        this.membershipRepository = membershipRepository;
        this.roomRepository = roomRepository;
        this.trainerRepository = trainerRepository;
    }

    /**
     * Retrieves equipment by its unique ID.
     * @param id The unique identifier of the equipment.
     * @return The Equipment object with the given ID.
     * @throws IllegalArgumentException if no equipment with the given ID exists.
     */
    public Equipment getEquipment(int id) {
        Equipment equipment = equipmentRepository.read(id);
        if (equipment == null) {
            throw new IllegalArgumentException("Equipment with ID " + id + " does not exist.");
        }
        return equipment;
    }

    /**
     * Adds a new piece of equipment to the repository.
     * @param equipment the equipment object to add
     * @throws IllegalArgumentException if the name is null/empty or if the quantity is less than or equal to zero.
     */
    public void addEquipment(Equipment equipment) {
        if (equipment.getName() == null || equipment.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Equipment name cannot be null or empty.");
        }
        if (equipment.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        equipmentRepository.create(equipment);
    }

    /**
     * Updates an existing piece of equipment in the repository.
     * @param id            The ID of the equipment to update.
     * @param name          The new name for the equipment. Must not be null or empty.
     * @param quantity      The new quantity for the equipment. Must be greater than zero.
     * @param fitnessClasses A list of FitnessClass objects associated with the equipment. Can be empty or null.
     * @throws IllegalArgumentException if the name is null/empty, the quantity is less than or equal to zero,
     *                                  or if the equipment with the given ID does not exist.
     */
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

    /**
     * Deletes equipment from the repository by its unique ID.
     * @param id The unique identifier of the equipment to delete.
     * @throws IllegalArgumentException if no equipment with the given ID exists.
     */
    public void deleteEquipment(int id) {
        Equipment existingEquipment = getEquipment(id);
        if (existingEquipment == null) {
            throw new IllegalArgumentException("Equipment with ID " + id + " does not exist.");
        }
        equipmentRepository.delete(id);
    }

    /**
     * Retrieves all pieces of equipment from the repository.
     * @return A list of all Equipment objects in the repository.
     * @throws IllegalStateException if no equipment is available in the repository.
     */
    public List<Equipment> getAllEquipment() {
        List<Equipment> equipmentList = equipmentRepository.getAll();
        if (equipmentList.isEmpty()) {
            throw new IllegalStateException("No equipment available.");
        }
        return equipmentList;
    }

    /**
     * Retrieves feedback by its unique ID.
     * @param id The unique identifier of the feedback.
     * @return The Feedback object with the given ID.
     * @throws IllegalArgumentException if no feedback with the given ID exists.
     */
    public Feedback getFeedback(int id) {
        Feedback feedback = feedbackRepository.read(id);
        if (feedback == null) {
            throw new IllegalArgumentException("Feedback with ID " + id + " does not exist.");
        }
        return feedback;
    }

    /**
     * Adds new feedback to the repository.
     * @param feedback the feedback object to add
     * @throws IllegalArgumentException if any parameter is invalid (e.g., null values, out-of-range rating).
     */
    public void addFeedback(Feedback feedback) {
        if (feedback.getMember() == null) {
            throw new IllegalArgumentException("Member cannot be null.");
        }
        if (feedback.getFitnessClass() == null) {
            throw new IllegalArgumentException("Fitness class cannot be null.");
        }
        if (feedback.getRating() < 1 || feedback.getRating() > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }
        if (feedback.getComment() == null || feedback.getComment().trim().isEmpty()) {
            throw new IllegalArgumentException("Comment cannot be null or empty.");
        }
        feedbackRepository.create(feedback);
    }

    /**
     * Updates an existing feedback in the repository.
     * @param id            The ID of the feedback to update.
     * @param rating        The updated rating for the feedback. Must be between 1 and 5.
     * @param comment       The updated textual comment in the feedback. Must not be null or empty.
     * @throws IllegalArgumentException if any parameter is invalid or if the feedback with the given ID does not exist.
     */
    public void updateFeedback(int id, int rating, String comment) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }
        if (comment == null || comment.trim().isEmpty()) {
            throw new IllegalArgumentException("Comment cannot be null or empty.");
        }
        Feedback existingFeedback = getFeedback(id);
        existingFeedback.setRating(rating);
        existingFeedback.setComment(comment);
        feedbackRepository.update(existingFeedback);
    }

    /**
     * Deletes feedback from the repository by its unique ID.
     * @param id The unique identifier of the feedback to delete.
     * @throws IllegalArgumentException if no feedback with the given ID exists.
     */
    public void deleteFeedback(int id) {
        Feedback existingFeedback = getFeedback(id);
        if (existingFeedback == null) {
            throw new IllegalArgumentException("Feedback with ID " + id + " does not exist.");
        }
        feedbackRepository.delete(id);
    }

    /**
     * Retrieves all feedback from the repository.
     * @return A list of all Feedback objects in the repository.
     * @throws IllegalStateException if no feedback is available in the repository.
     */
    public List<Feedback> getAllFeedback() {
        List<Feedback> feedbackList = feedbackRepository.getAll();
        if (feedbackList.isEmpty()) {
            throw new IllegalStateException("No feedback available.");
        }
        return feedbackList;
    }

    /**
     * Retrieves a fitness class by its unique ID.
     * @param id The unique identifier of the fitness class.
     * @return The FitnessClass object with the given ID.
     * @throws IllegalArgumentException if no fitness class with the given ID exists.
     */
    public FitnessClass getFitnessClass(int id) {
        FitnessClass fitnessClass = fitnessClassRepository.read(id);
        if (fitnessClass == null) {
            throw new IllegalArgumentException("Fitness class with ID " + id + " does not exist.");
        }
        return fitnessClass;
    }

    /**
     * Adds a new fitness class to the repository.
     * @param fitnessClass the fitness class object to add
     * @throws IllegalArgumentException if any parameter is invalid.
     */
    public void addFitnessClass(FitnessClass fitnessClass) {
        if (fitnessClass.getName() == null || fitnessClass.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Class name cannot be null or empty.");
        }
        if (fitnessClass.getStartTime().isAfter(fitnessClass.getEndTime())) {
            throw new IllegalArgumentException("Start time can't be after end time.");
        }
        if (fitnessClass.getTrainer() == null) {
            throw new IllegalArgumentException("Trainer cannot be null.");
        }
        if (fitnessClass.getRoom() == null) {
            throw new IllegalArgumentException("Room cannot be null.");
        }
        if (fitnessClass.getParticipantsCount() < 0) {
            throw new IllegalArgumentException("Participants count cannot be negative.");
        }
        fitnessClassRepository.create(fitnessClass);
    }

    /**
     * Updates an existing fitness class in the repository.
     * @param id                The unique ID of the fitness class to update.
     * @param name              The updated name of the fitness class. Must not be null or empty.
     * @param startTime         The updated start time of the class. Must be before endTime.
     * @param endTime           The updated end time of the class. Must be after startTime.
     * @param trainer           The updated trainer leading the class. Must not be null.
     * @param room              The updated room where the class will be held. Must not be null.
     * @param participantsCount The updated number of participants in the class. Must be non-negative.
     * @param location          The updated location of the class.
     * @param feedback          The updated list of feedback associated with the class.
     * @param members           The updated list of members attending the class.
     * @param equipment         The updated list of equipment used in the class.
     * @throws IllegalArgumentException if any parameter is invalid or if the class with the given ID does not exist.
     */
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

    /**
     * Deletes a fitness class from the repository by its unique ID.
     * @param id The unique identifier of the fitness class to delete.
     * @throws IllegalArgumentException if no fitness class with the given ID exists.
     */
    public void deleteFitnessClass(int id) {
        FitnessClass existingFitnessClass = getFitnessClass(id);
        if (existingFitnessClass == null) {
            throw new IllegalArgumentException("Fitness class with ID " + id + " does not exist.");
        }
        fitnessClassRepository.delete(id);
    }

    /**
     * Retrieves all fitness classes from the repository.
     * @return A list of all FitnessClass objects in the repository.
     * @throws IllegalStateException if no fitness classes are available.
     */
    public List<FitnessClass> getAllFitnessClasses() {
        List<FitnessClass> fitnessClasses = fitnessClassRepository.getAll();
        if (fitnessClasses.isEmpty()) {
            throw new IllegalStateException("No fitness classes available.");
        }
        return fitnessClasses;
    }

    /**
     * Retrieves a location by its unique ID.
     * @param id The unique identifier of the location.
     * @return The Location object with the given ID.
     * @throws IllegalArgumentException if no location with the given ID exists.
     */
    public Location getLocation(int id) {
        Location location = locationRepository.read(id);
        if (location == null) {
            throw new IllegalArgumentException("Location with ID " + id + " does not exist.");
        }
        return location;
    }

    /**
     * Adds a new location to the repository.
     * @param location the location object to add
     * @throws IllegalArgumentException if either the name or address is invalid.
     */
    public void addLocation(Location location) {
        if (location.getName() == null || location.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Location name cannot be null or empty.");
        }
        if (location.getAddress() == null || location.getAddress().trim().isEmpty()) {
            throw new IllegalArgumentException("Location address cannot be null or empty.");
        }
        locationRepository.create(location);
    }

    /**
     * Updates an existing location in the repository.
     * @param id      The unique ID of the location to update.
     * @param name    The updated name of the location. Must not be null or empty.
     * @param address The updated address of the location. Must not be null or empty.
     * @throws IllegalArgumentException if either the name or address is invalid or if the location with the given ID does not exist.
     */
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

    /**
     * Deletes a location from the repository by its unique ID.
     * @param id The unique identifier of the location to delete.
     * @throws IllegalArgumentException if no location with the given ID exists.
     */
    public void deleteLocation(int id) {
        Location existingLocation = getLocation(id);
        if (existingLocation == null) {
            throw new IllegalArgumentException("Location with ID " + id + " does not exist.");
        }
        locationRepository.delete(id);
    }

    /**
     * Retrieves all locations from the repository.
     * @return A list of all Location objects in the repository.
     * @throws IllegalStateException if no locations are available.
     */
    public List<Location> getAllLocations() {
        List<Location> locations = locationRepository.getAll();
        if (locations.isEmpty()) {
            throw new IllegalStateException("No locations available.");
        }
        return locations;
    }

    /**
     * Retrieves a member by its unique ID.
     * @param id The unique identifier of the member.
     * @return The Member object with the given ID.
     * @throws IllegalArgumentException if no member with the given ID exists.
     */
    public Member getMember(int id) {
        Member member = memberRepository.read(id);
        if (member == null) {
            throw new IllegalArgumentException("Member with ID " + id + " does not exist.");
        }
        return member;
    }

    public void addMember(Member member) {
        if (member != null) {
            memberRepository.create(member);
        }
    }

    /**
     * Updates an existing member's details in the repository.
     * @param id               The unique ID of the member to update.
     * @param name             The updated name of the member. Must not be null or empty.
     * @param password         The updated password of the member. Must not be null or empty.
     * @throws IllegalArgumentException if any of the parameters are invalid or if the member with the given ID does not exist.
     */
    public void updateMember(int id, String name, String password, Membership membership) {
        if (password == null) {
            throw new IllegalArgumentException("Password cannot be null or empty.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        Member existingMember = getMember(id);
        existingMember.setName(name);
        existingMember.setPassword(password);
        existingMember.setMembership(membership);
        memberRepository.update(existingMember);
    }

    /**
     * Deletes a member from the repository by its unique ID.
     * @param id The unique identifier of the member to delete.
     * @throws IllegalArgumentException if no member with the given ID exists.
     */
    public void deleteMember(int id) {
        Member existingMember = getMember(id);
        if (existingMember == null) {
            throw new IllegalArgumentException("Member with ID " + id + " does not exist.");
        }
        memberRepository.delete(id);
    }

    /**
     * Retrieves all members from the repository.
     * @return A list of all Member objects in the repository.
     * @throws IllegalStateException if no members are available in the repository.
     */
    public List<Member> getAllMembers() {
        List<Member> members = memberRepository.getAll();
        if (members.isEmpty()) {
            throw new IllegalStateException("No members available.");
        }
        return members;
    }

    /**
     * Retrieves a membership by its unique ID.
     * @param id The unique identifier of the membership.
     * @return The Membership object with the given ID.
     * @throws IllegalArgumentException if no membership with the given ID exists.
     */
    public Membership getMembership(int id) {
        Membership membership = membershipRepository.read(id);
        if (membership == null) {
            throw new IllegalArgumentException("Membership with ID " + id + " does not exist.");
        }
        return membership;
    }

    /**
     * Adds a new membership to the repository.
     * @param membership the membership object to add
     * @throws IllegalArgumentException if any of the parameters are invalid.
     */
    public void addMembership(Membership membership) {
        if (membership.getType() == null || membership.getType().trim().isEmpty()) {
            throw new IllegalArgumentException("Membership type cannot be null or empty.");
        }
        if (membership.getPrice() <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero.");
        }
        membershipRepository.create(membership);
    }

    /**
     * Updates an existing membership's details in the repository.
     * @param id      The unique ID of the membership to update.
     * @param type    The updated type of the membership. Must not be null or empty.
     * @param price   The updated price of the membership. Must be greater than zero.
     * @throws IllegalArgumentException if any of the parameters are invalid or if the membership with the given ID does not exist.
     */
    public void updateMembership(int id, String type, float price) {
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Membership type cannot be null or empty.");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero.");
        }
        Membership existingMembership = getMembership(id);
        existingMembership.setType(type);
        existingMembership.setPrice(price);
        membershipRepository.update(existingMembership);
    }
    /**
     * Deletes a membership from the repository by its unique ID.
     * @param id The unique identifier of the membership to delete.
     * @throws IllegalArgumentException if no membership with the given ID exists.
     */
    public void deleteMembership(int id) {
        Membership existingMembership = getMembership(id);
        if (existingMembership == null) {
            throw new IllegalArgumentException("Membership with ID " + id + " does not exist.");
        }
        membershipRepository.delete(id);
    }

    /**
     * Retrieves all memberships from the repository.
     * @return A list of all Membership objects in the repository.
     * @throws IllegalStateException if no memberships are available in the repository.
     */
    public List<Membership> getAllMemberships() {
        List<Membership> memberships = membershipRepository.getAll();
        if (memberships.isEmpty()) {
            throw new IllegalStateException("No memberships available.");
        }
        return memberships;
    }

    /**
     * Retrieves a room by its unique ID.
     * @param id The unique identifier of the room.
     * @return The Room object with the given ID.
     * @throws IllegalArgumentException if no room with the given ID exists.
     */
    public Room getRoom(int id) {
        Room room = roomRepository.read(id);
        if (room == null) {
            throw new IllegalArgumentException("Room with ID " + id + " does not exist.");
        }
        return room;
    }

    /**
     * Adds a new room to the repository.
     * @param room the room object to add
     * @throws IllegalArgumentException if any of the parameters are invalid, such as null values for name or location,
     *                                  or if the maxCapacity is less than or equal to zero.
     */
    public void addRoom(Room room) {
        if (room.getName() == null || room.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Room name cannot be null or empty.");
        }
        if (room.getMaxCapacity() <= 0) {
            throw new IllegalArgumentException("Max capacity must be greater than zero.");
        }
        if (room.getLocation() == null) {
            throw new IllegalArgumentException("Location cannot be null.");
        }
        roomRepository.create(room);
    }

    /**
     * Updates an existing room's details in the repository.
     * @param id          The unique ID of the room to update.
     * @param name        The updated name of the room. Must not be null or empty.
     * @param maxCapacity The updated maximum capacity of the room. Must be greater than zero.
     * @param location    The updated location of the room. Must not be null.
     * @throws IllegalArgumentException if any of the parameters are invalid, or if the room with the given ID does not exist.
     */
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

    /**
     * Deletes a room from the repository by its unique ID.
     * @param id The unique identifier of the room to delete.
     * @throws IllegalArgumentException if no room with the given ID exists.
     */
    public void deleteRoom(int id) {
        Room existingRoom = getRoom(id);
        if (existingRoom == null) {
            throw new IllegalArgumentException("Room with ID " + id + " does not exist.");
        }
        roomRepository.delete(id);
    }

    /**
     * Retrieves all rooms from the repository.
     * @return A list of all Room objects in the repository.
     * @throws IllegalStateException if no rooms are available in the repository.
     */
    public List<Room> getAllRooms() {
        List<Room> rooms = roomRepository.getAll();
        if (rooms.isEmpty()) {
            throw new IllegalStateException("No rooms available.");
        }
        return rooms;
    }

    /**
     * Retrieves a trainer by their unique ID.
     * @param id The unique identifier of the trainer.
     * @return The Trainer object with the given ID.
     * @throws IllegalArgumentException if no trainer with the given ID exists.
     */
    public Trainer getTrainer(int id) {
        Trainer trainer = trainerRepository.read(id);
        if (trainer == null) {
            throw new IllegalArgumentException("Trainer with ID " + id + " does not exist.");
        }
        return trainer;
    }

    /**
     * Adds a new trainer to the repository.
     * @param trainer the trainer object to add
     * @throws IllegalArgumentException if any of the parameters are invalid, such as null values for name, mail, phone, or specialisation.
     */
    public void addTrainer(Trainer trainer) {
        if (trainer.getName() == null || trainer.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Trainer name cannot be null or empty.");
        }
        if (trainer.getPassword() == null) {
            throw new IllegalArgumentException("Trainer password cannot be null or empty.");
        }
        if (trainer.getSpecialisation() == null || trainer.getSpecialisation().trim().isEmpty()) {
            throw new IllegalArgumentException("Trainer specialisation cannot be null or empty.");
        }
        trainerRepository.create(trainer);
    }

    /**
     * Updates an existing trainer's details in the repository.
     * @param id             The unique ID of the trainer to update.
     * @param name           The updated name of the trainer. Must not be null or empty.
     * @param password       The updated password of the trainer. Must not be null or empty.
     * @param specialisation The updated specialisation of the trainer. Must not be null or empty.
     * @throws IllegalArgumentException if any of the parameters are invalid, or if the trainer with the given ID does not exist.
     */
    public void updateTrainer(int id, String name, String password, String specialisation) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Trainer name cannot be null or empty.");
        }
        if (password == null) {
            throw new IllegalArgumentException("Trainer mail cannot be null or empty.");
        }
        Trainer existingTrainer = getTrainer(id);
        existingTrainer.setName(name);
        existingTrainer.setPassword(password);
        existingTrainer.setSpecialisation(specialisation);
        trainerRepository.update(existingTrainer);
    }

    /**
     * Deletes a trainer from the repository by their unique ID.
     * @param id The unique identifier of the trainer to delete.
     * @throws IllegalArgumentException if no trainer with the given ID exists.
     */
    public void deleteTrainer(int id) {
        Trainer existingTrainer = getTrainer(id);
        if (existingTrainer == null) {
            throw new IllegalArgumentException("Trainer with ID " + id + " does not exist.");
        }
        trainerRepository.delete(id);
    }

    /**
     * Retrieves all trainers from the repository.
     * @return A list of all Trainer objects in the repository.
     * @throws IllegalStateException if no trainers are available in the repository.
     */
    public List<Trainer> getAllTrainers() {
        List<Trainer> trainers = trainerRepository.getAll();
        if (trainers.isEmpty()) {
            throw new IllegalStateException("No trainers available.");
        }
        return trainers;
    }

    /**
     * Retrieves the upcoming fitness classes for a specific trainer.
     * Only classes that have not yet started and are managed by the given trainer are included.
     * @param trainerId The unique ID of the trainer for whom upcoming classes are to be retrieved.
     * @return A list of upcoming FitnessClass objects managed by the given trainer.
     * @throws IllegalArgumentException if no upcoming classes exist for the given trainer.
     */
    public List<FitnessClass> getTrainerUpcomingClasses(int trainerId) {
        List<FitnessClass> upcomingClasses = new ArrayList<>();
        List<FitnessClass> allClasses = fitnessClassRepository.getAll();
        for (FitnessClass fitnessClass : allClasses) {
            if(fitnessClass.getTrainer().getId() == trainerId);{
                if (fitnessClass.getStartTime().isAfter(LocalDateTime.now())) {
                    upcomingClasses.add(fitnessClass);
                }
            }
        }
        if (upcomingClasses != null) {return upcomingClasses;}
        else throw new IllegalArgumentException("No existing upcoming classes at the moment.");
    }

    /**
     * Retrieves all upcoming fitness classes
     * Only classes that have not yet started are included in the result.
     * @return A list of upcoming FitnessClass objects.
     * @throws IllegalArgumentException if no upcoming classes are available.
     */
    public List<FitnessClass> getAllUpcomingClasses() {
        List<FitnessClass> upcomingClasses = new ArrayList<>();
        List<FitnessClass> allClasses = fitnessClassRepository.getAll();
        for (FitnessClass fitnessClass : allClasses) {
            if (fitnessClass.getStartTime().isAfter(LocalDateTime.now())) {
                upcomingClasses.add(fitnessClass);
            }
        }
        if (upcomingClasses != null) {return upcomingClasses;}
        else throw new IllegalArgumentException("No existing upcoming classes at the moment.");
    }

    /**
     * Retrieves all upcoming fitness classes in which a member has not registered yet
     * Only classes that have not yet started are included in the result.
     * @return A list of upcoming FitnessClass objects.
     * @throws IllegalArgumentException if no upcoming classes are available.
     */
    public List<FitnessClass> getAllUpcomingClasses_MemberNotRegisteredYet(int memberId) {
        List<FitnessClass> upcomingClasses = getAllUpcomingClasses();
        Member member = getMember(memberId);
        List<FitnessClass> notRegisteredClasses = new ArrayList<>();
        for (FitnessClass fitnessClass : upcomingClasses) {
            if (!fitnessClass.getMembers().contains(member)) {
                notRegisteredClasses.add(fitnessClass);
            }
        }
        if (notRegisteredClasses != null) {return notRegisteredClasses;}
        else throw new IllegalArgumentException("No existing upcoming classes at the moment.");
    }

    /**
     * Checks for scheduling conflicts when adding a new fitness class to a room.
     * It compares the start and end times of the new class with the existing classes
     * scheduled in the same room to ensure there is no overlap.
     * @param fitnessClass The FitnessClass object to be checked for schedule conflicts.
     * @throws IllegalStateException if the room is already booked for the specified time slot.
     */
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

    /**
     * Schedules a new fitness class by validating the provided inputs and checking for schedule collisions.
     * If the class is valid and there are no conflicts, it creates and stores the new FitnessClass object
     * in the repository.
     * @param className The name of the new fitness class.
     * @param startTime The start time of the fitness class.
     * @param endTime The end time of the fitness class.
     * @param trainerId The ID of the trainer leading the class.
     * @param roomId The ID of the room where the class will be held.
     * @param participantsCount The maximum number of participants allowed in the class.
     * @param locationId The ID of the location where the room is situated.
     * @param equipment The list of equipment required for the class.
     * @throws IllegalArgumentException If any of the provided parameters are invalid, such as:
     *         - If the room or location does not exist.
     *         - If the start or end time is null or if the end time is before the start time.
     * @throws IllegalStateException If there is a schedule collision for the room at the given time.
     */
    public void scheduleNewClass(String className, LocalDateTime startTime, LocalDateTime endTime, int trainerId,
                                 int roomId, int participantsCount, int locationId,List<Equipment> equipment) {
        Room room = roomRepository.read(roomId);
        if (room == null) {
            throw new IllegalArgumentException("Room with ID " + roomId + " does not exist.");
        }
        Location location = locationRepository.read(locationId);
        if (location == null) {
            throw new IllegalArgumentException("Location with ID " + locationId + " does not exist.");
        }
        if (startTime == null || endTime == null) {
            throw new IllegalArgumentException("Start time and end time cannot be null.");
        }
        if (endTime.isBefore(startTime)) {
            throw new IllegalArgumentException("End time cannot be before start time.");
        }
        Trainer trainer = trainerRepository.read(trainerId);
        List<Feedback> feedback = new ArrayList<>();
        List<Member> members = new ArrayList<>();
        FitnessClass newFitnessClass = new FitnessClass(className, startTime, endTime, trainer, room, participantsCount,
                location, feedback, members, equipment);
        newFitnessClass.setId(HelperFunctions.randomId());
        checkForScheduleCollision(newFitnessClass);
        fitnessClassRepository.create(newFitnessClass);
    }

    /**
     * Helper method to check if two fitness classes are similar based on their trainer and equipment.
     * Two classes are considered similar if they share the same trainer and at least one common piece of equipment.
     * @param fitnessClass The first fitness class to compare.
     * @param targetClass The second fitness class to compare.
     * @return true if the classes are similar (same trainer and at least one common equipment), false otherwise.
     */
    private boolean findSimilarClasses(FitnessClass fitnessClass, FitnessClass targetClass) {
        if (fitnessClass == null || targetClass == null) {
            throw new IllegalArgumentException("Fitness classes must not be null");
        }
        if (!fitnessClass.getTrainer().equals(targetClass.getTrainer())) {
            return false;
        }
        Set<Equipment> targetEquipmentSet = new HashSet<>(targetClass.getEquipment());
        for (Equipment equipment : fitnessClass.getEquipment()) {
            if (targetEquipmentSet.contains(equipment)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to retrieve a list of fitness classes that are similar to a target class.
     * A class is considered similar if it has the same trainer and at least one common piece of equipment.
     * This method searches through all existing fitness classes in the repository and returns those that match
     * the criteria of similarity to the provided target class.
     * @param targetClass The fitness class to compare against other classes.
     * @return A list of fitness classes that are similar to the target class based on trainer and equipment.
     * @throws IllegalArgumentException if no similar classes are found.
     */
    public List<FitnessClass> getSimilarClasses(FitnessClass targetClass) {
        if (targetClass == null) {
            throw new IllegalArgumentException("Target class must not be null.");
        }
        List<FitnessClass> allClasses = fitnessClassRepository.getAll();
        List<FitnessClass> similarClasses = new ArrayList<>();
        for (FitnessClass fitnessClass : allClasses) {
            if (fitnessClass.getId() == targetClass.getId()) {
                continue;
            }
            if (findSimilarClasses(fitnessClass, targetClass) && fitnessClass.getStartTime().isAfter(LocalDateTime.now())) {
                similarClasses.add(fitnessClass);
            }
        }
        if (similarClasses != null) {return similarClasses;}
        return null;
    }

    /**
     * Registers a member for a fitness class, ensuring the class is not full and the member is not already registered.
     * This method checks if the class exists, if the member is already registered, and if there are available slots
     * in the class. If all checks pass, the member is added to the class, and the participant count is updated.
     * The class is then persisted to the repository with the new member and participant count.
     * @param memberId The ID of the member who wants to register.
     * @param classId The ID of the fitness class to which the member wants to register.
     * @throws IllegalArgumentException If the fitness class does not exist.
     * @throws IllegalStateException If the member is already registered for the class or if the class is full.
     */
    public void registerToClass(int memberId, int classId) {
        FitnessClass fitnessClass = fitnessClassRepository.read(classId);
        if (fitnessClass == null) {
            throw new IllegalArgumentException("Fitness class with ID " + classId + " does not exist.");
        }
        Member member = memberRepository.read(memberId);
        if (fitnessClass.getMembers().contains(member)) {
            throw new IllegalStateException("Member is already registered for this class.");
        }
        if (fitnessClass.getParticipantsCount() >= fitnessClass.getRoom().getMaxCapacity()) {
            throw new IllegalStateException("The class is already full.");
        }
        fitnessClass.getMembers().add(member);
        fitnessClass.setParticipantsCount(fitnessClass.getParticipantsCount() + 1);
        fitnessClassRepository.update(fitnessClass);
    }

    /**
     * Drops a member from a fitness class, removing them from the class and updating the participant count.
     * This method checks if the class exists and if the member is currently registered for the class.
     * If the member is registered, they are removed from the class, and the participant count is updated.
     * The class is then persisted to the repository with the updated member list and participant count.
     * @param memberId The ID of the member who wants to drop the class.
     * @param classId The ID of the fitness class the member wants to drop from.
     * @throws IllegalArgumentException If the fitness class does not exist.
     * @throws IllegalStateException If the member is not registered for the class.
     */
    public void dropClass(int memberId, int classId) {
        FitnessClass fitnessClass = fitnessClassRepository.read(classId);
        if (fitnessClass == null) {
            throw new IllegalArgumentException("Fitness class with ID " + classId + " does not exist.");
        }
        Member member = memberRepository.read(memberId);
        if (!fitnessClass.getMembers().contains(member)) {
            throw new IllegalStateException("Member is not registered for this class.");
        }
        fitnessClass.getMembers().remove(member);
        fitnessClass.setParticipantsCount(fitnessClass.getParticipantsCount() - 1);
        fitnessClassRepository.update(fitnessClass);
    }

    /**
     * Retrieves the feedback for a specific fitness class.
     * This method checks if the fitness class exists by its ID. If the class exists, it returns the list of feedback
     * associated with the class. If there is no feedback or if the class does not exist, an appropriate exception is thrown.
     * @param classId The ID of the fitness class for which feedback is to be retrieved.
     * @return A list of Feedback objects associated with the specified fitness class.
     * @throws IllegalArgumentException If the fitness class does not exist or if no feedback is available for the class.
     */
    public List<Feedback> getClassFeedback (int classId) {
        FitnessClass fitnessClass = fitnessClassRepository.read(classId);
        if (fitnessClass == null) {
            throw new IllegalArgumentException("Fitness class with ID" + classId + " does not exist.");
        }
        List<Feedback> feedbackList = fitnessClass.getFeedback();
        if (feedbackList == null || feedbackList.isEmpty()) {
            throw new IllegalArgumentException("No feedback available for this fitness class");
        }
        return feedbackList;
    }

    /**
     * Retrieves all fitness classes taught by a specific trainer.
     * This method fetches all the available fitness classes and filters them based on the trainer's ID. It returns a list
     * of fitness classes that are taught by the trainer with the specified ID.
     * @param trainerId The ID of the trainer for which the classes are to be retrieved.
     * @return A list of FitnessClass objects taught by the trainer with the specified ID.
     * @throws IllegalStateException If no classes are found for the specified trainer.
     */
    public ArrayList<FitnessClass> getAllClassesByTrainer(int trainerId) {
        List<FitnessClass> classes = getAllFitnessClasses();
        if (classes == null) {
            throw new IllegalArgumentException("No classes found by this trainer");
        }
        return filterByTrainerID(trainerId, classes);
    }

    /**
     * Filters a list of fitness classes by the trainer's ID.
     * This helper method takes a list of fitness classes and returns only those classes that are taught by the trainer with
     * the specified ID.
     * @param id The trainer's ID to filter the classes by.
     * @param classList The list of fitness classes to be filtered.
     * @return A filtered ArrayList<FitnessClass> containing only the classes taught by the specified trainer.
     */
    public ArrayList<FitnessClass> filterByTrainerID(int id, List<FitnessClass> classList) {
        if (classList == null) {
            throw new IllegalArgumentException("Class list must not be null.");
        }
        ArrayList<FitnessClass> filteredList = new ArrayList<>();
        for (FitnessClass fitnessClass : classList) {
            if(fitnessClass.getTrainer().getId() == id) {
                filteredList.add(fitnessClass);
            }
        }
        return filteredList;
    }

    /**
     * Retrieves all fitness classes in which a member has participated in the past.
     * This method fetches the fitness classes associated with a specific member based on the member's ID. It checks if the
     * member is registered in any classes. If no classes are found, it throws an exception.
     * @param memberId The ID of the member whose classes are to be retrieved.
     * @return A list of FitnessClass objects in which the member has participated.
     * @throws IllegalStateException If the member has not participated in any classes.
     */
    public List<FitnessClass> getClassesByMember(int memberId) {
        Member member = memberRepository.read(memberId);
        List<FitnessClass> memberClasses = member.getFitnessClasses();
        List<FitnessClass> pastMemberClasses = new ArrayList<>();
        if (memberClasses == null || memberClasses.isEmpty()) {
            throw new IllegalStateException("This member has not participated in any classes.");
        }
        for (FitnessClass fitnessClass : memberClasses) {
            if (fitnessClass.getEndTime().isBefore(LocalDateTime.now())) {
                pastMemberClasses.add(fitnessClass);
            }
        }
        return pastMemberClasses;
    }

    /**
     * Finds a fitness class by its ID.
     * This method searches for a fitness class with the given ID from the list of all available classes. If a class with the
     * specified ID is found, it is returned. Otherwise, an exception is thrown indicating that no class with the given ID
     * exists.
     * @param classId The ID of the fitness class to be retrieved.
     * @return The FitnessClass object corresponding to the specified ID.
     * @throws IllegalArgumentException If no class with the specified ID exists.
     */
    public FitnessClass findClassById(int classId) {
        List<FitnessClass> allClasses = getAllFitnessClasses();
        for (FitnessClass fitnessClass : allClasses) {
            if (fitnessClass.getId() == classId) {
                return fitnessClass;
            }
        }
        throw new IllegalArgumentException("No classes found with ID " + classId + ".");
    }

    /**
     * Sorts all upcoming fitness classes in ascending order by their start time.
     * This method retrieves a list of all upcoming fitness classes and sorts them so that the earliest-starting class
     * appears first. Useful for displaying the next available classes to users.
     * @return A list of FitnessClass objects, sorted by their start time in ascending order.
     */
    public List<FitnessClass> sortUpcomingClassesASC() {
        List<FitnessClass> fitnessClasses = getAllUpcomingClasses();
        if (fitnessClasses == null) {
            throw new IllegalArgumentException("No upcoming classes available.");
        }
        fitnessClasses.sort(Comparator.comparing(FitnessClass::getStartTime));
        return fitnessClasses;
    }

    /**
     * Sorts all upcoming fitness classes for a specific trainer in ascending order by their start time.
     * This method filters the list of all fitness classes to include only those assigned to the specified trainer, then sorts
     * them so that the earliest-starting class appears first.
     * @param trainerId The ID of the trainer whose classes are to be sorted.
     * @return A list of FitnessClass objects for the specified trainer, sorted by their start time in ascending order.
     */
    public List<FitnessClass> sortUpcomingTrainerClassesASC(int trainerId) {
        List<FitnessClass> fitnessClasses = getTrainerUpcomingClasses(trainerId);
        if (fitnessClasses == null) {
            throw new IllegalArgumentException("No upcoming classes available.");
        }
        fitnessClasses.sort(Comparator.comparing(FitnessClass::getStartTime));
        return fitnessClasses;
    }

    /**
     * Retrieves a list of fitness classes that a member has attended in the past.
     * This method checks the fitness classes registered to the specified member and returns those that have already ended.
     * If the member hasn't attended any past classes, an exception is thrown.
     * @param memberId The ID of the member whose past fitness classes are to be retrieved.
     * @return A list of FitnessClass objects representing the past classes attended by the member.
     * @throws IllegalStateException If the member has not attended any past fitness classes.
     */
    public List<FitnessClass> getPastClassesAttendedByMember(int memberId) {
        Member member = memberRepository.read(memberId);
        List<FitnessClass> pastClasses = new ArrayList<>();
        for (FitnessClass fitnessClass : member.getFitnessClasses()) {
            if (fitnessClass.getEndTime().isBefore(LocalDateTime.now())) {
                pastClasses.add(fitnessClass);
            }
        }
        if (pastClasses.isEmpty()) {
            throw new IllegalStateException("Member with ID " + memberId + " has not attended any past fitness classes.");
        }
        return pastClasses;
    }

    /**
     * Adds feedback for a specific fitness class.
     * This method allows a member to leave feedback on a fitness class they attended, including a text review and a rating.
     * The feedback is saved in the repository and associated with both the member and the class.
     * @param memberId The ID of the member providing the feedback.
     * @param classId The ID of the fitness class for which the feedback is being left.
     * @param feedbackContent The text content of the feedback.
     * @param rating The rating provided by the member (e.g., on a scale of 1 to 5).
     * @throws IllegalArgumentException If the fitness class with the specified ID does not exist.
     */
    public void addFeedbackForClass(int memberId, int classId, String feedbackContent, int rating) {
        Member member = memberRepository.read(memberId);
        FitnessClass fitnessClass = getFitnessClass(classId);
        if (fitnessClass == null) {
            throw new IllegalArgumentException("Fitness class with ID " + classId + " does not exist.");
        }
        Feedback feedback = new Feedback(member, fitnessClass, rating, feedbackContent);
        feedback.setId(HelperFunctions.randomId());
        feedbackRepository.create(feedback);
        fitnessClass.getFeedback().add(feedback);
        fitnessClassRepository.update(fitnessClass);
        System.out.println("Feedback added successfully");
    }

}
