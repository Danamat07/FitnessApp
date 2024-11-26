package app;

import model.*;
import controller.*;
import repository.FileRepository;
import repository.InMemoryRepository;
import service.FitnessService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The UI (User Interface) class is responsible for handling user input and output for fitness-related operations.
 * It interacts with the `FitnessController` to perform actions related to managing members, trainers, fitness classes,
 * schedules, and other related entities. The `UI` class acts as the interface between the user and the system's underlying
 * functionality provided by the `FitnessController` and `FitnessService` layers.
 * This class provides a basic text-based interface, typically used in console applications, for the user to interact
 * with the fitness management system.
 * <p>Primary responsibilities:</p>
 * <ul>
 *     <li>Prompting the user for input to manage members, classes, trainers, etc.</li>
 *     <li>Displaying relevant information, such as lists of members, trainers, or classes.</li>
 *     <li>Interacting with the `FitnessController` to perform actions based on user input (e.g., register members to classes).</li>
 * </ul>
 */
public class UI {

    private final FitnessController fitnessController;
    private final ArrayList<Member> fitnessmembers;
    private final ArrayList<Trainer> fitnesstraines;

    /**
     * Constructor to initialize the UI with necessary dependencies.
     * @param fitnessController The controller object that manages interactions with the service layer.
     * @param fitnessmembers The list of fitness members, representing the users interacting with the system.
     * @param fitnesstraines The list of trainers available for fitness classes.
     */
    public UI(FitnessController fitnessController, ArrayList<Member> fitnessmembers, ArrayList<Trainer> fitnesstraines) {
        this.fitnessController = fitnessController;
        this.fitnessmembers = fitnessmembers;
        this.fitnesstraines = fitnesstraines;
    }

