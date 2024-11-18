package controller;
import model.*;
import service.FitnessService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class FitnessController {

    private final FitnessService fitnessService;

    // Controller constructor
    public FitnessController(FitnessService fitnessService) {
        this.fitnessService = fitnessService;
    }

    // ----- Equipment -----

    // Display all equipment
    public void displayAllEquipment() {
        try {
            List<Equipment> equipmentList = fitnessService.getAllEquipment();
            if (equipmentList.isEmpty()) {
                System.out.println("No equipment available.");
            } else {
                for (Equipment equipment : equipmentList) {
                    System.out.println("Equipment ID: " + equipment.getId());
                    System.out.println("Name: " + equipment.getName());
                    System.out.println("Quantity: " + equipment.getQuantity());
                    System.out.println("Associated Fitness Classes: " + (equipment.getFitnessClasses().isEmpty() ? "None" : equipment.getFitnessClasses()));
                    System.out.println("----------------------------------------");
                }
            }
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
    }

    //Get equipment by ID
    public Equipment getEquipment(int id){
        try{
            return fitnessService.getEquipment(id);
        }catch (IllegalArgumentException e){
            System.err.println(e.getMessage());
        }
        return null;
    }

    // Display equipment by ID
    public void displayEquipmentById(int id) {
        try {
            Equipment equipment = fitnessService.getEquipment(id);
            System.out.println("Equipment ID: " + equipment.getId());
            System.out.println("Name: " + equipment.getName());
            System.out.println("Quantity: " + equipment.getQuantity());
            System.out.println("Associated Fitness Classes: " + (equipment.getFitnessClasses().isEmpty() ? "None" : equipment.getFitnessClasses()));
            System.out.println("----------------------------------------");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    // Add new equipment
    public void addEquipment(String name, int quantity, List<FitnessClass> fitnessClasses) {
        try {
            fitnessService.addEquipment(name, quantity, fitnessClasses);
            System.out.println("Equipment added successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    // Update existing equipment
    public void updateEquipment(int id, String name, int quantity, List<FitnessClass> fitnessClasses) {
        try {
            fitnessService.updateEquipment(id, name, quantity, fitnessClasses);
            System.out.println("Equipment updated successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    // Delete equipment by ID
    public void deleteEquipment(int id) {
        try {
            fitnessService.deleteEquipment(id);
            System.out.println("Equipment deleted successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    // ----- Feedback -----

    // Display all feedback
    public void displayAllFeedback() {
        try {
            List<Feedback> feedbackList = fitnessService.getAllFeedback();
            if (feedbackList.isEmpty()) {
                System.out.println("No feedback available.");
            } else {
                for (Feedback feedback : feedbackList) {
                    System.out.println("Feedback ID: " + feedback.getId());
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

    // Display feedback by ID
    public void displayFeedbackById(int id) {
        try {
            Feedback feedback = fitnessService.getFeedback(id);
            System.out.println("Feedback ID: " + feedback.getId());
            System.out.println("Member: " + feedback.getMember().getName());
            System.out.println("Fitness Class: " + feedback.getFitnessClass().getName());
            System.out.println("Rating: " + feedback.getRating());
            System.out.println("Comment: " + feedback.getComment());
            System.out.println("----------------------------------------");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    // Add new feedback
    public void addFeedback(Member member, FitnessClass fitnessClass, int rating, String comment) {
        try {
            fitnessService.addFeedback(member, fitnessClass, rating, comment);
            System.out.println("Feedback added successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    // Update existing feedback
    public void updateFeedback(int id, Member member, FitnessClass fitnessClass, int rating, String comment) {
        try {
            fitnessService.updateFeedback(id, member, fitnessClass, rating, comment);
            System.out.println("Feedback updated successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    // Delete feedback by ID
    public void deleteFeedback(int id) {
        try {
            fitnessService.deleteFeedback(id);
            System.out.println("Feedback deleted successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    // ----- Fitness Class -----

    // Display all fitness classes
    public void displayAllFitnessClasses() {
        try {
            List<FitnessClass> fitnessClassList = fitnessService.getAllFitnessClasses();
            if (fitnessClassList.isEmpty()) {
                System.out.println("No fitness classes available.");
            } else {
                for (FitnessClass fitnessClass : fitnessClassList) {
                    System.out.println("Fitness Class ID: " + fitnessClass.getId());
                    System.out.println("Name: " + fitnessClass.getName());
                    System.out.println("Start time: " + fitnessClass.getStartTime());
                    System.out.println("End time: " + fitnessClass.getEndTime());
                    System.out.println("Trainer: " + fitnessClass.getTrainer().getName());
                    System.out.println("Room: " + fitnessClass.getRoom().getName());
                    System.out.println("Participants Count: " + fitnessClass.getParticipantsCount());
                    System.out.println("Location: " + fitnessClass.getLocation());
                    System.out.println("Associated Equipment: " + (fitnessClass.getEquipment().isEmpty() ? "None" : fitnessClass.getEquipment()));
                    System.out.println("Feedback: " + (fitnessClass.getFeedback().isEmpty() ? "No feedback yet" : fitnessClass.getFeedback()));
                    System.out.println("----------------------------------------");
                }
            }
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
    }

    // Display fitness class by ID
    public void displayFitnessClassById(int id) {
        try {
            FitnessClass fitnessClass = fitnessService.getFitnessClass(id);
            System.out.println("Fitness Class ID: " + fitnessClass.getId());
            System.out.println("Name: " + fitnessClass.getName());
            System.out.println("Start time: " + fitnessClass.getStartTime());
            System.out.println("End time: " + fitnessClass.getEndTime());
            System.out.println("Trainer: " + fitnessClass.getTrainer().getName());
            System.out.println("Room: " + fitnessClass.getRoom().getName());
            System.out.println("Participants Count: " + fitnessClass.getParticipantsCount());
            System.out.println("Location: " + fitnessClass.getLocation());
            System.out.println("Associated Equipment: " + (fitnessClass.getEquipment().isEmpty() ? "None" : fitnessClass.getEquipment()));
            System.out.println("Feedback: " + (fitnessClass.getFeedback().isEmpty() ? "No feedback yet" : fitnessClass.getFeedback()));
            System.out.println("----------------------------------------");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    // Add new fitness class
    public void addFitnessClass(String name, LocalDateTime startTime, LocalDateTime endTime, Trainer trainer, Room room, int participantsCount,
                                Location location, List<Feedback> feedback,
                                List<Member> members, List<Equipment> equipment) {
        try {
            fitnessService.addFitnessClass(name, startTime, endTime, trainer, room, participantsCount,
                    location, feedback, members, equipment);
            System.out.println("Fitness class added successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    // Update existing fitness class
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

    // Delete fitness class by ID
    public void deleteFitnessClass(int id) {
        try {
            fitnessService.deleteFitnessClass(id);
            System.out.println("Fitness class deleted successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    // ----- Location -----

    // Display all locations
    public void displayAllLocations() {
        try {
            List<Location> locationList = fitnessService.getAllLocations();
            if (locationList.isEmpty()) {
                System.out.println("No locations available.");
            } else {
                for (Location location : locationList) {
                    System.out.println("Location ID: " + location.getId());
                    System.out.println("Name: " + location.getName());
                    System.out.println("Address: " + location.getAddress());
                    System.out.println("----------------------------------------");
                }
            }
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
    }

    // Display location by ID
    public void displayLocationById(int id) {
        try {
            Location location = fitnessService.getLocation(id);
            System.out.println("Location ID: " + location.getId());
            System.out.println("Name: " + location.getName());
            System.out.println("Address: " + location.getAddress());
            System.out.println("----------------------------------------");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    // Add a new location
    public void addLocation(String name, String address) {
        try {
            fitnessService.addLocation(name, address);
            System.out.println("Location added successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    // Update an existing location
    public void updateLocation(int id, String name, String address) {
        try {
            fitnessService.updateLocation(id, name, address);
            System.out.println("Location updated successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    // Delete a location by ID
    public void deleteLocation(int id) {
        try {
            fitnessService.deleteLocation(id);
            System.out.println("Location deleted successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    // ----- Member -----

    // Display all members
    public void displayAllMembers() {
        try {
            List<Member> memberList = fitnessService.getAllMembers();
            if (memberList.isEmpty()) {
                System.out.println("No members available.");
            } else {
                for (Member member : memberList) {
                    System.out.println("Member ID: " + member.getId());
                    System.out.println("Name: " + member.getName());
                    System.out.println("Email: " + member.getMail());
                    System.out.println("Phone: " + member.getPhone());
                    System.out.println("Registration Date: " + member.getRegistrationDate());
                    System.out.println("Membership Type: " + member.getMembershipType());
                    System.out.println("Associated Fitness Classes: " + (member.getFitnessClasses().isEmpty() ? "None" : member.getFitnessClasses()));
                    System.out.println("----------------------------------------");
                }
            }
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
    }

    // Display member by ID
    public void displayMemberById(int id) {
        try {
            Member member = fitnessService.getMember(id);
            System.out.println("Member ID: " + member.getId());
            System.out.println("Name: " + member.getName());
            System.out.println("Email: " + member.getMail());
            System.out.println("Phone: " + member.getPhone());
            System.out.println("Registration Date: " + member.getRegistrationDate());
            System.out.println("Membership Type: " + member.getMembershipType());
            System.out.println("Associated Fitness Classes: " + (member.getFitnessClasses().isEmpty() ? "None" : member.getFitnessClasses()));
            System.out.println("----------------------------------------");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    // Add a new member
    public void addMember(String name, String mail, String phone, LocalDate registrationDate, String membershipType, List<FitnessClass> fitnessClasses) {
        try {
            fitnessService.addMember(name, mail, phone, registrationDate, membershipType, fitnessClasses);
            System.out.println("Member added successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    // Update an existing member
    public void updateMember(int id, String name, String mail, String phone, LocalDate registrationDate, String membershipType, List<FitnessClass> fitnessClasses) {
        try {
            fitnessService.updateMember(id, name, mail, phone, registrationDate, membershipType, fitnessClasses);
            System.out.println("Member updated successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    // Delete a member by ID
    public void deleteMember(int id) {
        try {
            fitnessService.deleteMember(id);
            System.out.println("Member deleted successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    // ----- Membership -----

    // Display all memberships
    public void displayAllMemberships() {
        try {
            List<Membership> membershipList = fitnessService.getAllMemberships();
            if (membershipList.isEmpty()) {
                System.out.println("No memberships available.");
            } else {
                for (Membership membership : membershipList) {
                    System.out.println("Membership ID: " + membership.getId());
                    System.out.println("Type: " + membership.getType());
                    System.out.println("Price: " + membership.getPrice());
                    System.out.println("Members: " + (membership.getMembers().isEmpty() ? "None" : membership.getMembers()));
                    System.out.println("----------------------------------------");
                }
            }
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
    }

    // Display membership by ID
    public void displayMembershipById(int id) {
        try {
            Membership membership = fitnessService.getMembership(id);
            System.out.println("Membership ID: " + membership.getId());
            System.out.println("Type: " + membership.getType());
            System.out.println("Price: " + membership.getPrice());
            System.out.println("Members: " + (membership.getMembers().isEmpty() ? "None" : membership.getMembers()));
            System.out.println("----------------------------------------");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    // Add a new membership
    public void addMembership(String type, ArrayList<Member> members, float price) {
        try {
            fitnessService.addMembership(type, members, price);
            System.out.println("Membership added successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    // Update an existing membership
    public void updateMembership(int id, String type, ArrayList<Member> members, float price) {
        try {
            fitnessService.updateMembership(id, type, members, price);
            System.out.println("Membership updated successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    // Delete a membership by ID
    public void deleteMembership(int id) {
        try {
            fitnessService.deleteMembership(id);
            System.out.println("Membership deleted successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    // ----- Reservation -----

    // Display all reservations
    public void displayAllReservations() {
        try {
            List<Reservation> reservationList = fitnessService.getAllReservations();
            if (reservationList.isEmpty()) {
                System.out.println("No reservations available.");
            } else {
                for (Reservation reservation : reservationList) {
                    System.out.println("Reservation ID: " + reservation.getId());
                    System.out.println("Member: " + reservation.getMember().getName());
                    System.out.println("Fitness Class: " + reservation.getFitnessClass().getName());
                    System.out.println("Reservation Date: " + reservation.getReservationDate());
                    System.out.println("----------------------------------------");
                }
            }
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
    }

    // Display reservation by ID
    public void displayReservationById(int id) {
        try {
            Reservation reservation = fitnessService.getReservation(id);
            System.out.println("Reservation ID: " + reservation.getId());
            System.out.println("Member: " + reservation.getMember().getName());
            System.out.println("Fitness Class: " + reservation.getFitnessClass().getName());
            System.out.println("Reservation Date: " + reservation.getReservationDate());
            System.out.println("----------------------------------------");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    // Add a new reservation
    public void addReservation(Member member, FitnessClass fitnessClass, LocalDateTime reservationDate) {
        try {
            fitnessService.addReservation(member, fitnessClass, reservationDate);
            System.out.println("Reservation added successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    // Update an existing reservation
    public void updateReservation(int id, Member member, FitnessClass fitnessClass, LocalDateTime reservationDate) {
        try {
            fitnessService.updateReservation(id, member, fitnessClass, reservationDate);
            System.out.println("Reservation updated successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    // Delete a reservation by ID
    public void deleteReservation(int id) {
        try {
            fitnessService.deleteReservation(id);
            System.out.println("Reservation deleted successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    // ----- Room -----

    // Display all rooms
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

    // Display room by ID
    public void displayRoomById(int id) {
        try {
            Room room = fitnessService.getRoom(id);
            System.out.println("Room ID: " + room.getId());
            System.out.println("Room Name: " + room.getName());
            System.out.println("Max Capacity: " + room.getMaxCapacity());
            System.out.println("Location: " + room.getLocation().getName());
            System.out.println("----------------------------------------");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    // Add a new room
    public void addRoom(String name, int maxCapacity, Location location) {
        try {
            fitnessService.addRoom(name, maxCapacity, location);
            System.out.println("Room added successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    // Update an existing room
    public void updateRoom(int id, String name, int maxCapacity, Location location) {
        try {
            fitnessService.updateRoom(id, name, maxCapacity, location);
            System.out.println("Room updated successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    // Delete a room by ID
    public void deleteRoom(int id) {
        try {
            fitnessService.deleteRoom(id);
            System.out.println("Room deleted successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    // ----- Trainer -----

    // Display all trainers
    public void displayAllTrainers() {
        try {
            List<Trainer> trainerList = fitnessService.getAllTrainers();
            if (trainerList.isEmpty()) {
                System.out.println("No trainers available.");
            } else {
                for (Trainer trainer : trainerList) {
                    System.out.println("Trainer ID: " + trainer.getId());
                    System.out.println("Name: " + trainer.getName());
                    System.out.println("Email: " + trainer.getMail());
                    System.out.println("Phone: " + trainer.getPhone());
                    System.out.println("Specialisation: " + trainer.getSpecialisation());
                    System.out.println("----------------------------------------");
                }
            }
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
    }

    // Display trainer by ID
    public void displayTrainerById(int id) {
        try {
            Trainer trainer = fitnessService.getTrainer(id);
            System.out.println("Trainer ID: " + trainer.getId());
            System.out.println("Name: " + trainer.getName());
            System.out.println("Email: " + trainer.getMail());
            System.out.println("Phone: " + trainer.getPhone());
            System.out.println("Specialisation: " + trainer.getSpecialisation());
            System.out.println("----------------------------------------");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    // Add a new trainer
    public void addTrainer(String name, String mail, String phone, String specialisation) {
        try {
            fitnessService.addTrainer(name, mail, phone, specialisation);
            System.out.println("Trainer added successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    // Update an existing trainer
    public void updateTrainer(int id, String name, String mail, String phone, String specialisation) {
        try {
            fitnessService.updateTrainer(id, name, mail, phone, specialisation);
            System.out.println("Trainer updated successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    // Delete a trainer by ID
    public void deleteTrainer(int id) {
        try {
            fitnessService.deleteTrainer(id);
            System.out.println("Trainer deleted successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    // --------------------------------------------------------------------------------------

    // Get upcoming classes of a trainer
    public void getTrainerUpcomingClasses(int id) {
        try {
            List<FitnessClass> upcomingClasses = fitnessService.getTrainerUpcomingClasses(id);
            for (FitnessClass fitnessClass : upcomingClasses) {
                System.out.println(fitnessClass.toString());
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    // get all upcoming classes
    public void getAllUpcomingClasses() {
        try {
            List<FitnessClass> upcomingClasses = fitnessService.getAllUpcomingClasses();
            for (FitnessClass fitnessClass : upcomingClasses) {
                System.out.println(fitnessClass.toStringLessInfo());
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    // Schedule a new fitness class
    public void scheduleNewClass(String className, LocalDateTime startTime, LocalDateTime endTime, int trainerId,
                                 int roomId, int participantsCount, int locationId, List<Equipment> equipment) {
        try{
            fitnessService.scheduleNewClass(className, startTime, endTime, trainerId, roomId, participantsCount,
                    locationId, equipment);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    // View schedule
    public void viewSchedule() {
        fitnessService.viewSchedule();
    }

    // Recommend similar classes
    public void getSimilarClasses(FitnessClass targetClass) {
        try {
            List<FitnessClass> similarClasses = fitnessService.getSimilarClasses(targetClass);
            for (FitnessClass fitnessClass : similarClasses) {
                System.out.println(fitnessClass.toStringLessInfo());
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    // Register a member to a class
    public void registerToClass(int memberId, int classId) {
        try {
            fitnessService.registerToClass(memberId, classId);
            System.out.println("Registration done successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    // Drop a member from a class
    public void dropClass(int memberId, int classId) {
        try {
            fitnessService.dropClass(memberId, classId);
            System.out.println("Class dropped successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    //Dispaly feedback of a class
    public void displayFeedback(int classID){
        try {
            List<Feedback> feedbackList = fitnessService.getClassFeedback(classID);
            for(Feedback feedback: feedbackList){
                System.out.println("Rating: " + feedback.getRating() + "/5 -> " + feedback.getComment());
            }
        } catch (IllegalArgumentException e){
            System.err.println(e.getMessage());
        }
    }

    // Display classes taught by a trainer
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

    public void displayClassesByMember(int memberId) {
        try {
            List<FitnessClass> classes = fitnessService.getClassesByMember(memberId);
            for (FitnessClass fitnessClass : classes) {
                System.out.println(fitnessClass.toStringLessInfo());
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public FitnessClass findClassById(int classId) {
        try {
            return fitnessService.findClassById(classId);
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return null;
    }

}