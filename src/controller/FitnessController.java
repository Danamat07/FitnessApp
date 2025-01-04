package controller;
import model.*;
import service.FitnessService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The FitnessController class serves as the intermediary between the service layer (FitnessService) and the user interface
 * or other components that interact with the fitness-related features. It provides methods for interacting with the underlying
 * data and handling business logic.
 * This controller contains methods that make use of the FitnessService to perform operations related to fitness classes,
 * schedules, trainers, rooms, members, and other relevant entities.
 *
 * <p>Primary responsibilities include:</p>
 * <ul>
 *     <li>Invoking methods from the service layer to perform actions like scheduling, registering, updating, and deleting records.</li>
 *     <li>Serving as a middle layer that coordinates data flow between the user interface and the service layer.</li>
 * </ul>
 */
public class FitnessController {

    private final FitnessService fitnessService;

    /**
     * Constructor for initializing the FitnessController.
     */
    public FitnessController(FitnessService fitnessService) {
        this.fitnessService = fitnessService;
    }

    /**
     * Retrieves and displays the name and quantity of all available equipment.
     * If no equipment is available, a message is displayed. Catches and prints any
     * IllegalStateException that occurs during retrieval.
     */
    public void displayAllEquipment() {
        try {
            List<Equipment> equipmentList = fitnessService.getAllEquipment();
            if (equipmentList.isEmpty()) {
                System.out.println("No equipment available.");
            } else {
                for (Equipment equipment : equipmentList) {
                    System.out.println("ID: " + equipment.getId());
                    System.out.println("Name: " + equipment.getName());
                    System.out.println("Quantity: " + equipment.getQuantity());
                    System.out.println("----------------------------------------");
                }
            }
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Retrieves the equipment with the specified ID.
     * If an IllegalArgumentException is thrown during retrieval, it logs the error message and returns null.
     */
    public Equipment getEquipment(int id){
        try{
            return fitnessService.getEquipment(id);
        }catch (IllegalArgumentException e){
            System.err.println(e.getMessage());
        }
        return null;
    }

    /**
     * Displays the name and quantity of the equipment with the specified ID.
     * If an IllegalArgumentException is thrown during retrieval, it logs the error message.
     */
    public void displayEquipmentById(int id) {
        try {
            Equipment equipment = fitnessService.getEquipment(id);
            System.out.println("Name: " + equipment.getName());
            System.out.println("Quantity: " + equipment.getQuantity());
            System.out.println("----------------------------------------");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Adds the specified equipment to the system.
     * If an IllegalArgumentException is thrown during the addition, it logs the error message.
     */
    public void addEquipment(Equipment equipment) {
        try {
            fitnessService.addEquipment(equipment);
            System.out.println("Equipment added successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Updates the equipment with the specified ID, name, quantity, and associated fitness classes.
     * If an IllegalArgumentException is thrown during the update, it logs the error message.
     */
    public void updateEquipment(int id, String name, int quantity, List<FitnessClass> fitnessClasses) {
        try {
            fitnessService.updateEquipment(id, name, quantity, fitnessClasses);
            System.out.println("Equipment updated successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Deletes the equipment with the specified ID.
     * If an IllegalArgumentException is thrown during deletion, it logs the error message.
     */
    public void deleteEquipment(int id) {
        try {
            fitnessService.deleteEquipment(id);
            System.out.println("Equipment deleted successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Retrieves and displays all feedback, including member name, fitness class name, rating, and comment.
     * If no feedback is available, a message is displayed. If an IllegalStateException occurs during retrieval,
     * it logs the error message.
     */
    public void displayAllFeedback() {
        try {
            List<Feedback> feedbackList = fitnessService.getAllFeedback();
            if (feedbackList.isEmpty()) {
                System.out.println("No feedback available.");
            } else {
                for (Feedback feedback : feedbackList) {
                    System.out.println("Member: " + feedback.getMember().getName());
                    System.out.println("Fitness Class: " + feedback.getFitnessClass().getName());
                    System.out.println("Rating: " + feedback.getRating());
                    System.out.println("Comment: " + feedback.getComment());
                    System.out.println("----------------------------------------");
                }
            }
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Retrieves and displays the feedback with the specified ID, including member name, fitness class name,
     * rating, and comment. If an IllegalArgumentException occurs during retrieval, it logs the error message.
     */
    public void displayFeedbackById(int id) {
        try {
            Feedback feedback = fitnessService.getFeedback(id);
            System.out.println("Member: " + feedback.getMember().getName());
            System.out.println("Fitness Class: " + feedback.getFitnessClass().getName());
            System.out.println("Rating: " + feedback.getRating());
            System.out.println("Comment: " + feedback.getComment());
            System.out.println("----------------------------------------");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Adds the specified feedback to the system.
     * If an IllegalArgumentException is thrown, it logs the error message.
     */
    public void addFeedback(Feedback feedback) {
        try {
            fitnessService.addFeedback(feedback);
            System.out.println("Feedback added successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Updates the feedback with the specified ID, rating, and comment.
     * If an IllegalArgumentException is thrown, it logs the error message.
     */
    public void updateFeedback(int id, int rating, String comment) {
        try {
            fitnessService.updateFeedback(id, rating, comment);
            System.out.println("Feedback updated successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Deletes the feedback with the specified ID.
     * If an IllegalArgumentException is thrown, it logs the error message.
     */
    public void deleteFeedback(int id) {
        try {
            fitnessService.deleteFeedback(id);
            System.out.println("Feedback deleted successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Retrieves a fitness calss by its ID.
     * Logs any IllegalArgumentException that occurs and returns null if an exception is thrown.
     */
    public FitnessClass getFitnessClass(int id) {
        try {
            return fitnessService.getFitnessClass(id);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    /**
     * Retrieves and displays all fitness classes, including their name, start time, end time, trainer, room, and location.
     * If no fitness classes are available, a message is displayed. If an IllegalStateException occurs during retrieval,
     * it logs the error message.
     */
    public void displayAllFitnessClasses() {
        try {
            List<FitnessClass> fitnessClassList = fitnessService.getAllFitnessClasses();
            if (fitnessClassList.isEmpty()) {
                System.out.println("No fitness classes available.");
            } else {
                for (FitnessClass fitnessClass : fitnessClassList) {
                    System.out.println("ID: " + fitnessClass.getId());
                    System.out.println("Name: " + fitnessClass.getName());
                    System.out.println("Start time: " + fitnessClass.getStartTime());
                    System.out.println("End time: " + fitnessClass.getEndTime());
                    System.out.println("----------------------------------------");
                }
            }
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Retrieves and displays the details of the fitness class with the specified ID, including its name, start time,
     * end time, trainer, room, and location. If an IllegalArgumentException occurs during retrieval, it logs the error message.
     */
    public void displayFitnessClassById(int id) {
        try {
            FitnessClass fitnessClass = fitnessService.getFitnessClass(id);
            System.out.println("Name: " + fitnessClass.getName());
            System.out.println("Start time: " + fitnessClass.getStartTime());
            System.out.println("End time: " + fitnessClass.getEndTime());
            System.out.println("Trainer: " + fitnessClass.getTrainer().getName());
            System.out.println("Room: " + fitnessClass.getRoom().getName());
            System.out.println("Location: " + fitnessClass.getLocation());
            System.out.println("----------------------------------------");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Adds the specified fitness class to the system.
     * If an IllegalArgumentException is thrown, it logs the error message.
     */
    public void addFitnessClass(FitnessClass fitnessClass) {
        try {
            fitnessService.addFitnessClass(fitnessClass);
            System.out.println("Fitness class added successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Updates the fitness class with the specified ID, including its details such as name, start time, end time,
     * trainer, room, participant count, location, feedback, members, and equipment.
     * If an IllegalArgumentException is thrown, it logs the error message.
     */
    public void updateFitnessClass(int id, String name, LocalDateTime stratTime, LocalDateTime endTime, Trainer trainer, Room room,
                                   int participantsCount, Location location,
                                   List<Feedback> feedback, List<Member> members, List<Equipment> equipment) {
        try {
            fitnessService.updateFitnessClass(id, name, stratTime, endTime, trainer, room, participantsCount,
                    location, feedback, members, equipment);
            System.out.println("Fitness class updated successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Deletes the fitness class with the specified ID.
     * If an IllegalArgumentException is thrown, it logs the error message.
     */
    public void deleteFitnessClass(int id) {
        try {
            fitnessService.deleteFitnessClass(id);
            System.out.println("Fitness class deleted successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Retrieves a location by its ID.
     * Logs any IllegalArgumentException that occurs and returns null if an exception is thrown.
     */
    public Location getLocation(int id) {
        try {
            return fitnessService.getLocation(id);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    /**
     * Displays all locations, including their name and address.
     * If no locations are available, a message is displayed. Logs any IllegalStateException that occurs.
     */
    public void displayAllLocations() {
        try {
            List<Location> locationList = fitnessService.getAllLocations();
            if (locationList.isEmpty()) {
                System.out.println("No locations available.");
            } else {
                for (Location location : locationList) {
                    System.out.println("ID: " + location.getId());
                    System.out.println("Name: " + location.getName());
                    System.out.println("Address: " + location.getAddress());
                    System.out.println("----------------------------------------");
                }
            }
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Displays the details of a location by its ID, including its name and address.
     * If an IllegalArgumentException is thrown, it logs the error message.
     */
    public void displayLocationById(int id) {
        try {
            Location location = fitnessService.getLocation(id);
            System.out.println("Name: " + location.getName());
            System.out.println("Address: " + location.getAddress());
            System.out.println("----------------------------------------");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Adds a new location to the system.
     * If an IllegalArgumentException is thrown, it logs the error message.
     */
    public void addLocation(Location location) {
        try {
            fitnessService.addLocation(location);
            System.out.println("Location added successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Updates the location with the specified ID, name, and address.
     * If an IllegalArgumentException is thrown, it logs the error message.
     */
    public void updateLocation(int id, String name, String address) {
        try {
            fitnessService.updateLocation(id, name, address);
            System.out.println("Location updated successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Deletes the location with the specified ID.
     * If an IllegalArgumentException is thrown, it logs the error message.
     */
    public void deleteLocation(int id) {
        try {
            fitnessService.deleteLocation(id);
            System.out.println("Location deleted successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Displays all members, including their name, registration date, and membership type.
     * If no members are available, a message is displayed. If an IllegalStateException occurs during retrieval,
     * it logs the error message.
     */
    public void displayAllMembers() {
        try {
            List<Member> memberList = fitnessService.getAllMembers();
            if (memberList.isEmpty()) {
                System.out.println("No members available.");
            } else {
                for (Member member : memberList) {
                    System.out.println("Name: " + member.getName());
                    System.out.println("Registration Date: " + member.getRegistrationDate());
                    System.out.println("Membership Type: " + member.getMembership());
                    System.out.println("----------------------------------------");
                }
            }
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Displays the details of a member by their ID, including their name, registration date, and membership type.
     * If an IllegalArgumentException is thrown, it logs the error message.
     */
    public void displayMemberById(int id) {
        try {
            Member member = fitnessService.getMember(id);
            System.out.println("Name: " + member.getName());
            System.out.println("Registration Date: " + member.getRegistrationDate());
            System.out.println("Membership Type: " + member.getMembership());
            System.out.println("----------------------------------------");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Retrieves the member with the specified ID.
     * If an IllegalArgumentException is thrown, it logs the error message and returns null.
     */
    public Member getMember(int id) {
        try {
            fitnessService.getMember(id);
        }catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    /**
     * Adds a new member to the system.
     * If an IllegalArgumentException is thrown, it logs the error message.
     */
    public void addMember(Member member) {
        try {
            fitnessService.addMember(member);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Updates a member's details.
     * Logs any IllegalArgumentException that occurs.
     */
    public void updateMember(int id, String name, String password, Membership membership, List<FitnessClass> fitnessClasses) {
        try {
            fitnessService.updateMember(id, name, password, membership);
            System.out.println("Account updated successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Deletes the member with the specified ID.
     * Logs any IllegalArgumentException that occurs.
     */
    public void deleteMember(int id) {
        try {
            fitnessService.deleteMember(id);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Displays all memberships, including type and price.
     * If no memberships are available, a message is displayed.
     * Logs any IllegalStateException that occurs.
     */
    public void displayAllMemberships() {
        try {
            List<Membership> membershipList = fitnessService.getAllMemberships();
            if (membershipList.isEmpty()) {
                System.out.println("No memberships available.");
            } else {
                for (Membership membership : membershipList) {
                    System.out.println("ID: " + membership.getId());
                    System.out.println("Type: " + membership.getType());
                    System.out.println("Price: " + membership.getPrice());
                    System.out.println("----------------------------------------");
                }
            }
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Displays the details of a membership by its ID, including type and price.
     * Logs any IllegalArgumentException that occurs.
     */
    public void displayMembershipById(int id) {
        try {
            Membership membership = fitnessService.getMembership(id);
            System.out.println("Type: " + membership.getType());
            System.out.println("Price: " + membership.getPrice());
            System.out.println("----------------------------------------");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Adds a new membership to the system.
     * Logs any IllegalArgumentException that occurs.
     */
    public void addMembership(Membership membership) {
        try {
            fitnessService.addMembership(membership);
            System.out.println("Membership added successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Updates the details of an existing membership.
     */
    public void updateMembership(int id, String type, float price) {
        try {
            fitnessService.updateMembership(id, type, price);
            System.out.println("Membership updated successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Deletes a membership by its ID.
     * Logs any IllegalArgumentException that occurs.
     */
    public void deleteMembership(int id) {
        try {
            fitnessService.deleteMembership(id);
            System.out.println("Membership deleted successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Retrieves a membership by its ID.
     * Logs any IllegalArgumentException that occurs and returns null if an exception is thrown.
     */
    public Membership getMembership(int id) {
        try {
            return fitnessService.getMembership(id);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    /**
     * Retrieves all memberships.
     * Logs any IllegalArgumentException that occurs and returns null if an exception is thrown.
     */
    public List<Membership> getAllMemberships(){
        try{
            return fitnessService.getAllMemberships();
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    /**
     * Retrieves a room by its ID.
     * Logs any IllegalArgumentException that occurs and returns null if an exception is thrown.
     */
    public Room getRoom(int id) {
        try {
            return fitnessService.getRoom(id);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    /**
     * Displays all rooms, including room name, max capacity, and location.
     * If no rooms are available, a message is displayed.
     * Logs any IllegalStateException that occurs.
     */
    public void displayAllRooms() {
        try {
            List<Room> roomList = fitnessService.getAllRooms();
            if (roomList.isEmpty()) {
                System.out.println("No rooms available.");
            } else {
                for (Room room : roomList) {
                    System.out.println("Room ID: " + room.getId());
                    System.out.println("Room Name: " + room.getName());
                    System.out.println("Max Capacity: " + room.getMaxCapacity());
                    System.out.println("Location: " + room.getLocation().getName());
                    System.out.println("----------------------------------------");
                }
            }
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Displays the details of a room by its ID, including room name, max capacity, and location.
     * Logs any IllegalArgumentException that occurs.
     */
    public void displayRoomById(int id) {
        try {
            Room room = fitnessService.getRoom(id);
            System.out.println("Room Name: " + room.getName());
            System.out.println("Max Capacity: " + room.getMaxCapacity());
            System.out.println("Location: " + room.getLocation().getName());
            System.out.println("----------------------------------------");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Adds a new room to the system.
     * Logs any IllegalArgumentException that occurs.
     */
    public void addRoom(Room room) {
        try {
            fitnessService.addRoom(room);
            System.out.println("Room added successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Updates the details of a room by its ID.
     * Logs any IllegalArgumentException that occurs.
     */
    public void updateRoom(int id, String name, int maxCapacity, Location location) {
        try {
            fitnessService.updateRoom(id, name, maxCapacity, location);
            System.out.println("Room updated successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Deletes a room by its ID.
     * Logs any IllegalArgumentException that occurs.
     */
    public void deleteRoom(int id) {
        try {
            fitnessService.deleteRoom(id);
            System.out.println("Room deleted successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Displays all trainers, including their name and specialization.
     * If no trainers are available, a message is displayed.
     * Logs any IllegalStateException that occurs.
     */
    public void displayAllTrainers() {
        try {
            List<Trainer> trainerList = fitnessService.getAllTrainers();
            if (trainerList.isEmpty()) {
                System.out.println("No trainers available.");
            } else {
                for (Trainer trainer : trainerList) {
                    System.out.println("Name: " + trainer.getName());
                    System.out.println("Specialisation: " + trainer.getSpecialisation());
                    System.out.println("----------------------------------------");
                }
            }
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Displays the details of a trainer by their ID, including their name and specialization.
     * Logs any IllegalArgumentException that occurs.
     */
    public void displayTrainerById(int id) {
        try {
            Trainer trainer = fitnessService.getTrainer(id);
            System.out.println("Name: " + trainer.getName());
            System.out.println("Specialisation: " + trainer.getSpecialisation());
            System.out.println("----------------------------------------");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Retrieves a trainer by their ID.
     * Logs any IllegalArgumentException that occurs and returns null if the trainer is not found.
     */
    public Trainer getTrainer(int id) {
        try {
            fitnessService.getTrainer(id);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    /**
     * Adds a new trainer to the system.
     * Logs any IllegalArgumentException that occurs.
     */
    public void addTrainer(Trainer trainer) {
        try {
            fitnessService.addTrainer(trainer);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Updates the details of an existing trainer.
     * Logs any IllegalArgumentException that occurs.
     */
    public void updateTrainer(int id, String name, String password, String specialisation) {
        try {
            fitnessService.updateTrainer(id, name, password, specialisation);
            System.out.println("Account updated successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Deletes a trainer from the system.
     * Logs any IllegalArgumentException that occurs.
     */
    public void deleteTrainer(int id) {
        try {
            fitnessService.deleteTrainer(id);
            System.out.println("Trainer deleted successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Retrieves and displays all upcoming fitness classes that a member is not registered in.
     * Logs any IllegalArgumentException that occurs.
     */
    public void getAllUpcomingClasses_MemberNotRegisteredYet(int memberId) {
        try {
            List<FitnessClass> notRegisteredClasses = fitnessService.getAllUpcomingClasses_MemberNotRegisteredYet(memberId);
            for (FitnessClass fitnessClass : notRegisteredClasses) {
                System.out.println(fitnessClass.toStringLessInfo());
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Schedules a new fitness class with the specified details.
     * Logs any IllegalArgumentException that occurs.
     */
    public void scheduleNewClass(String className, LocalDateTime startTime, LocalDateTime endTime, int trainerId,
                                 int roomId, int participantsCount, int locationId, List<Equipment> equipment) {
        try{
            fitnessService.scheduleNewClass(className, startTime, endTime, trainerId, roomId, participantsCount,
                    locationId, equipment);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Retrieves and displays a list of fitness classes similar to the specified target class.
     * @throws IllegalArgumentException if there is an error fetching similar classes.
     */
    public void getSimilarClasses(FitnessClass targetClass) {
        try {
            List<FitnessClass> similarClasses = fitnessService.getSimilarClasses(targetClass);
            for (FitnessClass fitnessClass : similarClasses) {
                System.out.println(fitnessClass.toStringLessInfo() + "\n");
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Registers a member to a specified fitness class.
     * @throws IllegalArgumentException if registration fails due to invalid IDs or other issues.
     */
    public void registerToClass(int memberId, int classId) {
        try {
            fitnessService.registerToClass(memberId, classId);
            System.out.println("Registration done successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Allows a member to drop a previously registered fitness class.
     * @throws IllegalArgumentException if the class cannot be dropped due to invalid IDs or other issues.
     */
    public void dropClass(int memberId, int classId) {
        try {
            fitnessService.dropClass(memberId, classId);
            System.out.println("Class dropped successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Displays all feedback for a specific fitness class.
     * @throws IllegalArgumentException if no feedback is found for the specified class.
     */
    public void displayFeedback(int classID){
        try {
            List<Feedback> feedbackList = fitnessService.getClassFeedback(classID);
            for(Feedback feedback: feedbackList){
                System.out.println("Rating: " + feedback.getRating() + "/5\nComment: " + feedback.getComment() + "\n");
            }
        } catch (IllegalArgumentException e){
            System.err.println(e.getMessage());
        }
    }

    /**
     * Displays all fitness classes taught by a specific trainer.
     * @throws IllegalArgumentException if no classes are found for the specified trainer.
     */
    public void displayClassesOfTrainer(int trainerId) {
        try {
            List<FitnessClass> classes = fitnessService.getAllClassesByTrainer(trainerId);
            if (classes.isEmpty()) {
                System.out.println("No fitness classes found for trainer with ID: " + trainerId);
                return;
            }
            for (FitnessClass fitnessClass : classes) {
                System.out.println("Class ID: " + fitnessClass.getId() +
                        ", Name: " + fitnessClass.getName());
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Displays all fitness classes in which a member has participated in the past.
     * @throws IllegalArgumentException if no classes are found for the specified member.
     */
    public void displayClassesByMember(int memberId) {
        try {
            List<FitnessClass> classes = fitnessService.getClassesByMember(memberId);
            for (FitnessClass fitnessClass : classes) {
                System.out.println(fitnessClass.toStringLessInfo() + "\n");
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Finds and returns a fitness class by its ID.
     * @throws IllegalArgumentException if the class with the specified ID does not exist.
     */
    public FitnessClass findClassById(int classId) {
        try {
            return fitnessService.findClassById(classId);
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return null;
    }

    /**
     * Displays all upcoming fitness classes sorted by their start time in ascending order.
     * If no upcoming classes are available, it prints a corresponding message.
     * Otherwise, it prints the details of each class.
     */
    public void displaySortedUpcomingClasses() {
        try {
            List<FitnessClass> sortedClasses = fitnessService.sortUpcomingClassesASC();
            if (sortedClasses.isEmpty()) {
                System.out.println("No upcoming classes.");
            } else {
                System.out.println("Upcoming Classes (sorted by start time):");
                for (FitnessClass fitnessClass : sortedClasses) {
                    System.out.println(fitnessClass.toStringLessInfo() + "\n");
                }
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Displays the upcoming classes for a specific trainer, sorted by start time.
     * If no classes are found, a message is displayed.
     */
    public void displaySortedTrainerUpcomingClasses(int trainerId) {
        try {
            List<FitnessClass> sortedClasses = fitnessService.sortUpcomingTrainerClassesASC(trainerId);
            if (sortedClasses.isEmpty()) {
                System.out.println("No upcoming classes.");
            } else {
                System.out.println("Upcoming Classes (sorted by start time):");
                for (FitnessClass fitnessClass : sortedClasses) {
                    System.out.println(fitnessClass.toStringLessInfo() + "\n");
                }
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Retrieves the list of past fitness classes attended by a specific member.
     */
    public List<FitnessClass> getPastClassesAttendedByMember(int memberId) {
        try {
            return fitnessService.getPastClassesAttendedByMember(memberId);
        } catch (IllegalArgumentException e){
            System.err.println(e.getMessage());
        }
        return null;
    }

    /**
     * Displays the past fitness classes attended by a specific member.
     */
    public void displayPastClassesAttendedByMember(int memberId) {
        try {
            for (FitnessClass fitnessClass : getPastClassesAttendedByMember(memberId)) {
                System.out.println(fitnessClass.toStringLessInfo() + "\n");
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Adds feedback for a specific fitness class by a member.
     */
    public void addFeedbackForClass(int memberId, int classId, String feedbackContent, int rating) {
        try {
            fitnessService.addFeedbackForClass(memberId, classId, feedbackContent, rating);
        } catch (IllegalArgumentException e){
            System.err.println(e.getMessage());
        }
    }

}