    public void trainerUI(int id) {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning){
            System.out.println("==== Menu ====");
            System.out.println("1. View my classes");
            System.out.println("2. Schedule a new class");
            System.out.println("3. View feedback");
            System.out.println("4. Logout");
            System.out.println("Enter your choice (1/2/3 or 4): ");

            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice){
                case 1 -> {
                    System.out.println("------------------------------------------ ");
                    fitnessController.getTrainerUpcomingClasses(id);
                    System.out.println("------------------------------------------ ");
                }
                case 2 -> {
                    System.out.println("------------------------------------------ ");
                    fitnessController.getAllUpcomingClasses();
                    System.out.println("Enter class name: ");
                    String className = scanner.nextLine();
                    System.out.println("Enter start time (yyyy-MM-ddTHH:mm): ");
                    LocalDateTime startTime = LocalDateTime.parse(scanner.nextLine());
                    System.out.println("Enter end time (yyyy-MM-ddTHH:mm): ");
                    LocalDateTime endTime = LocalDateTime.parse(scanner.nextLine());
                    System.out.println("--->These are your rooms options: ");
                    fitnessController.displayAllRooms();
                    System.out.println("Enter room by id: ");
                    int roomId = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter max participants: ");
                    int participantsCount = Integer.parseInt(scanner.nextLine());
                    System.out.println("--->These are your location options: ");
                    fitnessController.displayAllLocations();
                    System.out.println("Enter location by id: ");
                    int locationId = Integer.parseInt(scanner.nextLine());
                    System.out.println("--->These are your equipment options: ");
                    fitnessController.displayAllEquipment();
                    System.out.println("Enter equipment by id: ");
                    int equipmentId = Integer.parseInt(scanner.nextLine());
                    List<Equipment> equipmentList = new ArrayList<>();
                    equipmentList.add(fitnessController.getEquipment(equipmentId));
                    fitnessController.scheduleNewClass(className,startTime,endTime,id,roomId,participantsCount,locationId,equipmentList);
                    System.out.println("Fitness class scheduled successfully! ");
                    System.out.println("------------------------------------------ ");

                }
                case 3 -> {
                    System.out.println("------------------------------------------ ");
                    fitnessController.displayClassesOfTrainer(id);
                    System.out.println("\n");
                    System.out.println("Choose a class to view feedback for (by ID): ");
                    int classId = Integer.parseInt(scanner.nextLine());
                    fitnessController.displayFeedback(classId);
                    System.out.println("------------------------------------------ ");
                }
                case 4 -> {
                    System.out.println("------------------------------------------ ");
                    System.out.println("Logging out...");
                    isRunning = false;
                }
            }
        }
    }

    public void memberUI(int id) {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning){
            System.out.println("==== Menu ====");
            System.out.println("1. View upcoming classes");
            System.out.println("2. Register to class");
            System.out.println("3. Drop class");
            System.out.println("4. View classes you participated in");
            System.out.println("5. Get similar classes");
            System.out.println("6. Logout");
            System.out.println("Enter your choice (1/2/3 or 4): ");

            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice){
                case 1 -> {
                    System.out.println("------------------------------------------ ");
                    fitnessController.viewSchedule(id);
                    System.out.println("------------------------------------------ ");
                }
                case 2 -> {
                    System.out.println("------------------------------------------ ");
                    fitnessController.viewSchedule(id);
                    System.out.println("Which class would you like to book? (enter class ID): ");
                    int classId = Integer.parseInt(scanner.nextLine());
                    fitnessController.registerToClass(id, classId);
                    System.out.println("------------------------------------------ ");
                }
                case 3 -> {
                    System.out.println("------------------------------------------ ");
                    fitnessController.viewSchedule(id);
                    System.out.println("Which class would you like to drop? (enter class ID): ");
                    int classId = Integer.parseInt(scanner.nextLine());
                    fitnessController.dropClass(id, classId);
                    System.out.println("------------------------------------------ ");
                }
                case 4 -> {
                    System.out.println("------------------------------------------ ");
                    fitnessController.displayClassesByMember(id);
                    System.out.println("------------------------------------------ ");
                }
                case 5 -> {
                    System.out.println("------------------------------------------ ");
                    fitnessController.displayClassesByMember(id);
                    System.out.println("Choose a class to get a recommendations for similar classes (enter class ID): ");
                    int classId = Integer.parseInt(scanner.nextLine());
                    FitnessClass targetClass = fitnessController.findClassById(classId);
                    fitnessController.getSimilarClasses(targetClass);
                    System.out.println("------------------------------------------ ");
                }
                case 6 -> {
                    System.out.println("------------------------------------------ ");
                    System.out.println("Logging out...");
                    isRunning = false;
                }
            }
        }
    }

    public void login() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Fitness center!");
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("Please enter your login info:");
            System.out.println("Full name: ");
            String userName = scanner.nextLine();
            System.out.println("Mail: ");
            String userEmail = scanner.nextLine();
            System.out.println("Phone number: ");
            String userPhoneNumber = scanner.nextLine();

            // Try to find member or trainer
            Member member = findMemberByCredentials(fitnessmembers, userName, userEmail, userPhoneNumber);
            Trainer trainer = findTrainerByCredentials(fitnesstraines, userName, userEmail, userPhoneNumber);

            // Check login results and redirect to appropriate UI
            if (member != null) {
                System.out.println("Welcome, " + member.getName() + "!");
                int memberID = member.getId();
                memberUI(memberID);
                isRunning = false; // Exit after successful login
            } else if (trainer != null) {
                System.out.println("Welcome, " + trainer.getName() + "!");
                int trainerID = trainer.getId();
                trainerUI(trainerID);
                isRunning = false; // Exit after successful login
            } else {
                // If no matching member or trainer is found
                System.out.println("No user found with the given credentials.");
                System.out.println("Do you want to try again? (y/n): ");
                String tryAgain = scanner.nextLine();
                if (!tryAgain.equalsIgnoreCase("y")) {
                    isRunning = false; // Exit if user doesn't want to try again
                }
            }
        }

        // Close the scanner resource when done
        scanner.close();
    }

    // Filer functions -> identify user (member or trainer)

    // Method to find member by name, email, and phone
    public static Member findMemberByCredentials(ArrayList<Member> members, String name, String email, String phone) {
        for (Member member : members) {
            if (member.getName().equalsIgnoreCase(name) &&
                    member.getMail().equalsIgnoreCase(email) &&
                    member.getPhone().equals(phone)) {
                return member;
            }
        }
        return null;
    }

    // Method to find trainer by name, email, and phone
    public static Trainer findTrainerByCredentials(ArrayList<Trainer> trainers, String name, String email, String phone) {
        for (Trainer trainer : trainers) {
            if (trainer.getName().equalsIgnoreCase(name) &&
                    trainer.getMail().equalsIgnoreCase(email) &&
                    trainer.getPhone().equals(phone)) {
                return trainer;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        // loading data

        // trainers
        Trainer trainer1 = new Trainer("Sabin Matei", "sabinmatei@gmail.com", "0746912336", "weight lifting, cardio");
        Trainer trainer2 = new Trainer("Tudor Ratiu", "tudorratiu@gmail.com", "0733912375", "cardio, weight lifting, kickboxing");
        Trainer trainer3 = new Trainer("Rusai Denis", "rusaidenis@gmail.com", "0734119845", "kickboxing, weight lifting");
        Trainer trainer4 = new Trainer("Ana Popescu", "anapopescu@gmail.com", "0734829450", "yoga, pilates");
        Trainer trainer5 = new Trainer("Alina Carcea", "alinacarcea@gmail.com", "0745012367", "pilates, yoga");
        trainer1.setId(1);
        trainer2.setId(2);
        trainer3.setId(3);
        trainer4.setId(4);
        trainer5.setId(5);

        ArrayList<Trainer> trainersList = new ArrayList<>();
        trainersList.add(trainer1);
        trainersList.add(trainer2);
        trainersList.add(trainer3);
        trainersList.add(trainer4);
        trainersList.add(trainer5);

        // locations
        Location location1 = new Location("Revo Fit", "Str. Manastur nr.24");
        Location location2 = new Location("Revo Star", "Str. Buna Ziua nr.102");
        location1.setId(1);
        location2.setId(2);

        ArrayList<Location> locationsList = new ArrayList<>();
        locationsList.add(location1);
        locationsList.add(location2);

        // rooms
        Room room1 = new Room("Room 1", 30, location1);
        Room room2 = new Room("Room 2", 25, location2);
        Room room3 = new Room("Room 3", 18, location2);
        Room room4 = new Room("Room 4", 29, location1);
        Room room5 = new Room("Room 5", 31, location1);
        room1.setId(1);
        room2.setId(2);
        room3.setId(3);
        room4.setId(4);
        room5.setId(5);

        ArrayList<Room> roomList = new ArrayList<>();
        roomList.add(room1);
        roomList.add(room2);
        roomList.add(room3);
        roomList.add(room4);
        roomList.add(room5);

        // members
        ArrayList<FitnessClass> member1Classes = new ArrayList<>();
        Member member1 = new Member("Hrihor Mihaela", "mihaelahrihor@gmail.com", "0758909520", LocalDate.ofYearDay(2024, 12), "Premium", member1Classes);
        member1.setId(1);

        ArrayList<FitnessClass> member2Classes = new ArrayList<>();
        Member member2 = new Member("Matei Dana", "mateidana@gmail.com", "0771367002", LocalDate.ofYearDay(2024, 67), "Student", member2Classes);
        member2.setId(2);

        ArrayList<FitnessClass> member3Classes = new ArrayList<>();
        Member member3 = new Member("Mitri Miruna", "mitrimiruna@gmail.com", "0758867145", LocalDate.ofYearDay(2023, 47), "Basic", member3Classes);
        member3.setId(3);

        ArrayList<FitnessClass> member4Classes = new ArrayList<>();
        Member member4 = new Member("Berceaengels Kathrin", "kathrinberceaengels@gmail.com", "0745602416", LocalDate.ofYearDay(2020, 101), "Premium", member4Classes);
        member4.setId(4);

        ArrayList<FitnessClass> member5Classes = new ArrayList<>();
        Member member5 = new Member("Bleoca Cristiana", "cristianableoca@gmail.com", "0790143267", LocalDate.ofYearDay(2023, 258), "Student", member5Classes);
        member5.setId(5);

        ArrayList<Member> membersList = new ArrayList<>();
        membersList.add(member1);
        membersList.add(member2);
        membersList.add(member3);
        membersList.add(member4);
        membersList.add(member5);

        // memberships
        ArrayList<Member> studentMembershipList = new ArrayList<>();
        studentMembershipList.add(member2);
        studentMembershipList.add(member5);

        Membership studentMembership = new Membership("Student", studentMembershipList, 150);
        studentMembership.setId(1);

        ArrayList<Member> premiumMembershipList = new ArrayList<>();
        premiumMembershipList.add(member1);
        premiumMembershipList.add(member4);

        Membership premiumMembership = new Membership("Premium", premiumMembershipList, 250);
        premiumMembership.setId(2);

        ArrayList<Member> basicMembershipList = new ArrayList<>();
        basicMembershipList.add(member3);

        Membership basicMembership = new Membership("Basic", basicMembershipList, 200);
        basicMembership.setId(3);

        ArrayList<Membership> membershipList = new ArrayList<>();
        membershipList.add(basicMembership);
        membershipList.add(premiumMembership);
        membershipList.add(studentMembership);

        // equipments
        ArrayList<FitnessClass> classesWithWeights = new ArrayList<>();
        Equipment weights = new Equipment("weights", 50, classesWithWeights);
        weights.setId(1);

        ArrayList<FitnessClass> classesWithMattresses = new ArrayList<>();
        Equipment mattresses = new Equipment("mattresses", 30, classesWithMattresses);
        mattresses.setId(2);

        ArrayList<FitnessClass> classesWithTreadmills = new ArrayList<>();
        Equipment treadmill = new Equipment("treadmill", 10, classesWithTreadmills);
        treadmill.setId(3);

        ArrayList<FitnessClass> classesWithLegPresses = new ArrayList<>();
        Equipment legPress = new Equipment("leg press", 5, classesWithLegPresses);
        legPress.setId(4);

        ArrayList<FitnessClass> classesWithFoamRollers = new ArrayList<>();
        Equipment foamRoller = new Equipment("foam roller", 10, classesWithFoamRollers);
        foamRoller.setId(5);

        ArrayList<FitnessClass> classesWithResistenceBands = new ArrayList<>();
        Equipment resistanceBands = new Equipment("resistance bands", 12, classesWithResistenceBands);
        resistanceBands.setId(6);

        ArrayList<FitnessClass> classesWithPilatesRings = new ArrayList<>();
        Equipment pilatesRing = new Equipment("pilates ring", 8, classesWithPilatesRings);
        pilatesRing.setId(7);

        ArrayList<FitnessClass> classesWithYogaBlocks = new ArrayList<>();
        Equipment yogaBlocks = new Equipment("yoga blocks", 40, classesWithYogaBlocks);
        yogaBlocks.setId(8);

        ArrayList<FitnessClass> classesWithJumpRopes = new ArrayList<>();
        Equipment jumpRope = new Equipment("jump rope", 25, classesWithJumpRopes);
        jumpRope.setId(9);

        ArrayList<FitnessClass> classesWithAirBikes = new ArrayList<>();
        Equipment airBike = new Equipment("air bike", 10, classesWithAirBikes);
        airBike.setId(10);

        ArrayList<FitnessClass> classesWithBoxingGloves = new ArrayList<>();
        Equipment boxingGloves = new Equipment("boxing gloves", 10, classesWithBoxingGloves);
        boxingGloves.setId(11);

        ArrayList<FitnessClass> classesWithHeadgears = new ArrayList<>();
        Equipment headgear = new Equipment("headgear", 10, classesWithHeadgears);
        headgear.setId(12);

        ArrayList<FitnessClass> classesWithPunchingBags = new ArrayList<>();
        Equipment punchingBags = new Equipment("punching bags", 10, classesWithPunchingBags);
        punchingBags.setId(13);

        ArrayList<Equipment> equipmentList = new ArrayList<>();
        equipmentList.add(weights);
        equipmentList.add(mattresses);
        equipmentList.add(treadmill);
        equipmentList.add(legPress);
        equipmentList.add(foamRoller);
        equipmentList.add(resistanceBands);
        equipmentList.add(pilatesRing);
        equipmentList.add(yogaBlocks);
        equipmentList.add(jumpRope);
        equipmentList.add(airBike);
        equipmentList.add(boxingGloves);
        equipmentList.add(headgear);
        equipmentList.add(punchingBags);

        ArrayList<Equipment> yogaEquipment = new ArrayList<>();
        yogaEquipment.add(mattresses);
        yogaEquipment.add(foamRoller);
        yogaEquipment.add(resistanceBands);
        yogaEquipment.add(yogaBlocks);

        ArrayList<Equipment> pilatesEquipment = new ArrayList<>();
        pilatesEquipment.add(mattresses);
        pilatesEquipment.add(foamRoller);
        pilatesEquipment.add(resistanceBands);
        pilatesEquipment.add(pilatesRing);

        ArrayList<Equipment> boxEquipment = new ArrayList<>();
        boxEquipment.add(boxingGloves);
        boxEquipment.add(punchingBags);
        boxEquipment.add(headgear);
        boxEquipment.add(weights);

        ArrayList<Equipment> cardioEquipment = new ArrayList<>();
        cardioEquipment.add(jumpRope);
        cardioEquipment.add(treadmill);
        cardioEquipment.add(airBike);

        ArrayList<Equipment> weightLiftingEquipment = new ArrayList<>();
        weightLiftingEquipment.add(weights);
        weightLiftingEquipment.add(legPress);
        weightLiftingEquipment.add(airBike);

        // fitness classes
        ArrayList<Feedback> feedbackListForClass1 = new ArrayList<>();
        ArrayList<Member> membersForClass1 = new ArrayList<>();
        membersForClass1.add(member2);
        membersForClass1.add(member3);
        membersForClass1.add(member5);
        FitnessClass class1 = new FitnessClass("yoga", LocalDateTime.of(2024, 4, 3, 10, 30), LocalDateTime.of(2024, 4, 3, 12, 30), trainer4, room1, 20, room1.getLocation(), feedbackListForClass1, membersForClass1, yogaEquipment);
        class1.setId(1);
        member2Classes.add(class1);
        member3Classes.add(class1);
        member5Classes.add(class1);

        ArrayList<Feedback> feedbackListForClass2 = new ArrayList<>();
        ArrayList<Member> membersForClass2 = new ArrayList<>();
        membersForClass2.add(member2);
        membersForClass2.add(member3);
        membersForClass2.add(member1);
        FitnessClass class2 = new FitnessClass("yoga", LocalDateTime.of(2024, 7, 3, 13, 0), LocalDateTime.of(2024, 7, 3, 15, 0), trainer5, room2, 20, room2.getLocation(), feedbackListForClass2, membersForClass2, yogaEquipment);
        class2.setId(2);
        member1Classes.add(class2);
        member2Classes.add(class2);
        member3Classes.add(class2);

        ArrayList<Feedback> feedbackListForClass3 = new ArrayList<>();
        ArrayList<Member> membersForClass3 = new ArrayList<>();
        membersForClass3.add(member1);
        membersForClass3.add(member3);
        membersForClass3.add(member4);
        FitnessClass class3 = new FitnessClass("yoga", LocalDateTime.of(2024, 12, 5, 10, 0), LocalDateTime.of(2024, 12, 5, 12, 0), trainer5, room1, 20, room1.getLocation(), feedbackListForClass3, membersForClass3, yogaEquipment);
        class3.setId(3);
        member1Classes.add(class3);
        member3Classes.add(class3);
        member4Classes.add(class3);

        ArrayList<Feedback> feedbackListForClass4 = new ArrayList<>();
        ArrayList<Member> membersForClass4 = new ArrayList<>();
        membersForClass4.add(member1);
        membersForClass4.add(member5);
        membersForClass4.add(member2);
        FitnessClass class4 = new FitnessClass("pilates", LocalDateTime.of(2024, 7, 5, 11, 30), LocalDateTime.of(2024, 7, 5, 13, 0), trainer4, room3, 20, room3.getLocation(), feedbackListForClass4, membersForClass4, pilatesEquipment);
        class4.setId(4);
        member1Classes.add(class4);
        member2Classes.add(class4);
        member5Classes.add(class4);

        ArrayList<Feedback> feedbackListForClass5 = new ArrayList<>();
        ArrayList<Member> membersForClass5 = new ArrayList<>();
        membersForClass5.add(member1);
        membersForClass5.add(member5);
        membersForClass5.add(member2);
        membersForClass5.add(member4);
        FitnessClass class5 = new FitnessClass("pilates", LocalDateTime.of(2024, 10, 10, 10, 30), LocalDateTime.of(2024, 10, 10, 12, 30), trainer4, room3, 20, room3.getLocation(), feedbackListForClass5, membersForClass5, pilatesEquipment);
        class5.setId(5);
        member1Classes.add(class5);
        member2Classes.add(class5);
        member4Classes.add(class5);
        member5Classes.add(class5);

        ArrayList<Feedback> feedbackListForClass6 = new ArrayList<>();
        ArrayList<Member> membersForClass6 = new ArrayList<>();
        membersForClass6.add(member1);
        membersForClass6.add(member2);
        membersForClass6.add(member3);
        membersForClass6.add(member4);
        FitnessClass class6 = new FitnessClass("pilates", LocalDateTime.of(2024, 12, 11, 10, 30), LocalDateTime.of(2024, 12, 11, 12, 30), trainer5, room4, 20, room4.getLocation(), feedbackListForClass6, membersForClass6, pilatesEquipment);
        class6.setId(6);
        member1Classes.add(class6);
        member2Classes.add(class6);
        member3Classes.add(class6);
        member4Classes.add(class6);

        ArrayList<Feedback> feedbackListForClass7 = new ArrayList<>();
        ArrayList<Member> membersForClass7 = new ArrayList<>();
        membersForClass7.add(member1);
        membersForClass7.add(member5);
        membersForClass7.add(member4);
        FitnessClass class7 = new FitnessClass("kickboxing", LocalDateTime.of(2024, 4, 20, 17, 30), LocalDateTime.of(2024, 4, 20, 19, 30), trainer2, room5, 20, room5.getLocation(), feedbackListForClass7, membersForClass7, boxEquipment);
        class7.setId(7);
        member1Classes.add(class7);
        member4Classes.add(class7);
        member5Classes.add(class7);

        ArrayList<Feedback> feedbackListForClass8 = new ArrayList<>();
        ArrayList<Member> membersForClass8 = new ArrayList<>();
        membersForClass8.add(member1);
        membersForClass8.add(member5);
        membersForClass8.add(member2);
        FitnessClass class8 = new FitnessClass("kickboxing", LocalDateTime.of(2024, 8, 20, 17, 30), LocalDateTime.of(2024, 8, 20, 19, 30), trainer3, room2, 20, room2.getLocation(), feedbackListForClass8, membersForClass8, boxEquipment);
        class8.setId(8);
        member1Classes.add(class8);
        member2Classes.add(class8);
        member5Classes.add(class8);

        ArrayList<Feedback> feedbackListForClass9 = new ArrayList<>();
        ArrayList<Member> membersForClass9 = new ArrayList<>();
        membersForClass9.add(member3);
        membersForClass9.add(member4);
        membersForClass9.add(member2);
        FitnessClass class9 = new FitnessClass("kickboxing", LocalDateTime.of(2024, 12, 22, 15, 30), LocalDateTime.of(2024, 12, 22, 17, 30), trainer2, room4, 20, room4.getLocation(), feedbackListForClass9, membersForClass9, boxEquipment);
        class9.setId(9);
        member2Classes.add(class9);
        member3Classes.add(class9);
        member4Classes.add(class9);

        ArrayList<Feedback> feedbackListForClass10 = new ArrayList<>();
        ArrayList<Member> membersForClass10 = new ArrayList<>();
        membersForClass10.add(member3);
        membersForClass10.add(member4);
        membersForClass10.add(member2);
        membersForClass10.add(member1);
        FitnessClass class10 = new FitnessClass("cardio", LocalDateTime.of(2024, 9, 21, 13, 30), LocalDateTime.of(2024, 9, 21, 15, 30), trainer1, room4, 20, room4.getLocation(), feedbackListForClass10, membersForClass10, cardioEquipment);
        class10.setId(10);
        member1Classes.add(class10);
        member2Classes.add(class10);
        member3Classes.add(class10);
        member4Classes.add(class10);

        ArrayList<Feedback> feedbackListForClass11 = new ArrayList<>();
        ArrayList<Member> membersForClass11 = new ArrayList<>();
        membersForClass11.add(member4);
        membersForClass11.add(member5);
        membersForClass11.add(member1);
        FitnessClass class11 = new FitnessClass("cardio", LocalDateTime.of(2024, 1, 1, 12, 30), LocalDateTime.of(2024, 11, 1, 14, 30), trainer2, room3, 20, room3.getLocation(), feedbackListForClass11, membersForClass11, cardioEquipment);
        class11.setId(11);
        member1Classes.add(class11);
        member4Classes.add(class11);
        member5Classes.add(class11);

        ArrayList<Feedback> feedbackListForClass12 = new ArrayList<>();
        ArrayList<Member> membersForClass12 = new ArrayList<>();
        membersForClass12.add(member2);
        membersForClass12.add(member3);
        membersForClass12.add(member1);
        FitnessClass class12 = new FitnessClass("cardio", LocalDateTime.of(2024, 12, 12, 12, 30), LocalDateTime.of(2024, 12, 12, 14, 30), trainer1, room5, 20, room5.getLocation(), feedbackListForClass12, membersForClass12, cardioEquipment);
        class12.setId(12);
        member1Classes.add(class12);
        member2Classes.add(class12);
        member3Classes.add(class12);

        ArrayList<Feedback> feedbackListForClass13 = new ArrayList<>();
        ArrayList<Member> membersForClass13 = new ArrayList<>();
        membersForClass13.add(member4);
        membersForClass13.add(member5);
        membersForClass13.add(member1);
        FitnessClass class13 = new FitnessClass("weight lifting", LocalDateTime.of(2024, 10, 12, 10, 30), LocalDateTime.of(2024, 10, 12, 12, 30), trainer1, room1, 20, room1.getLocation(), feedbackListForClass13, membersForClass13, weightLiftingEquipment);
        class13.setId(13);
        member1Classes.add(class13);
        member4Classes.add(class13);
        member5Classes.add(class13);

        ArrayList<Feedback> feedbackListForClass14 = new ArrayList<>();
        ArrayList<Member> membersForClass14 = new ArrayList<>();
        membersForClass14.add(member4);
        membersForClass14.add(member3);
        membersForClass14.add(member2);
        FitnessClass class14 = new FitnessClass("weight lifting", LocalDateTime.of(2024, 11, 2, 10, 30), LocalDateTime.of(2024, 11, 2, 12, 30), trainer2, room4, 20, room4.getLocation(), feedbackListForClass14, membersForClass14, weightLiftingEquipment);
        class14.setId(14);
        member2Classes.add(class14);
        member3Classes.add(class14);
        member4Classes.add(class14);

        ArrayList<Feedback> feedbackListForClass15 = new ArrayList<>();
        ArrayList<Member> membersForClass15 = new ArrayList<>();
        membersForClass15.add(member4);
        membersForClass15.add(member1);
        membersForClass15.add(member2);
        membersForClass15.add(member5);
        FitnessClass class15 = new FitnessClass("weight lifting", LocalDateTime.of(2024, 12, 20, 10, 30), LocalDateTime.of(2024, 12, 20, 12, 30), trainer3, room3, 20, room3.getLocation(), feedbackListForClass15, membersForClass15, weightLiftingEquipment);
        class15.setId(15);
        member1Classes.add(class15);
        member2Classes.add(class15);
        member4Classes.add(class15);
        member5Classes.add(class15);

        // feedbacks
        Feedback feedback1 = new Feedback(member2, class1, 5, "Nice energy, I really enjoyed this class!");
        feedback1.setId(1);
        feedbackListForClass1.add(feedback1);
        Feedback feedback2 = new Feedback(member3, class2, 4, "Great class!");
        feedback2.setId(2);
        feedbackListForClass2.add(feedback2);
        Feedback feedback3 = new Feedback(member4, class3, 4, "I'll definitely come again!");
        feedback3.setId(3);
        feedbackListForClass3.add(feedback3);
        Feedback feedback4 = new Feedback(member1, class4, 3, "Niceeee!!");
        feedback4.setId(4);
        feedbackListForClass4.add(feedback4);
        Feedback feedback5 = new Feedback(member4, class5, 5, "10/10");
        feedback5.setId(5);
        feedbackListForClass5.add(feedback5);
        Feedback feedback6 = new Feedback(member1, class6, 5, "10/10");
        feedback6.setId(6);
        feedbackListForClass6.add(feedback6);
        Feedback feedback7 = new Feedback(member5, class7, 3, "It was fine..");
        feedback7.setId(7);
        feedbackListForClass7.add(feedback7);
        Feedback feedback8 = new Feedback(member1, class8, 5, "Had a great time, I'll definitely come again!");
        feedback8.setId(8);
        feedbackListForClass8.add(feedback8);
        Feedback feedback9 = new Feedback(member2, class9, 4, "Great class!");
        feedback9.setId(9);
        feedbackListForClass9.add(feedback9);
        Feedback feedback10 = new Feedback(member3, class10, 5, "Amasingggg!!");
        feedback10.setId(10);
        feedbackListForClass10.add(feedback10);
        Feedback feedback11 = new Feedback(member4, class11, 3, "It was exhausting..");
        feedback11.setId(11);
        feedbackListForClass11.add(feedback11);
        Feedback feedback12 = new Feedback(member1, class12, 5, "10/10");
        feedback12.setId(12);
        feedbackListForClass12.add(feedback12);
        Feedback feedback13 = new Feedback(member5, class13, 2, "Boring..");
        feedback13.setId(13);
        feedbackListForClass13.add(feedback13);
        Feedback feedback14 = new Feedback(member3, class14, 1, "Eww.");
        feedback14.setId(14);
        feedbackListForClass14.add(feedback14);
        Feedback feedback15 = new Feedback(member1, class15, 4, "Had a great time!");
        feedback15.setId(15);
        feedbackListForClass15.add(feedback15);

        // repos, services, controller and ui
        InMemoryRepository<Equipment> equipmentInMemoRepo = new InMemoryRepository<>();
        FileRepository<Equipment> equipmentFileRepo = new FileRepository<>("Equipment.csv",Equipment.class);
        equipmentFileRepo.create(weights);
        equipmentInMemoRepo.create(weights);
        equipmentInMemoRepo.create(mattresses);
        equipmentFileRepo.create(mattresses);
        equipmentInMemoRepo.create(treadmill);
        equipmentFileRepo.create(treadmill);
        equipmentInMemoRepo.create(legPress);
        equipmentFileRepo.create(legPress);
        equipmentInMemoRepo.create(foamRoller);
        equipmentFileRepo.create(foamRoller);
        equipmentInMemoRepo.create(resistanceBands);
        equipmentFileRepo.create(resistanceBands);
        equipmentInMemoRepo.create(pilatesRing);
        equipmentFileRepo.create(pilatesRing);
        equipmentInMemoRepo.create(yogaBlocks);
        equipmentFileRepo.create(yogaBlocks);
        equipmentInMemoRepo.create(jumpRope);
        equipmentFileRepo.create(jumpRope);
        equipmentInMemoRepo.create(airBike);
        equipmentFileRepo.create(airBike);
        equipmentInMemoRepo.create(boxingGloves);
        equipmentFileRepo.create(boxingGloves);
        equipmentInMemoRepo.create(headgear);
        equipmentFileRepo.create(headgear);
        equipmentInMemoRepo.create(punchingBags);
        equipmentFileRepo.create(punchingBags);

        InMemoryRepository<Feedback> feedbackInMemoRepo = new InMemoryRepository<>();
        FileRepository<Feedback> feedbackFileRepo = new FileRepository<>("Feedback.csv",Feedback.class);
        feedbackInMemoRepo.create(feedback1);
        feedbackFileRepo.create(feedback1);
        feedbackInMemoRepo.create(feedback2);
        feedbackFileRepo.create(feedback2);
        feedbackInMemoRepo.create(feedback3);
        feedbackFileRepo.create(feedback3);
        feedbackInMemoRepo.create(feedback4);
        feedbackFileRepo.create(feedback4);
        feedbackInMemoRepo.create(feedback5);
        feedbackFileRepo.create(feedback5);
        feedbackInMemoRepo.create(feedback6);
        feedbackFileRepo.create(feedback6);
        feedbackInMemoRepo.create(feedback7);
        feedbackFileRepo.create(feedback7);
        feedbackInMemoRepo.create(feedback8);
        feedbackFileRepo.create(feedback8);
        feedbackInMemoRepo.create(feedback9);
        feedbackFileRepo.create(feedback9);
        feedbackInMemoRepo.create(feedback10);
        feedbackFileRepo.create(feedback10);
        feedbackInMemoRepo.create(feedback11);
        feedbackFileRepo.create(feedback11);
        feedbackInMemoRepo.create(feedback12);
        feedbackFileRepo.create(feedback12);
        feedbackInMemoRepo.create(feedback13);
        feedbackFileRepo.create(feedback13);
        feedbackInMemoRepo.create(feedback14);
        feedbackFileRepo.create(feedback14);
        feedbackInMemoRepo.create(feedback15);
        feedbackFileRepo.create(feedback15);

        InMemoryRepository<FitnessClass> fitnessClassInMemoRepo = new InMemoryRepository<>();
        FileRepository<FitnessClass> fitnessClassFileRepo = new FileRepository<>("FitnessClass.csv", FitnessClass.class);
        fitnessClassInMemoRepo.create(class1);
        fitnessClassFileRepo.create(class1);
        fitnessClassInMemoRepo.create(class2);
        fitnessClassFileRepo.create(class2);
        fitnessClassInMemoRepo.create(class3);
        fitnessClassFileRepo.create(class3);
        fitnessClassInMemoRepo.create(class4);
        fitnessClassFileRepo.create(class4);
        fitnessClassInMemoRepo.create(class5);
        fitnessClassFileRepo.create(class5);
        fitnessClassInMemoRepo.create(class6);
        fitnessClassFileRepo.create(class6);
        fitnessClassInMemoRepo.create(class7);
        fitnessClassFileRepo.create(class7);
        fitnessClassInMemoRepo.create(class8);
        fitnessClassFileRepo.create(class8);
        fitnessClassInMemoRepo.create(class9);
        fitnessClassFileRepo.create(class9);
        fitnessClassInMemoRepo.create(class10);
        fitnessClassFileRepo.create(class10);
        fitnessClassInMemoRepo.create(class11);
        fitnessClassFileRepo.create(class11);
        fitnessClassInMemoRepo.create(class12);
        fitnessClassFileRepo.create(class12);
        fitnessClassInMemoRepo.create(class13);
        fitnessClassFileRepo.create(class13);
        fitnessClassInMemoRepo.create(class14);
        fitnessClassFileRepo.create(class14);
        fitnessClassInMemoRepo.create(class15);
        fitnessClassFileRepo.create(class15);

        InMemoryRepository<Location> locationInMemoRepo = new InMemoryRepository<>();
        FileRepository<Location> locationFileRepo = new FileRepository<>("Location.csv", Location.class);
        locationInMemoRepo.create(location1);
        locationFileRepo.create(location1);
        locationInMemoRepo.create(location2);
        locationFileRepo.create(location2);

        InMemoryRepository<Member> memberInMemoRepo = new InMemoryRepository<>();
        FileRepository<Member> memberFileRepo = new FileRepository<>("Member.csv",Member.class);
        memberInMemoRepo.create(member1);
        memberFileRepo.create(member1);
        memberInMemoRepo.create(member2);
        memberFileRepo.create(member2);
        memberInMemoRepo.create(member3);
        memberFileRepo.create(member3);
        memberInMemoRepo.create(member4);
        memberFileRepo.create(member4);
        memberInMemoRepo.create(member5);
        memberFileRepo.create(member5);

        InMemoryRepository<Membership> membershipInMemoRepo = new InMemoryRepository<>();
        FileRepository<Membership> membershipFileRepo = new FileRepository<>("Membership.csv",Membership.class);
        membershipInMemoRepo.create(basicMembership);
        membershipFileRepo.create(basicMembership);
        membershipInMemoRepo.create(studentMembership);
        membershipFileRepo.create(studentMembership);
        membershipInMemoRepo.create(premiumMembership);
        membershipFileRepo.create(premiumMembership);

        InMemoryRepository<Room> roomInMemoRepo = new InMemoryRepository<>();
        FileRepository<Room> roomFileRepo = new FileRepository<>("Room.csv",Room.class);
        roomInMemoRepo.create(room1);
        roomFileRepo.create(room1);
        roomInMemoRepo.create(room2);
        roomFileRepo.create(room2);
        roomInMemoRepo.create(room3);
        roomFileRepo.create(room3);
        roomInMemoRepo.create(room4);
        roomFileRepo.create(room4);
        roomInMemoRepo.create(room5);
        roomFileRepo.create(room5);

        InMemoryRepository<Trainer> trainerInMemoRepo = new InMemoryRepository<>();
        FileRepository<Trainer> trainerFileRepo = new FileRepository<>("Trainer.csv",Trainer.class);
        trainerInMemoRepo.create(trainer1);
        trainerFileRepo.create(trainer1);
        trainerInMemoRepo.create(trainer2);
        trainerFileRepo.create(trainer2);
        trainerInMemoRepo.create(trainer3);
        trainerFileRepo.create(trainer3);
        trainerInMemoRepo.create(trainer4);
        trainerFileRepo.create(trainer4);
        trainerInMemoRepo.create(trainer5);
        trainerFileRepo.create(trainer5);

        FitnessService inMemoryService = new FitnessService(equipmentInMemoRepo, feedbackInMemoRepo, fitnessClassInMemoRepo, locationInMemoRepo, memberInMemoRepo, membershipInMemoRepo, roomInMemoRepo, trainerInMemoRepo);
        FitnessService fileService = new FitnessService(equipmentFileRepo, feedbackFileRepo, fitnessClassFileRepo, locationFileRepo, memberFileRepo, membershipFileRepo, roomFileRepo, trainerFileRepo);
        FitnessController controller = new FitnessController(inMemoryService);

        UI ui = new UI(controller, membersList, trainersList);
        ui.login();

    }

}