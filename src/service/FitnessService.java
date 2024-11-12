package service;
import model.*;
import repository.IRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    // Service constructor
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

    //  ----- Equipment -----

    // Get equipment by ID, returning null if it doesn't exist
    public Equipment getEquipment(int equipmentID) {
        return equipmentRepository.read(equipmentID);
    }

    // Add new equipment to the repository if it doesn't already exist
    public void addEquipment(int equipmentID, String name, int quantity, List<FitnessClass> fitnessClasses) {
        Equipment existingEquipment = getEquipment(equipmentID);
        if (existingEquipment != null) {
            throw new IllegalArgumentException("Equipment with ID " + equipmentID + " already exists.");
        }
        Equipment newEquipment = new Equipment(equipmentID, name, quantity, fitnessClasses);
        equipmentRepository.create(newEquipment);
    }

    // Update an existing equipment item
    public void updateEquipment(int equipmentID, String name, int quantity, List<FitnessClass> fitnessClasses) {
        Equipment existingEquipment = getEquipment(equipmentID);
        if (existingEquipment == null) {
            throw new IllegalArgumentException("Equipment with ID " + equipmentID + " doesn't exist.");
        }
        existingEquipment.setName(name);
        existingEquipment.setQuantity(quantity);
        existingEquipment.setFitnessClasses(fitnessClasses);
        equipmentRepository.update(existingEquipment);
    }

    // Delete an equipment item by ID
    public void deleteEquipment(int equipmentID) {
        Equipment existingEquipment = getEquipment(equipmentID);
        if (existingEquipment == null) {
            throw new IllegalArgumentException("Equipment with ID " + equipmentID + " doesn't exist.");
        }
        equipmentRepository.delete(equipmentID);
    }

    // Retrieve all equipment
    public List<Equipment> getAllEquipments(){
        return equipmentRepository.getAll();
    }

    //  ----- Feedback -----

    // Get feedback by ID, returning null if it doesn't exist
    public Feedback getFeedback(int feedbackID) {
        return feedbackRepository.read(feedbackID);
    }

    // Add new feedback to the repository if it doesn't already exist
    public void addFeedback(int feedbackID, Member member, FitnessClass fitnessClass, int rating, String comment) {
        Feedback existingFeedback = getFeedback(feedbackID);
        if (existingFeedback != null) {
            throw new IllegalArgumentException("Feedback with ID " + feedbackID + " already exists.");
        }
        Feedback newFeedback = new Feedback(feedbackID, member, fitnessClass, rating, comment);
        feedbackRepository.create(newFeedback);
    }

    // Update an existing feedback item
    public void updateFeedback(int feedbackID, Member member, FitnessClass fitnessClass, int rating, String comment) {
        Feedback existingFeedback = getFeedback(feedbackID);
        if (existingFeedback == null) {
            throw new IllegalArgumentException("Feedback with ID " + feedbackID + " doesn't exist.");
        }
        existingFeedback.setMember(member);
        existingFeedback.setFitnessClass(fitnessClass);
        existingFeedback.setRating(rating);
        existingFeedback.setComment(comment);
        feedbackRepository.update(existingFeedback);
    }

    // Delete a feedback item by ID
    public void deleteFeedback(int feedbackID) {
        Feedback existingFeedback = getFeedback(feedbackID);
        if (existingFeedback == null) {
            throw new IllegalArgumentException("Feedback with ID " + feedbackID + " doesn't exist.");
        }
        feedbackRepository.delete(feedbackID);
    }

    // Retrieve all feedbacks
    public List<Feedback> getAllFeedbacks(){
        return feedbackRepository.getAll();
    }

    //  ----- FitnessClass -----

    // Retrieve a fitness class by its ID
    public FitnessClass getFitnessClass(int fitnessClassID) {
        return fitnessClassRepository.read(fitnessClassID);
    }

    // Add a new fitness class to the repository
    public void addFitnessClass(int fitnessClassID, String name, int duration, Trainer trainer, Room room,
                                int participantsCount, Schedule schedule, Location location,
                                List<Feedback> feedback, List<Member> members, List<Equipment> equipment) {
        FitnessClass existingClass = getFitnessClass(fitnessClassID);
        if (existingClass != null) {
            throw new IllegalArgumentException("Class with ID " + fitnessClassID + " already exists.");
        }
        FitnessClass newFitnessClass = new FitnessClass(fitnessClassID, name, duration, trainer, room,
                participantsCount, schedule, location, feedback,
                members, equipment);
        fitnessClassRepository.create(newFitnessClass);
    }

    // Update an existing fitness class
    public void updateFitnessClass(int fitnessClassID, String name, int duration, Trainer trainer, Room room,
                                   int participantsCount, Schedule schedule, Location location,
                                   List<Feedback> feedback, List<Member> members, List<Equipment> equipment) {
        FitnessClass existingFitnessClass = getFitnessClass(fitnessClassID);
        if (existingFitnessClass == null) {
            throw new IllegalArgumentException("Fitness class with ID " + fitnessClassID + " does not exist.");
        }
        existingFitnessClass.setName(name);
        existingFitnessClass.setDuration(duration);
        existingFitnessClass.setTrainer(trainer);
        existingFitnessClass.setRoom(room);
        existingFitnessClass.setParticipantsCount(participantsCount);
        existingFitnessClass.setSchedule(schedule);
        existingFitnessClass.setLocation(location);
        existingFitnessClass.setFeedback(feedback);
        existingFitnessClass.setEquipment(equipment);
        existingFitnessClass.setMembers(members);

        fitnessClassRepository.update(existingFitnessClass);
    }

    // Delete a fitness class by its ID
    public void deleteFitnessClass(int fitnessClassID) {
        FitnessClass existingFitnessClass = getFitnessClass(fitnessClassID);
        if (existingFitnessClass == null) {
            throw new IllegalArgumentException("Fitness class with ID " + fitnessClassID + " does not exist.");
        }
        fitnessClassRepository.delete(fitnessClassID);
    }

    // Retrieve all fitness classes
    public List<FitnessClass> getAllFitnessClasses() {
        return fitnessClassRepository.getAll();
    }

    //  ----- Location -----

    // Get location by ID, returning null if it doesn't exist
    public Location getLocation(int locationID) {
        return locationRepository.read(locationID);
    }

    // Add new location to the repository if it doesn't already exist
    public void addLocation(int locationID, String name, String address) {
        Location existingLocation = getLocation(locationID);
        if (existingLocation != null) {
            throw new IllegalArgumentException("Location with ID " + locationID + " already exists.");
        }
        Location newLocation = new Location(locationID, name, address);
        locationRepository.create(newLocation);
    }

    // Update an existing location item
    public void updateLocation(int locationID, String name, String address) {
        Location existingLocation = getLocation(locationID);
        if (existingLocation == null) {
            throw new IllegalArgumentException("Location with ID " + locationID + " doesn't exist.");
        }
        existingLocation.setName(name);
        existingLocation.setAdress(address);
        locationRepository.update(existingLocation);
    }

    // Delete a location item by ID
    public void deleteLocation(int locationID) {
        Location existingLocation = getLocation(locationID);
        if (existingLocation == null) {
            throw new IllegalArgumentException("Location with ID " + locationID + " doesn't exist.");
        }
        locationRepository.delete(locationID);
    }

    // Retrieve all locations
    public List<Location> getAllLocations(){
        return locationRepository.getAll();
    }

    //  ----- Member -----

    // Get member by ID, returning null if it doesn't exist
    public Member getMember(int memberID) {
        return memberRepository.read(memberID);
    }

    // Add new member to the repository if it doesn't already exist
    public void addMember(int memberID, String name, String mail, String phone, LocalDate registrationDate, String membershipType, List<FitnessClass> fitnessClasses) {
        Member existingMember = getMember(memberID);
        if (existingMember != null) {
            throw new IllegalArgumentException("Member with ID " + memberID + " already exists.");
        }
        Member newMember = new Member(name, mail, phone, memberID, registrationDate, membershipType, fitnessClasses);
        memberRepository.create(newMember);
    }

    // Update an existing member
    public void updateMember(int memberID, String name, String mail, String phone, LocalDate registrationDate, String membershipType, List<FitnessClass> fitnessClasses) {
        Member existingMember = getMember(memberID);
        if (existingMember == null) {
            throw new IllegalArgumentException("Member with ID " + memberID + " doesn't exist.");
        }
        existingMember.setName(name);
        existingMember.setMail(mail);
        existingMember.setPhone(phone);
        existingMember.setRegistrationDate(registrationDate);
        existingMember.setMembershipType(membershipType);
        existingMember.setFitnessClasses(fitnessClasses);
        memberRepository.update(existingMember);
    }

    // Delete a member by ID
    public void deleteMember(int memberID) {
        Member existingMember = getMember(memberID);
        if (existingMember == null) {
            throw new IllegalArgumentException("Member with ID " + memberID + " doesn't exist.");
        }
        memberRepository.delete(memberID);
    }

    // Retrieve all members
    public List<Member> getAllMembers() {
        return memberRepository.getAll();
    }

    //  ----- Membership -----

    // Get membership by ID, returning null if it doesn't exist
    public Membership getMembership(int membershipID) {
        return membershipRepository.read(membershipID);
    }

    // Add a new membership to the repository if it doesn't already exist
    public void addMembership(int membershipID, String type, List<Member> members, float price) {
        Membership existingMembership = getMembership(membershipID);
        if (existingMembership != null) {
            throw new IllegalArgumentException("Membership with ID " + membershipID + " already exists.");
        }
        Membership newMembership = new Membership(membershipID, type, members, price);
        membershipRepository.create(newMembership);
    }

    // Update an existing membership
    public void updateMembership(int membershipID, String type, List<Member> members, float price) {
        Membership existingMembership = getMembership(membershipID);
        if (existingMembership == null) {
            throw new IllegalArgumentException("Membership with ID " + membershipID + " doesn't exist.");
        }
        existingMembership.setType(type);
        existingMembership.setMembers(members);
        existingMembership.setPrice(price);
        membershipRepository.update(existingMembership);
    }

    // Delete a membership by ID
    public void deleteMembership(int membershipID) {
        Membership existingMembership = getMembership(membershipID);
        if (existingMembership == null) {
            throw new IllegalArgumentException("Membership with ID " + membershipID + " doesn't exist.");
        }
        membershipRepository.delete(membershipID);
    }

    // Retrieve all memberships
    public List<Membership> getAllMemberships() {
        return membershipRepository.getAll();
    }

    //  ----- Reservation -----

    // Get reservation by ID, returning null if it doesn't exist
    public Reservation getReservation(int reservationID) {
        return reservationRepository.read(reservationID);
    }

    // Add a new reservation to the repository if it doesn't already exist
    public void addReservation(int reservationID, Member member, FitnessClass fitnessClass, LocalDateTime reservationDate) {
        Reservation existingReservation = getReservation(reservationID);
        if (existingReservation != null) {
            throw new IllegalArgumentException("Reservation with ID " + reservationID + " already exists.");
        }
        Reservation newReservation = new Reservation(reservationID, member, fitnessClass, reservationDate);
        reservationRepository.create(newReservation);
    }

    // Update an existing reservation
    public void updateReservation(int reservationID, Member member, FitnessClass fitnessClass, LocalDateTime reservationDate) {
        Reservation existingReservation = getReservation(reservationID);
        if (existingReservation == null) {
            throw new IllegalArgumentException("Reservation with ID " + reservationID + " doesn't exist.");
        }
        existingReservation.setMember(member);
        existingReservation.setFitnessClass(fitnessClass);
        existingReservation.setReservationDate(reservationDate);
        reservationRepository.update(existingReservation);
    }

    // Delete a reservation by ID
    public void deleteReservation(int reservationID) {
        Reservation existingReservation = getReservation(reservationID);
        if (existingReservation == null) {
            throw new IllegalArgumentException("Reservation with ID " + reservationID + " doesn't exist.");
        }
        reservationRepository.delete(reservationID);
    }

    // Retrieve all reservations
    public List<Reservation> getAllReservations(){
        return reservationRepository.getAll();
    }

    //  ----- Room -----

    // Get room by ID, returning null if it doesn't exist
    public Room getRoom(int roomID) {
        return roomRepository.read(roomID);
    }

    // Add a new room to the repository if it doesn't already exist
    public void addRoom(int roomID, String name, int maxCapacity, Location location) {
        Room existingRoom = getRoom(roomID);
        if (existingRoom != null) {
            throw new IllegalArgumentException("Room with ID " + roomID + " already exists.");
        }
        Room newRoom = new Room(roomID, name, maxCapacity, location);
        roomRepository.create(newRoom);
    }

    // Update an existing room
    public void updateRoom(int roomID, String name, int maxCapacity, Location location) {
        Room existingRoom = getRoom(roomID);
        if (existingRoom == null) {
            throw new IllegalArgumentException("Room with ID " + roomID + " doesn't exist.");
        }
        existingRoom.setName(name);
        existingRoom.setMaxCapacity(maxCapacity);
        existingRoom.setLocation(location);
        roomRepository.update(existingRoom);
    }

    // Delete a room by ID
    public void deleteRoom(int roomID) {
        Room existingRoom = getRoom(roomID);
        if (existingRoom == null) {
            throw new IllegalArgumentException("Room with ID " + roomID + " doesn't exist.");
        }
        roomRepository.delete(roomID);
    }

    // Retrieve all rooms
    public List<Room> getAllRooms(){
        return roomRepository.getAll();
    }

    //  ----- Schedule -----

    // Get schedule by ID, returning null if it doesn't exist
    public Schedule getSchedule(int scheduleID) {
        return scheduleRepository.read(scheduleID);
    }

    // Add a new schedule to the repository if it doesn't already exist
    public void addSchedule(int scheduleID, FitnessClass fitnessClass, LocalDateTime startTime, LocalDateTime endTime) {
        Schedule existingSchedule = getSchedule(scheduleID);
        if (existingSchedule != null) {
            throw new IllegalArgumentException("Schedule with ID " + scheduleID + " already exists.");
        }
        Schedule newSchedule = new Schedule(scheduleID, fitnessClass, startTime, endTime);
        scheduleRepository.create(newSchedule);
    }

    // Update an existing schedule
    public void updateSchedule(int scheduleID, FitnessClass fitnessClass, LocalDateTime startTime, LocalDateTime endTime) {
        Schedule existingSchedule = getSchedule(scheduleID);
        if (existingSchedule == null) {
            throw new IllegalArgumentException("Schedule with ID " + scheduleID + " doesn't exist.");
        }
        existingSchedule.setFitnessClass(fitnessClass);
        existingSchedule.setStartTime(startTime);
        existingSchedule.setEndTime(endTime);
        scheduleRepository.update(existingSchedule);
    }

    // Delete a schedule by ID
    public void deleteSchedule(int scheduleID) {
        Schedule existingSchedule = getSchedule(scheduleID);
        if (existingSchedule == null) {
            throw new IllegalArgumentException("Schedule with ID " + scheduleID + " doesn't exist.");
        }
        scheduleRepository.delete(scheduleID);
    }

    // Retrieve all schedules
    public List<Schedule> getAllSchedules(){
        return scheduleRepository.getAll();
    }

    //  ----- Trainer -----

    // Get trainer by ID, returning null if it doesn't exist
    public Trainer getTrainer(int trainerID) {
        return trainerRepository.read(trainerID);
    }

    // Add a new trainer to the repository if it doesn't already exist
    public void addTrainer(int trainerID, String name, String mail, String phone, String specialisation) {
        Trainer existingTrainer = getTrainer(trainerID);
        if (existingTrainer != null) {
            throw new IllegalArgumentException("Trainer with ID " + trainerID + " already exists.");
        }
        Trainer newTrainer = new Trainer(name, mail, phone, trainerID, specialisation);
        trainerRepository.create(newTrainer);
    }

    // Update an existing trainer
    public void updateTrainer(int trainerID, String name, String mail, String phone, String specialisation) {
        Trainer existingTrainer = getTrainer(trainerID);
        if (existingTrainer == null) {
            throw new IllegalArgumentException("Trainer with ID " + trainerID + " doesn't exist.");
        }
        existingTrainer.setName(name);
        existingTrainer.setMail(mail);
        existingTrainer.setPhone(phone);
        existingTrainer.setSpecialisation(specialisation);
        trainerRepository.update(existingTrainer);
    }

    // Delete a trainer by ID
    public void deleteTrainer(int trainerID) {
        Trainer existingTrainer = getTrainer(trainerID);
        if (existingTrainer == null) {
            throw new IllegalArgumentException("Trainer with ID " + trainerID + " doesn't exist.");
        }
        trainerRepository.delete(trainerID);
    }

    // Retrieve all trainers
    public List<Trainer> getAllTrainers(){
        return trainerRepository.getAll();
    }

    //  ----- Attendance -----

    // Get attendance by memberID and classID
    public Attendance getAttendance(int memberID, int classID) {
        for (Attendance attendance : attendanceRepository.getAll()) {
            if (attendance.getMemberID() == memberID && attendance.getClassID() == classID) {
                return attendance;
            }
        }
        return null;
    }

    // Add a new attendance record if it doesn't already exist
    public void addAttendance(int memberID, int classID, LocalDateTime reservationDate) {
        Attendance existingAttendance = getAttendance(memberID, classID);
        if (existingAttendance != null) {
            throw new IllegalArgumentException("Attendance for member " + memberID + " in class " + classID + " already exists.");
        }
        Attendance newAttendance = new Attendance(memberID, classID, reservationDate);
        attendanceRepository.create(newAttendance);
    }

    // Update an existing attendance record
    public void updateAttendance(int memberID, int classID, LocalDateTime reservationDate) {
        Attendance existingAttendance = getAttendance(memberID, classID);
        if (existingAttendance == null) {
            throw new IllegalArgumentException("Attendance for member " + memberID + " in class " + classID + " does not exist.");
        }
        existingAttendance.setReservationDate(reservationDate);
        attendanceRepository.update(existingAttendance);
    }

    // Delete an attendance record by memberID and classID
    public void deleteAttendance(int memberID, int classID) {
        Attendance existingAttendance = getAttendance(memberID, classID);
        if (existingAttendance == null) {
            throw new IllegalArgumentException("Attendance for member " + memberID + " in class " + classID + " does not exist.");
        }
        attendanceRepository.delete(existingAttendance.getMemberID());  // Assuming memberID is unique here
    }

    // Retrieve all attendance records
    public List<Attendance> getAllAttendances() {
        return attendanceRepository.getAll();
    }

    //  ----- FitnessClass Equipment -----

    // Get FitnessClassEquipment by equipmentID and classID
    public FitnessClassEquipment getFitnessClassEquipment(int equipmentID, int classID) {
        for (FitnessClassEquipment equipment : fitnessClassEquipmentRepository.getAll()) {
            if (equipment.getEquipmentID() == equipmentID && equipment.getClassID() == classID) {
                return equipment;
            }
        }
        return null;
    }

    // Add a new fitness class equipment record if it doesn't already exist
    public void addFitnessClassEquipment(int equipmentID, int classID, int quantity) {
        FitnessClassEquipment existingEquipment = getFitnessClassEquipment(equipmentID, classID);
        if (existingEquipment != null) {
            throw new IllegalArgumentException("Fitness class equipment for class " + classID + " and equipment " + equipmentID + " already exists.");
        }
        FitnessClassEquipment newFitnessClassEquipment = new FitnessClassEquipment(equipmentID, classID, quantity);
        fitnessClassEquipmentRepository.create(newFitnessClassEquipment);
    }

    // Update quantity of an existing fitness class equipment
    public void updateFitnessClassEquipmentQuantity(int equipmentID, int classID, int quantity) {
        FitnessClassEquipment existingFitnessClassEquipment = getFitnessClassEquipment(equipmentID, classID);
        if (existingFitnessClassEquipment == null) {
            throw new IllegalArgumentException("Fitness class equipment with Equipment ID " + equipmentID + " does not exist.");
        }
        existingFitnessClassEquipment.setQuantity(quantity);
        fitnessClassEquipmentRepository.update(existingFitnessClassEquipment);
    }

    // Delete a fitness class equipment record by equipmentID and classID
    public void deleteFitnessClassEquipment(int equipmentID, int classID) {
        FitnessClassEquipment existingEquipment = getFitnessClassEquipment(equipmentID, classID);
        if (existingEquipment == null) {
            throw new IllegalArgumentException("Fitness class equipment for class " + classID + " and equipment " + equipmentID + " does not exist.");
        }
        fitnessClassEquipmentRepository.delete(existingEquipment.getEquipmentID());
    }

    // Retrieve all fitness class equipment
    public List<FitnessClassEquipment> getAllFitnessClassEquipments() {
        return fitnessClassEquipmentRepository.getAll();
    }

