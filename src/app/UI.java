package app;

import model.*;
import controller.*;
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
                    fitnessController.viewSchedule();
                    System.out.println("------------------------------------------ ");
                }
                case 2 -> {
                    System.out.println("------------------------------------------ ");
                    fitnessController.viewSchedule();
                    System.out.println("Which class would you like to book? (enter class ID): ");
                    int classId = Integer.parseInt(scanner.nextLine());
                    fitnessController.registerToClass(id, classId);
                    System.out.println("------------------------------------------ ");
                }
                case 3 -> {
                    System.out.println("------------------------------------------ ");
                    fitnessController.viewSchedule();
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

    // filer functions -> identify user (member or trainer)

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
        Trainer t1 = new Trainer("Sabin Matei", "sabinmatei@gmail.com", "0746912336", "weight lifting");
        Trainer t2 = new Trainer("Tudor Ratiu", "tudorratiu@gmail.com", "0733912375", "cardio");
        Trainer t3 = new Trainer("Rusai Denis", "rusaidenis@gmail.com", "0734119845", "kickboxing");
        Trainer t4 = new Trainer("Ana Popescu", "anapopescu@gmail.com", "0734829450", "yoga");
        Trainer t5 = new Trainer("Alina Carcea", "alinacarcea@gmail.com", "0745012367", "pilates");
        t1.setId(1);
        t2.setId(2);
        t3.setId(3);
        t4.setId(4);
        t5.setId(5);

        ArrayList<Trainer> trainersList = new ArrayList<>();
        trainersList.add(t1);
        trainersList.add(t2);
        trainersList.add(t3);
        trainersList.add(t4);
        trainersList.add(t5);

        // locations
        Location l1 = new Location("Revo Fit", "Str. Manastur nr.24");
        Location l2 = new Location("Revo Star", "Str. Buna Ziua nr.102");
        l1.setId(1);
        l2.setId(2);

        ArrayList<Location> locationsList = new ArrayList<>();
        locationsList.add(l1);
        locationsList.add(l2);

        // rooms
        Room r1 = new Room("Room 1", 30, l1);
        Room r2 = new Room("Room 2", 25, l2);
        Room r3 = new Room("Room 3", 18, l2);
        Room r4 = new Room("Room 4", 29, l1);
        Room r5 = new Room("Room 5", 31, l1);
        r1.setId(1);
        r2.setId(2);
        r3.setId(3);
        r4.setId(4);
        r5.setId(5);

        ArrayList<Room> roomList = new ArrayList<>();
        roomList.add(r1);
        roomList.add(r2);
        roomList.add(r3);
        roomList.add(r4);
        roomList.add(r5);

        // members
        ArrayList<FitnessClass> mihaelaClasses = new ArrayList<>();
        Member m1 = new Member("Hrihor Mihaela", "mihaelahrihor@gmail.com", "0758909520", LocalDate.ofYearDay(2024, 12), "Premium", mihaelaClasses);
        m1.setId(1);

        ArrayList<FitnessClass> danaClasses = new ArrayList<>();
        Member m2 = new Member("Matei Dana", "mateidana@gmail.com", "0771367002", LocalDate.ofYearDay(2024, 67), "Student", danaClasses);
        m2.setId(2);

        ArrayList<FitnessClass> mirunaClasses = new ArrayList<>();
        Member m3 = new Member("Mitri Miruna", "mitrimiruna@gmail.com", "0758867145", LocalDate.ofYearDay(2023, 47), "Basic", mirunaClasses);
        m3.setId(3);

        ArrayList<FitnessClass> kathrinClasses = new ArrayList<>();
        Member m4 = new Member("Berceaengels Kathrin", "kathrinberceaengels@gmail.com", "0745602416", LocalDate.ofYearDay(2020, 101), "Premium", kathrinClasses);
        m4.setId(4);

        ArrayList<FitnessClass> cristianaClasses = new ArrayList<>();
        Member m5 = new Member("Bleoca Cristiana", "cristianableoca@gmail.com", "0790143267", LocalDate.ofYearDay(2023, 258), "Student", cristianaClasses);
        m5.setId(5);

        ArrayList<Member> membersList = new ArrayList<>();
        membersList.add(m1);
        membersList.add(m2);
        membersList.add(m3);
        membersList.add(m4);
        membersList.add(m5);

        // memberships
        ArrayList<Member> studentMs = new ArrayList<>();
        studentMs.add(m2);
        studentMs.add(m5);

        Membership studentMembership = new Membership("Student", studentMs, 150);
        studentMembership.setId(1);

        ArrayList<Member> premiumMs = new ArrayList<>();
        premiumMs.add(m1);
        premiumMs.add(m4);

        Membership premiumMembership = new Membership("Premium", premiumMs, 250);
        premiumMembership.setId(2);

        ArrayList<Member> basicMs = new ArrayList<>();
        basicMs.add(m3);

        Membership basicMembership = new Membership("Basic", basicMs, 200);
        basicMembership.setId(3);

        ArrayList<Membership> membershipList = new ArrayList<>();
        membershipList.add(basicMembership);
        membershipList.add(premiumMembership);
        membershipList.add(studentMembership);

        // equipments
        ArrayList<FitnessClass> e1FcList = new ArrayList<>();
        Equipment e1 = new Equipment("weights", 50, e1FcList);
        e1.setId(1);

        ArrayList<FitnessClass> e2FcList = new ArrayList<>();
        Equipment e2 = new Equipment("mattresses", 30, e2FcList);
        e2.setId(2);

        ArrayList<FitnessClass> e3FcList = new ArrayList<>();
        Equipment e3 = new Equipment("treadmill", 10, e3FcList);
        e3.setId(3);

        ArrayList<FitnessClass> e4FcList = new ArrayList<>();
        Equipment e4 = new Equipment("leg press", 5, e4FcList);
        e4.setId(4);

        ArrayList<Equipment> equipmentList = new ArrayList<>();
        equipmentList.add(e1);
        equipmentList.add(e2);
        equipmentList.add(e3);
        equipmentList.add(e4);

        // feedbacks
        ArrayList<Equipment> eqForFClass1 = new ArrayList<>();
        eqForFClass1.add(e4);
        ArrayList<Member> f1Members = new ArrayList<>();
        f1Members.add(m1);
        f1Members.add(m3);
        ArrayList<Feedback> fBackForFClass1List = new ArrayList<>();
        FitnessClass fClass1 = new FitnessClass("kickboxing", LocalDateTime.of(2024, 11, 20, 12, 30), LocalDateTime.of(2024, 11, 20, 14, 30), t1, r2, 18, r2.getLocation(), fBackForFClass1List, f1Members, eqForFClass1);
        fClass1.setId(1);
        Feedback fBack1 = new Feedback(m1, fClass1, 4, "Had a great time but the class was a little too exhausting for a beginner");
        fBack1.setId(1);
        fBackForFClass1List.add(fBack1);

        // fitness classes
        ArrayList<Equipment> eqForFClass2 = new ArrayList<>();
        eqForFClass1.add(e3);
        ArrayList<Member> f2Members = new ArrayList<>();
        f2Members.add(m2);
        f2Members.add(m4);
        f2Members.add(m5);
        ArrayList<Feedback> fBackForFClass2List = new ArrayList<>();
        FitnessClass fClass2 = new FitnessClass("cardio", LocalDateTime.of(2024, 11, 27, 17, 0), LocalDateTime.of(2024, 11, 27, 18, 0), t2, r1, 11, r1.getLocation(), fBackForFClass2List, f2Members, eqForFClass2);
        fClass2.setId(2);
        Feedback fBack2 = new Feedback(m2, fClass2, 2, "The trainer seemed of..");
        fBack2.setId(2);
        fBackForFClass2List.add(fBack2);

        ArrayList<Feedback> feedbackList = new ArrayList();
        feedbackList.add(fBack1);
        feedbackList.add(fBack2);

        // reservations
        Reservation res1 = new Reservation(m1, fClass1, LocalDateTime.of(2024, 11, 20, 12, 30));
        Reservation res2 = new Reservation(m2, fClass2, LocalDateTime.of(2024, 11, 27, 17, 0));
        res1.setId(1);
        res2.setId(2);

        ArrayList<Reservation> reservationsList = new ArrayList<>();
        reservationsList.add(res1);
        reservationsList.add(res2);

        // repos, services, controller and ui
        InMemoryRepository<Equipment> equipmentRepo = new InMemoryRepository<>();
        equipmentRepo.create(e1);
        equipmentRepo.create(e2);
        equipmentRepo.create(e3);
        equipmentRepo.create(e4);
        InMemoryRepository<Feedback> feedbackRepo = new InMemoryRepository<>();
        feedbackRepo.create(fBack1);
        feedbackRepo.create(fBack2);
        InMemoryRepository<FitnessClass> fitnessClassRepo = new InMemoryRepository<>();
        fitnessClassRepo.create(fClass1);
        fitnessClassRepo.create(fClass2);
        InMemoryRepository<Location> locationRepo = new InMemoryRepository<>();
        locationRepo.create(l1);
        locationRepo.create(l2);
        InMemoryRepository<Member> memberRepo = new InMemoryRepository<>();
        memberRepo.create(m1);
        memberRepo.create(m2);
        memberRepo.create(m3);
        memberRepo.create(m4);
        memberRepo.create(m5);
        InMemoryRepository<Membership> membershipRepo = new InMemoryRepository<>();
        membershipRepo.create(basicMembership);
        membershipRepo.create(studentMembership);
        membershipRepo.create(premiumMembership);
        InMemoryRepository<Reservation> reservationRepo = new InMemoryRepository<>();
        reservationRepo.create(res1);
        reservationRepo.create(res2);
        InMemoryRepository<Room> roomRepo = new InMemoryRepository<>();
        roomRepo.create(r1);
        roomRepo.create(r2);
        roomRepo.create(r3);
        roomRepo.create(r4);
        roomRepo.create(r5);
        InMemoryRepository<Trainer> trainerRepo = new InMemoryRepository<>();
        trainerRepo.create(t1);
        trainerRepo.create(t2);
        trainerRepo.create(t3);
        trainerRepo.create(t4);
        trainerRepo.create(t5);

        FitnessService service = new FitnessService(equipmentRepo, feedbackRepo, fitnessClassRepo, locationRepo, memberRepo, membershipRepo, reservationRepo, roomRepo, trainerRepo);
        FitnessController controller = new FitnessController(service);

        UI ui = new UI(controller, membersList, trainersList);
        ui.login();

    }

}