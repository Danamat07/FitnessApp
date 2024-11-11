package controller;
import model.*;
import service.FitnessService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class FitnessController {

    private final FitnessService fitnessService;

    // Controller constructor
    public FitnessController(FitnessService fitnessService) {
        this.fitnessService = fitnessService;
    }

    // ----- Equipment -----

    // Add new equipment
    public String addEquipment(int equipmentID, String name, int quantity, List<FitnessClass> fitnessClasses) {
        try {
            fitnessService.addEquipment(equipmentID, name, quantity, fitnessClasses);
            return "Equipment added successfully.";
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    // Update existing equipment
    public String updateEquipment(int equipmentID, String name, int quantity, List<FitnessClass> fitnessClasses) {
        try {
            fitnessService.updateEquipment(equipmentID, name, quantity, fitnessClasses);
            return "Equipment updated successfully.";
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    // Delete equipment by ID
    public String deleteEquipment(int equipmentID) {
        try {
            fitnessService.deleteEquipment(equipmentID);
            return "Equipment deleted successfully.";
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    // Display all equipment
    public void displayAllEquipment() {
        List<Equipment> equipmentList = fitnessService.getAllEquipments();
        if (equipmentList.isEmpty()) {
            System.out.println("No equipment found.");
        } else {
            for (Equipment equipment : equipmentList) {
                System.out.println("Equipment ID: " + equipment.getEquipmentID());
                System.out.println("Name: " + equipment.getName());
                System.out.println("Quantity: " + equipment.getQuantity());
                System.out.println("Associated Fitness Classes: " + (equipment.getFitnessClasses().isEmpty() ? "None" : equipment.getFitnessClasses()));
                System.out.println("----------------------------------------");
            }
        }
    }

    // ----- Feedback -----

    // Add new feedback
    public String addFeedback(int feedbackID, Member member, FitnessClass fitnessClass, int rating, String comment) {
        try {
            fitnessService.addFeedback(feedbackID, member, fitnessClass, rating, comment);
            return "Feedback added successfully.";
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    // Update existing feedback
    public String updateFeedback(int feedbackID, Member member, FitnessClass fitnessClass, int rating, String comment) {
        try {
            fitnessService.updateFeedback(feedbackID, member, fitnessClass, rating, comment);
            return "Feedback updated successfully.";
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    // Delete feedback by ID
    public String deleteFeedback(int feedbackID) {
        try {
            fitnessService.deleteFeedback(feedbackID);
            return "Feedback deleted successfully.";
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    // Display all feedbacks
    public void displayAllFeedbacks() {
        List<Feedback> feedbackList = fitnessService.getAllFeedbacks();
        if (feedbackList.isEmpty()) {
            System.out.println("No feedback found.");
        } else {
            for (Feedback feedback : feedbackList) {
                System.out.println("Feedback ID: " + feedback.getFeedbackID());
                System.out.println("Member: " + feedback.getMember().getName());
                System.out.println("Fitness Class: " + feedback.getFitnessClass().getName());
                System.out.println("Rating: " + feedback.getRating());
                System.out.println("Comment: " + feedback.getComment());
                System.out.println("----------------------------------------");
            }
        }
    }

    // ----- FitnessClass -----

    // Add a new fitness class
    public String addFitnessClass(int fitnessClassID, String name, int duration, Trainer trainer, Room room,
                                  int participantsCount, Schedule schedule, Location location,
                                  List<Feedback> feedback, List<Member> members, List<Equipment> equipment) {
        try {
            fitnessService.addFitnessClass(fitnessClassID, name, duration, trainer, room, participantsCount,
                    schedule, location, feedback, members, equipment);
            return "Fitness class added successfully.";
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    // Update an existing fitness class
    public String updateFitnessClass(int fitnessClassID, String name, int duration, Trainer trainer, Room room,
                                     int participantsCount, Schedule schedule, Location location,
                                     List<Feedback> feedback, List<Member> members, List<Equipment> equipment) {
        try {
            fitnessService.updateFitnessClass(fitnessClassID, name, duration, trainer, room, participantsCount,
                    schedule, location, feedback, members, equipment);
            return "Fitness class updated successfully.";
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    // Delete a fitness class by ID
    public String deleteFitnessClass(int fitnessClassID) {
        try {
            fitnessService.deleteFitnessClass(fitnessClassID);
            return "Fitness class deleted successfully.";
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    // Display all fitness classes
    public void displayAllFitnessClasses() {
        List<FitnessClass> fitnessClassList = fitnessService.getAllFitnessClasses();
        if (fitnessClassList.isEmpty()) {
            System.out.println("No fitness classes found.");
        } else {
            for (FitnessClass fitnessClass : fitnessClassList) {
                System.out.println("Fitness Class ID: " + fitnessClass.getFitnessClassID());
                System.out.println("Name: " + fitnessClass.getName());
                System.out.println("Duration: " + fitnessClass.getDuration() + " minutes");
                System.out.println("Trainer: " + fitnessClass.getTrainer().getName());
                System.out.println("Room: " + fitnessClass.getRoom().getName());
                System.out.println("Participants Count: " + fitnessClass.getParticipantsCount());
                System.out.println("Location: " + (fitnessClass.getLocation() != null ? fitnessClass.getLocation().toString() : "N/A"));
                System.out.println("----------------------------------------");
            }
        }
    }

    // ----- Location -----

    // Get location by ID
    public Location getLocation(int locationID) {
        Location location = fitnessService.getLocation(locationID);
        if (location == null) {
            System.out.println("Location not found with ID: " + locationID);
            return null;
        }
        return location;
    }

    // Add a new location
    public String addLocation(int locationID, String name, String address) {
        try {
            fitnessService.addLocation(locationID, name, address);
            return "Location added successfully.";
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    // Update an existing location
    public String updateLocation(int locationID, String name, String address) {
        try {
            fitnessService.updateLocation(locationID, name, address);
            return "Location updated successfully.";
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    // Delete a location
    public String deleteLocation(int locationID) {
        try {
            fitnessService.deleteLocation(locationID);
            return "Feedback deleted successfully.";
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    // Display all locations
    public void displayAllLocations() {
        List<Location> locations = fitnessService.getAllLocations(); // Get all locations
        if (locations == null || locations.isEmpty()) {
            System.out.println("No locations found.");
        } else {
            for (Location location : locations) {
                System.out.println("Location ID: " + location.getLocationID());
                System.out.println("Name: " + location.getName());
                System.out.println("Address: " + location.getAdress());
                System.out.println("--------------------------------------");
            }
        }
    }

    // ----- Member -----

    // Get member by ID
    public Member getMember(int memberID) {
        Member member = fitnessService.getMember(memberID);
        if (member == null) {
            System.out.println("Member not found with ID: " + memberID);
            return null;
        }
        return member;
    }

    // Add a new member
    public String addMember(int memberID, String name, String mail, String phone, LocalDate registrationDate, String membershipType, List<FitnessClass> fitnessClasses) {
        try {
            fitnessService.addMember(memberID, name, mail, phone, registrationDate, membershipType, fitnessClasses);
            return "Member added successfully.";
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    // Update an existing member
    public String updateMember(int memberID, String name, String mail, String phone, LocalDate registrationDate, String membershipType, List<FitnessClass> fitnessClasses) {
        try {
            fitnessService.updateMember(memberID, name, mail, phone, registrationDate, membershipType, fitnessClasses);
            return "Member updated successfully.";
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    // Delete a member
    public String deleteMember(int memberID) {
        try {
            fitnessService.deleteMember(memberID);
            return "Member deleted successfully.";
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    // Display all members
    public void displayAllMembers() {
        List<Member> members = fitnessService.getAllMembers(); // Get all members
        if (members == null || members.isEmpty()) {
            System.out.println("No members found.");
        } else {
            for (Member member : members) {
                System.out.println("Member ID: " + member.getMemberID());
                System.out.println("Name: " + member.getName());
                System.out.println("Email: " + member.getMail());
                System.out.println("Phone: " + member.getPhone());
                System.out.println("Registration Date: " + member.getRegistrationDate());
                System.out.println("Membership Type: " + member.getMembershipType());
                System.out.println("Fitness Classes: " + member.getFitnessClasses());
                System.out.println("--------------------------------------");
            }
        }
    }

    // ----- Membership -----

    // Get membership by ID
    public Membership getMembership(int membershipID) {
        Membership membership = fitnessService.getMembership(membershipID);
        if (membership == null) {
            System.out.println("Membership not found with ID: " + membershipID);
            return null;
        }
        return membership;
    }

    // Add a new membership
    public String addMembership(int membershipID, String type, List<Member> members, float price) {
        try {
            fitnessService.addMembership(membershipID, type, members, price);
            return "Membership added successfully.";
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    // Update an existing membership
    public String updateMembership(int membershipID, String type, List<Member> members, float price) {
        try {
            fitnessService.updateMembership(membershipID, type, members, price);
            return "Membership updated successfully.";
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    // Delete a membership
    public String deleteMembership(int membershipID) {
        try {
            fitnessService.deleteMembership(membershipID);
            return "Membership deleted successfully.";
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    // Display all memberships
    public void displayAllMemberships() {
        List<Membership> memberships = fitnessService.getAllMemberships(); // Get all memberships
        if (memberships == null || memberships.isEmpty()) {
            System.out.println("No memberships found.");
        } else {
            for (Membership membership : memberships) {
                System.out.println("Membership ID: " + membership.getMembershipID());
                System.out.println("Type: " + membership.getType());
                System.out.println("Members: " + membership.getMembers());
                System.out.println("Price: " + membership.getPrice());
                System.out.println("--------------------------------------");
            }
        }
    }

    // ----- Reservation -----

    // Get reservation by ID
    public Reservation getReservation(int reservationID) {
        Reservation reservation = fitnessService.getReservation(reservationID);
        if (reservation == null) {
            System.out.println("Reservation not found with ID: " + reservationID);
            return null;
        }
        return reservation;
    }

    // Add a new reservation
    public String addReservation(int reservationID, Member member, FitnessClass fitnessClass, LocalDateTime reservationDate) {
        try {
            fitnessService.addReservation(reservationID, member, fitnessClass, reservationDate);
            return "Reservation added successfully.";
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    // Update an existing reservation
    public String updateReservation(int reservationID, Member member, FitnessClass fitnessClass, LocalDateTime reservationDate) {
        try {
            fitnessService.updateReservation(reservationID, member, fitnessClass, reservationDate);
            return "Reservation updated successfully.";
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    // Delete a reservation
    public String deleteReservation(int reservationID) {
        try {
            fitnessService.deleteReservation(reservationID);
            return "Reservation deleted successfully.";
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    // Display all reservations
    public void displayAllReservations() {
        List<Reservation> reservations = fitnessService.getAllReservations(); // Get all reservations
        if (reservations == null || reservations.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            for (Reservation reservation : reservations) {
                System.out.println("Reservation ID: " + reservation.getReservationID());
                System.out.println("Member: " + reservation.getMember().getName());
                System.out.println("Fitness Class: " + reservation.getFitnessClass().getName());
                System.out.println("Reservation Date: " + reservation.getReservationDate());
                System.out.println("--------------------------------------");
            }
        }
    }

    // ----- Room -----

    // Get room by ID
    public Room getRoom(int roomID) {
        Room room = fitnessService.getRoom(roomID);
        if (room == null) {
            System.out.println("Room not found with ID: " + roomID);
            return null;
        }
        return room;
    }

    // Add a new room
    public String addRoom(int roomID, String name, int maxCapacity, Location location) {
        try {
            fitnessService.addRoom(roomID, name, maxCapacity, location);
            return "Room added successfully.";
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    // Update an existing room
    public String updateRoom(int roomID, String name, int maxCapacity, Location location) {
        try {
            fitnessService.updateRoom(roomID, name, maxCapacity, location);
            return "Room updated successfully.";
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    // Delete a room
    public String deleteRoom(int roomID) {
        try {
            fitnessService.deleteRoom(roomID);
            return "Room deleted successfully.";
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    // Display all rooms
    public void displayAllRooms() {
        List<Room> rooms = fitnessService.getAllRooms(); // Get all rooms
        if (rooms == null || rooms.isEmpty()) {
            System.out.println("No rooms found.");
        } else {
            for (Room room : rooms) {
                System.out.println("Room ID: " + room.getRoomID());
                System.out.println("Name: " + room.getName());
                System.out.println("Max Capacity: " + room.getMaxCapacity());
                System.out.println("Location: " + room.getLocation().getName() + " - " + room.getLocation().getAdress());
                System.out.println("--------------------------------------");
            }
        }
    }

    // ----- Schedule -----

    // Get schedule by ID
    public Schedule getSchedule(int scheduleID) {
        Schedule schedule = fitnessService.getSchedule(scheduleID);
        if (schedule == null) {
            System.out.println("Schedule not found with ID: " + scheduleID);
            return null;
        }
        return schedule;
    }

    // Add a new schedule
    public String addSchedule(int scheduleID, FitnessClass fitnessClass, LocalDateTime startTime, LocalDateTime endTime) {
        try {
            fitnessService.addSchedule(scheduleID, fitnessClass, startTime, endTime);
            return "Schedule added successfully.";
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    // Update an existing schedule
    public String updateSchedule(int scheduleID, FitnessClass fitnessClass, LocalDateTime startTime, LocalDateTime endTime) {
        try {
            fitnessService.updateSchedule(scheduleID, fitnessClass, startTime, endTime);
            return "Schedule updated successfully.";
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    // Delete a schedule
    public String deleteSchedule(int scheduleID) {
        try {
            fitnessService.deleteSchedule(scheduleID);
            return "Schedule deleted successfully.";
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    // Display all schedules
    public void displayAllSchedules() {
        List<Schedule> schedules = fitnessService.getAllSchedules(); // Get all schedules
        if (schedules == null || schedules.isEmpty()) {
            System.out.println("No schedules found.");
        } else {
            for (Schedule schedule : schedules) {
                System.out.println("Schedule ID: " + schedule.getScheduleID());
                System.out.println("Fitness Class: " + schedule.getFitnessClass().getName());
                System.out.println("Start Time: " + schedule.getStartTime());
                System.out.println("End Time: " + schedule.getEndTime());
                System.out.println("--------------------------------------");
            }
        }
    }

    // ---- Trainer -----

    // Get trainer by ID
    public Trainer getTrainer(int trainerID) {
        Trainer trainer = fitnessService.getTrainer(trainerID);
        if (trainer == null) {
            System.out.println("Trainer not found with ID: " + trainerID);
            return null;
        }
        return trainer;
    }

    // Add a new trainer
    public String addTrainer(int trainerID, String name, String mail, String phone, String specialisation) {
        try {
            fitnessService.addTrainer(trainerID, name, mail, phone, specialisation);
            return "Trainer added successfully.";
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    // Update an existing trainer
    public String updateTrainer(int trainerID, String name, String mail, String phone, String specialisation) {
        try {
            fitnessService.updateTrainer(trainerID, name, mail, phone, specialisation);
            return "Trainer updated successfully.";
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    // Delete a trainer
    public String deleteTrainer(int trainerID) {
        try {
            fitnessService.deleteTrainer(trainerID);
            return "Trainer deleted successfully.";
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    // Display all trainers
    public void displayAllTrainers() {
        List<Trainer> trainers = fitnessService.getAllTrainers(); // Get all trainers
        if (trainers == null || trainers.isEmpty()) {
            System.out.println("No trainers found.");
        } else {
            for (Trainer trainer : trainers) {
                System.out.println("Trainer ID: " + trainer.getTrainerID());
                System.out.println("Name: " + trainer.getName());
                System.out.println("Mail: " + trainer.getMail());
                System.out.println("Phone: " + trainer.getPhone());
                System.out.println("Specialisation: " + trainer.getSpecialisation());
                System.out.println("--------------------------------------");
            }
        }
    }

    // ----- Attendance -----

    // Get attendance by memberID and classID
    public Attendance getAttendance(int memberID, int classID) {
        Attendance attendance = fitnessService.getAttendance(memberID, classID);
        if (attendance == null) {
            System.out.println("Attendance not found for member " + memberID + " in class " + classID);
            return null;
        }
        return attendance;
    }

    // Add a new attendance record
    public String addAttendance(int memberID, int classID, LocalDateTime reservationDate) {
        try {
            fitnessService.addAttendance(memberID, classID, reservationDate);
            return "Attendance added successfully.";
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    // Update an existing attendance record
    public String updateAttendance(int memberID, int classID, LocalDateTime reservationDate) {
        try {
            fitnessService.updateAttendance(memberID, classID, reservationDate);
            return "Attendance updated successfully.";
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    // Delete an attendance record
    public String deleteAttendance(int memberID, int classID) {
        try {
            fitnessService.deleteAttendance(memberID, classID);
            return "Attendance deleted successfully.";
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    // Display all attendance records
    public void displayAllAttendances() {
        List<Attendance> attendances = fitnessService.getAllAttendances(); // Get all attendance records
        if (attendances == null || attendances.isEmpty()) {
            System.out.println("No attendance records found.");
        } else {
            for (Attendance attendance : attendances) {
                System.out.println("Member ID: " + attendance.getMemberID());
                System.out.println("Class ID: " + attendance.getClassID());
                System.out.println("Reservation Date: " + attendance.getReservationDate());
                System.out.println("--------------------------------------");
            }
        }
    }

    // ----- FitnessClass Equipment ----

    // Get FitnessClassEquipment by equipmentID and classID
    public FitnessClassEquipment getFitnessClassEquipment(int equipmentID, int classID) {
        FitnessClassEquipment equipment = fitnessService.getFitnessClassEquipment(equipmentID, classID);
        if (equipment == null) {
            System.out.println("FitnessClassEquipment not found for Equipment ID " + equipmentID + " and Class ID " + classID);
            return null;
        }
        return equipment;
    }

    // Add a new fitness class equipment record
    public String addFitnessClassEquipment(int equipmentID, int classID, int quantity) {
        try {
            fitnessService.addFitnessClassEquipment(equipmentID, classID, quantity);
            return "Fitness class equipment added successfully.";
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    // Update quantity of an existing fitness class equipment
    public String updateFitnessClassEquipmentQuantity(int equipmentID, int classID, int quantity) {
        try {
            fitnessService.updateFitnessClassEquipmentQuantity(equipmentID, classID, quantity);
            return "Fitness class equipment quantity updated successfully.";
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    // Delete a fitness class equipment record
    public String deleteFitnessClassEquipment(int equipmentID, int classID) {
        try {
            fitnessService.deleteFitnessClassEquipment(equipmentID, classID);
            return "Fitness class equipment deleted successfully.";
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    // Display all fitness class equipment records
    public void displayAllFitnessClassEquipments() {
        List<FitnessClassEquipment> fitnessClassEquipments = fitnessService.getAllFitnessClassEquipments();
        if (fitnessClassEquipments == null || fitnessClassEquipments.isEmpty()) {
            System.out.println("No fitness class equipment found.");
        } else {
            for (FitnessClassEquipment equipment : fitnessClassEquipments) {
                System.out.println("Equipment ID: " + equipment.getEquipmentID());
                System.out.println("Class ID: " + equipment.getClassID());
                System.out.println("Quantity: " + equipment.getQuantity());
                System.out.println("--------------------------------------");
            }
        }
    }
}