//    //  ----- Complex Methods -----
//
//    // Helper method to retrieve equipment list for a given fitness class
//    private List<FitnessClassEquipment> getEquipmentForFitnessClass(int classID) {
//        List<FitnessClassEquipment> classEquipmentList = new ArrayList<>();
//        for (FitnessClassEquipment equipment : fitnessClassEquipmentRepository.getAll()) {
//            if (equipment.getClassID() == classID) {
//                classEquipmentList.add(equipment);
//            }
//        }
//        return classEquipmentList;
//    }
//
//    // Helper method to check for common equipment between classes
//    private boolean hasCommonEquipment( int classID, List<FitnessClassEquipment> targetEquipmentList) {
//        List<FitnessClassEquipment> classEquipmentList = getEquipmentForFitnessClass(classID);
//        for (FitnessClassEquipment targetEquipment : targetEquipmentList) {
//            for (FitnessClassEquipment equipment : classEquipmentList) {
//                if (equipment.getEquipmentID() == targetEquipment.getEquipmentID()) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//    // Complex method, returns similar classes based on trainer and equipment
//    public List<FitnessClass> getSimilarFitnessClasses( int fitnessClassID ) {
//        //get the target fitness class based on the provided ID
//        FitnessClass targetClass = getFitnessClass(fitnessClassID);
//        if ( targetClass == null ) {
//            throw new IllegalArgumentException("Fitness class with ID" + fitnessClassID + " doesn't exist.");
//        }
//        List<FitnessClass> similarClasses = new ArrayList<>();
//        //get equipment list and trainer for the target class
//        Trainer targetTrainer = targetClass.getTrainer();
//        List<FitnessClassEquipment> targetEquipmentList = getEquipmentForFitnessClass(fitnessClassID);
//        //iterate through all fitness classes
//        for(FitnessClass fitnessClass : getAllFitnessClasses()) {
//            //skip if it's the same class as the target
//            if( fitnessClass.equals(targetClass )) {
//                continue;
//            }
//            //check if the trainer matches
//            boolean sameTrainer = fitnessClass.getTrainer().equals(targetTrainer);
//            //check if there is any common equipment
//            boolean commonEquipment = hasCommonEquipment(fitnessClass.getFitnessClassID(), targetEquipmentList);
//            //add to similar classes list if trainer or equipment match
//            if( sameTrainer || commonEquipment) {
//                similarClasses.add(fitnessClass);
//            }
//        }
//        return similarClasses;
//    }
//
//    // Helper method to check if there is a scheduling conflict for a room
//    private boolean isRoomScheduleConflict(int roomID, LocalDateTime newStartTime, LocalDateTime newEndTime, int currentScheduleID) {
//        List<Schedule> allSchedules = scheduleRepository.getAll();
//        for(Schedule schedule : allSchedules) {
//            //check if the schedule is in the same room, and it is not the current schedule (to avoid self-conflict)
//            if(schedule.getFitnessClass().getRoom().getRoomID() == roomID && schedule.getScheduleID() != currentScheduleID) {
//                LocalDateTime existingStartTime = schedule.getStartTime();
//                LocalDateTime existingEndTime = schedule.getEndTime();
//                //check if the new time overlaps with the existing schedule
//                if((newStartTime.isBefore(existingEndTime) && newEndTime.isAfter(existingStartTime))) {
//                    return true;    //conflict found
//                }
//            }
//        }
//        return false;   // no conflict
//    }
//
//    // Complex method, reschedule a fitness class
//    public void rescheduleClass(int fitnessClassID, LocalDateTime newStartTime, LocalDateTime newEndTime) {
//        //find the fitness class
//        FitnessClass fitnessClass = fitnessClassRepository.read(fitnessClassID);
//        if(fitnessClass == null) {
//            throw new IllegalArgumentException("Fitness class with ID " + fitnessClassID + " does not exist.");
//        }
//        //get the room and schedule associated with the fitness class
//        Room room = fitnessClass.getRoom();
//        Schedule currentSchedule = fitnessClass.getSchedule();
//        //check for scheduling conflicts in the room
//        if(isRoomScheduleConflict(room.getRoomID(), newStartTime, newEndTime, currentSchedule.getScheduleID())) {
//            throw  new IllegalArgumentException("The new schedule conflicts with anther class in the same room.");
//        }
//        //update the schedule
//        currentSchedule.setStartTime(newStartTime);
//        currentSchedule.setEndTime(newEndTime);
//        //save the updated schedule
//        scheduleRepository.update(currentSchedule);
//    }
//
//    // ------------------------------------------------------------------------------
//
//    // This method displays the courses allocated to a trainer
//    public List<Map<String, Object>> getTrainerCourses(int trainerID) {
//        // Find the trainer by ID
//        Trainer trainer = trainerRepository.read(trainerID);
//        if (trainer == null) {
//            throw new IllegalArgumentException("Trainer with ID " + trainerID + " not found.");
//        }
//        // Get all fitness classes from the repository
//        List<FitnessClass> allFitnessClasses = fitnessClassRepository.getAll();
//        List<Map<String, Object>> courseDetails = new ArrayList<>();
//        // Loop through each fitness class to find those that belong to the trainer
//        for (FitnessClass fitnessClass : allFitnessClasses) {
//            // Check if the trainer of the fitness class matches the provided trainerID
//            if (fitnessClass.getTrainer().getTrainerID() == trainerID) {
//                // Get the associated room for the fitness class
//                Room room = fitnessClass.getRoom();
//                Location location = null;
//                if (room != null) {
//                    location = room.getLocation(); // Get the location of the room if room is available
//                }
//                // Create a map with the necessary information about the fitness class
//                Map<String, Object> course = new HashMap<>();
//                course.put("name", fitnessClass.getName());
//                course.put("duration", fitnessClass.getDuration());
//                // If room is available, add its name, otherwise add "N/A"
//                if (room != null) {
//                    course.put("room", room.getName());
//                } else {
//                    course.put("room", "N/A");
//                }
//                // If location is available, add its string representation, otherwise add "N/A"
//                if (location != null) {
//                    course.put("location", location.toString());
//                } else {
//                    course.put("location", "N/A");
//                }
//                // Add the number of participants in the course
//                course.put("participants", fitnessClass.getParticipantsCount());
//                // Add the start and end time of the course
//                course.put("startTime", fitnessClass.getStartTime());
//                course.put("endTime", fitnessClass.getEndTime());
//                // Add the course details map to the list
//                courseDetails.add(course);
//            }
//        }
//        // Return the list of course details for the given trainer
//        return courseDetails;
//    }
//
    // Method to get available classes for a trainer
    public List<FitnessClass> getTrainerAvailableClasses(int trainerID) {
        List<FitnessClass> availableClasses = new ArrayList<>();
        List<FitnessClass> allClasses = fitnessClassRepository.getAll(); // Get all fitness classes
        // Loop through all classes to find the ones for the given trainer and not yet started
        for (FitnessClass fitnessClass : allClasses) {
            // Check if the class is managed by the trainer and hasn't started yet
            if (fitnessClass.getTrainer().getID() == trainerID) {
                // Make sure the schedule is not null and the start time is after the current time
                if (fitnessClass.getSchedule() != null && fitnessClass.getSchedule().getStartTime().isAfter(LocalDateTime.now())) {
                    availableClasses.add(fitnessClass);
                }
            }
        }
        return availableClasses;
    }


}
