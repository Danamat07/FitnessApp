package app;

import model.*;
import controller.*;
import repository.*;
import service.FitnessService;
import Helpers.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    public List<Member> fitnessmembers;
    public List<Trainer> fitnesstraines;

    /**
     * Constructor to initialize the UI with necessary dependencies.
     * @param fitnessController The controller object that manages interactions with the service layer.
     * @param fitnessmembers The list of fitness members, representing the users interacting with the system.
     * @param fitnesstraines The list of trainers available for fitness classes.
     */
    public UI(FitnessController fitnessController, List<Member> fitnessmembers, List<Trainer> fitnesstraines) {
        this.fitnessController = fitnessController;
        this.fitnessmembers = fitnessmembers;
        this.fitnesstraines = fitnesstraines;
    }

    // Main menu, handles account of user
    public void menu() {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("Welcome to the Fitness center!");
            System.out.println("1. Create new account");
            System.out.println("2. Login");
            System.out.println("3. Delete account");
            System.out.println("4. Exit");
            System.out.println("Enter your option here (1, 2, 3 or 4): ");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> createNewAccount();
                case 2 -> login();
                case 3 -> deleteAccount();
                case 4 -> {
                    System.out.println("Exiting...");
                    isRunning = false;
                }
            }
        }
        scanner.close();
    }

    // updates account of user, identifying it by name and password
    public void updateAccount(String name, String password) {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning) {
            Member member = findMemberByCredentials(fitnessmembers,name, password);
            Trainer trainer = findTrainerByCredentials(fitnesstraines, name, password);
            if (member != null){
                int memberId = member.getId();
                System.out.println("Current membership type is: ");
                String membership = member.getMembership().getType();
                System.out.println(membership);
                System.out.println("Choose a new membership (by ID): ");
                List<Membership> membershipList = fitnessController.getAllMemberships();
                for(Membership membershipOption : membershipList){
                    System.out.println(membershipOption.getId() + ". " + membershipOption.getType() + " (" + membershipOption.getPrice() + " RON)");
                }
                Integer choice = Integer.parseInt(scanner.nextLine());
                Membership newMembership = fitnessController.getMembership(choice);
                fitnessController.updateMember(memberId, name, password, newMembership, member.getFitnessClasses());
                System.out.println("\n------------------------------------------ ");
                System.out.println("Back to menu. \n");
                memberUI(memberId, name, password);
            }else if(trainer != null){
                int trainerId = trainer.getId();
                System.out.println("Current specialisation is: ");
                String specialisation = trainer.getSpecialisation();
                System.out.println(specialisation);
                System.out.println("Enter updated specialisation here: ");
                String newSpecialisation = scanner.nextLine();
                fitnessController.updateTrainer(trainerId, name, password, newSpecialisation);
                System.out.println("\n------------------------------------------ ");
                System.out.println("Back to menu. \n");
                trainerUI(trainerId, name, password);
            }else {
                System.out.println("No user found with the given credentials.");
                System.out.println("Do you want to try again? (y/n): ");
                String tryAgain = scanner.nextLine();
                if (!tryAgain.equalsIgnoreCase("y")){
                    isRunning = false;
                }
            }
        }
        scanner.close();
    }

    // creates a new account for prefered user choice
    public void createNewAccount() {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("\n------------------------------------------ ");
            System.out.println("Which account type would you like to create? (member or trainer): ");
            String userInput = scanner.nextLine();
            if (Objects.equals(userInput, "member")) {
                System.out.println("\n------------------------------------------ ");
                String name = null;
                while (true) {
                    try {
                        System.out.println("Full name: ");
                        name = scanner.nextLine();
                        validateName(name);
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }
                System.out.println("Password: ");
                String password = null;
                while (true) {
                    try {
                        password = scanner.nextLine();
                        validatePassword(password);
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }
                LocalDateTime registrationDate = LocalDateTime.now();
                List<FitnessClass> classes = new ArrayList<>();
                int id = HelperFunctions.randomId();
                Member member = new Member(name, password, registrationDate, null, classes);
                member.setId(id);
                fitnessController.addMember(member);
                fitnessmembers.add(member);
                System.out.println("Account created successfully!");
                System.out.println("\n------------------------------------------ ");
                System.out.println("Back to main menu. \n");
                menu();
            } else if (Objects.equals(userInput, "trainer")) {
                System.out.println("\n------------------------------------------ ");
                String name = null;
                while (true) {
                    try {
                        System.out.println("Full name: ");
                        name = scanner.nextLine();
                        validateName(name);
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }
                System.out.println("Password: ");
                String password = null;
                while (true) {
                    try {
                        password = scanner.nextLine();
                        validatePassword(password);
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }
                String specialisation = "not specified";
                int id = HelperFunctions.randomId();
                Trainer trainer = new Trainer(name, password, specialisation);
                trainer.setId(id);
                fitnessController.addTrainer(trainer);
                fitnesstraines.add(trainer);
                System.out.println("Account created successfully!");
                System.out.println("\n------------------------------------------ ");
                System.out.println("Back to main menu. \n");
                menu();
            }
        }
        scanner.close();
    }

    // Method to validate the name
    private void validateName(String name) {
        // Check if the name is null or empty
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty. Please enter a valid name.");
        }
        // Check if the name contains digits
        if (name.matches(".*\\d.*")) {
            throw new IllegalArgumentException("Name cannot contain numbers. Please enter a valid name.");
        }
        // Check if each word in the name has at least 3 consecutive letters
        String[] words = name.split("\\s+");
        for (String word : words) {
            if (word.length() < 3) {
                throw new IllegalArgumentException("Each word in the name must have at least 3 letters. Please enter a valid name.");
            }
        }
    }

    // Method to validate the password
    private void validatePassword(String password) {
        // Check if the password is null or has fewer than 4 characters
        if (password == null || password.length() < 4) {
            throw new IllegalArgumentException("Password must have at least 4 characters. Please enter a valid password.");
        }
        // Check if the password contains spaces
        if (password.contains(" ")) {
            throw new IllegalArgumentException("Password cannot contain spaces. Please enter a valid password.");
        }
        // Check if the password contains only letters and numbers
        if (!password.matches("[a-zA-Z0-9]+")) {
            throw new IllegalArgumentException("Password can only contain letters and numbers. Please enter a valid password.");
        }
    }

    // deletes user account
    public void deleteAccount() {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("\n------------------------------------------ ");
            System.out.println("Full name: ");
            String name = scanner.nextLine();
            System.out.println("Password: ");
            String password = scanner.nextLine();
            Member member = findMemberByCredentials(fitnessmembers, name, password);
            Trainer trainer = findTrainerByCredentials(fitnesstraines, name, password);
            if (member != null) {
                int memberID = member.getId();
                fitnessController.deleteMember(memberID);
                fitnessmembers.remove(member);
                System.out.println("User deleted successfully.");
                System.out.println("\n------------------------------------------ ");
                System.out.println("Back to main menu. \n");
                menu();
            } else if (trainer != null) {
                int trainerID = trainer.getId();
                fitnessController.deleteTrainer(trainerID);
                fitnesstraines.remove(trainer);
                System.out.println("User deleted successfully.");
                System.out.println("\n------------------------------------------ ");
                System.out.println("Back to main menu. \n");
                menu();
            }
        }
    }

    // login method, redirects the user to the member or trainer menu
    public void login() {
        Scanner scanner = new Scanner(System.in);

        boolean isRunning = true;
        while (isRunning) {
            System.out.println("\n------------------------------------------ ");
            System.out.println("Please enter your login info:");
            System.out.println("Full name: ");
            String userName = scanner.nextLine();
            System.out.println("Password: ");
            String userPassword = scanner.nextLine();

            // Try to find member or trainer
            Member member = findMemberByCredentials(fitnessmembers, userName, userPassword);
            Trainer trainer = findTrainerByCredentials(fitnesstraines, userName, userPassword);

            // Check login results and redirect to appropriate UI
            if (member != null) {
                System.out.println("\nWelcome, " + member.getName() + "!");
                int memberID = member.getId();
                String memberName = member.getName();
                String memberPassword = member.getPassword();
                memberUI(memberID, memberName, memberPassword);
            } else if (trainer != null) {
                System.out.println("\nWelcome, " + trainer.getName() + "!");
                int trainerID = trainer.getId();
                String trainerName = trainer.getName();
                String trainerPassword = trainer.getPassword();
                trainerUI(trainerID, trainerName, trainerPassword);
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
        scanner.close();
    }

    // Filer functions -> identify user (member or trainer)

    // Method to find member by name, email, and phone
    public static Member findMemberByCredentials(List<Member> members, String name, String password) {
        for (Member member : members) {
            if (member.getName().equalsIgnoreCase(name) &&
                    member.getPassword().equalsIgnoreCase(password)){
                return member;
            }
        }
        return null;
    }

    // Method to find trainer by name, email, and phone
    public static Trainer findTrainerByCredentials(List<Trainer> trainers, String name, String password) {
        for (Trainer trainer : trainers) {
            if (trainer.getName().equalsIgnoreCase(name) &&
                    trainer.getPassword().equalsIgnoreCase(password)) {
                return trainer;
            }
        }
        return null;
    }

    // menu that handles trainer permissions
    public void trainerUI(int id, String name, String password) {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning){
            System.out.println("==== Menu ====");
            System.out.println("1. View my classes");
            System.out.println("2. Schedule a new class");
            System.out.println("3. Update a fitness class");
            System.out.println("4. Delete a fitness class");
            System.out.println("5. View feedback");
            System.out.println("6. Update account");
            System.out.println("7. Handle equipment");
            System.out.println("8. Handle locations");
            System.out.println("9. Handle rooms");
            System.out.println("10. Handle memberships");
            System.out.println("11. Logout");
            System.out.println("Enter your choice (1/2/3/4/5/6/7/8/9/10 or 11): ");

            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice){
                case 1 -> {
                    System.out.println("------------------------------------------ ");
                    fitnessController.displaySortedTrainerUpcomingClasses(id);
                    System.out.println("------------------------------------------ ");
                }
                case 2 -> {
                    System.out.println("------------------------------------------ ");
                    System.out.println("Enter class name: ");
                    String className = scanner.nextLine();
                    System.out.println("Enter start time (yyyy-MM-ddTHH:mm): ");
                    LocalDateTime startTime = LocalDateTime.parse(scanner.nextLine());
                    System.out.println("Enter end time (yyyy-MM-ddTHH:mm): ");
                    LocalDateTime endTime = LocalDateTime.parse(scanner.nextLine());
                    System.out.println("--->These are your room options: ");
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
                    System.out.println("\n");
                    fitnessController.displaySortedTrainerUpcomingClasses(id);
                    System.out.println("\nChoose class to update (by ID):");
                    int Tchoice = Integer.parseInt(scanner.nextLine());
                    FitnessClass fitnessClass = fitnessController.getFitnessClass(Tchoice);
                    System.out.println("Current start time: " + fitnessClass.getStartTime());
                    System.out.println("Enter new start time:");
                    LocalDateTime startTime = LocalDateTime.parse(scanner.nextLine());
                    System.out.println("Enter end time:");
                    LocalDateTime endTime = LocalDateTime.parse(scanner.nextLine());
                    fitnessController.updateFitnessClass(Tchoice, fitnessClass.getName(), startTime, endTime, fitnessClass.getTrainer(), fitnessClass.getRoom(), fitnessClass.getParticipantsCount(), fitnessClass.getLocation(), fitnessClass.getFeedback(), fitnessClass.getMembers(), fitnessClass.getEquipment());
                    System.out.println("\n------------------------------------------ ");
                }
                case 4 -> {
                    System.out.println("\n");
                    fitnessController.displaySortedTrainerUpcomingClasses(id);
                    System.out.println("\nChoose a class to delete (by ID):");
                    int Tchoice = Integer.parseInt(scanner.nextLine());
                    fitnessController.deleteFitnessClass(Tchoice);
                    System.out.println("\n------------------------------------------ ");
                }
                case 5 -> {
                    System.out.println("------------------------------------------ ");
                    fitnessController.displayClassesOfTrainer(id);
                    System.out.println("\n");
                    System.out.println("Choose a class to view feedback for (by ID): ");
                    int classId = Integer.parseInt(scanner.nextLine());
                    fitnessController.displayFeedback(classId);
                    System.out.println("------------------------------------------ ");
                }
                case 6 -> {
                    System.out.println("------------------------------------------ ");
                    updateAccount(name, password);
                }
                case 7 -> {
                    System.out.println("\n------------------------------------------ ");
                    handleEquipment(id, name, password);
                }
                case 8 -> {
                    System.out.println("\n------------------------------------------ ");
                    handleLocations(id, name, password);
                }
                case 9 -> {
                    System.out.println("\n------------------------------------------ ");
                    handleRooms(id, name, password);
                }
                case 10 -> {
                    System.out.println("\n------------------------------------------ ");
                    handleMemberships(id, name, password);
                }
                case 11 -> {
                    System.out.println("------------------------------------------ ");
                    System.out.println("Logging out...");
                    System.out.println("Back to main menu. \n");
                    menu();
                }
            }
        }
    }

    // method that handles CRUD operations for Equipment type objects
    public void handleEquipment(int id, String name, String password) {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("1. View all equipment");
            System.out.println("2. Add new equipment");
            System.out.println("3. Update existing equipment");
            System.out.println("4. Delete existing equipment");
            System.out.println("5. Go back");
            System.out.println("Enter your choice (1/2/3/4 or 5): ");

            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice){
                case 1 -> {
                    System.out.println("\n");
                    fitnessController.displayAllEquipment();
                }
                case 2 -> {
                    System.out.println("\nEnter new equipment name: ");
                    String equipmentName = scanner.nextLine();
                    System.out.println("\nEnter equipment quantity: ");
                    int equipmentQuantity = Integer.parseInt(scanner.nextLine());
                    List<FitnessClass> equipmentClasses = new ArrayList<>();
                    Equipment newEquipment = new Equipment(equipmentName, equipmentQuantity, equipmentClasses);
                    newEquipment.setId(HelperFunctions.randomId());
                    fitnessController.addEquipment(newEquipment);
                    System.out.println("\n------------------------------------------ ");
                }
                case 3 -> {
                    System.out.println("\n");
                    fitnessController.displayAllEquipment();
                    System.out.println("\nChoose equipment to update (by ID):");
                    int Tchoice = Integer.parseInt(scanner.nextLine());
                    Equipment equipmentToUpdate = fitnessController.getEquipment(Tchoice);
                    System.out.println("Current quantity: " + equipmentToUpdate.getQuantity());
                    System.out.println("Enter new quantity:");
                    int newQuantity = Integer.parseInt(scanner.nextLine());
                    fitnessController.updateEquipment(Tchoice, equipmentToUpdate.getName(), newQuantity, equipmentToUpdate.getFitnessClasses());
                    System.out.println("\n------------------------------------------ ");
                }
                case 4 -> {
                    System.out.println("\n");
                    fitnessController.displayAllEquipment();
                    System.out.println("\nChoose equipment to delete (by ID):");
                    int Tchoice = Integer.parseInt(scanner.nextLine());
                    fitnessController.deleteEquipment(Tchoice);
                    System.out.println("\n------------------------------------------ ");
                }
                case 5 -> {
                    System.out.println("------------------------------------------ ");
                    System.out.println("Back to menu. \n");
                    trainerUI(id, name, password);
                }
            }
        }
    }

    // method that handles CRUD operations for Location type objects
    public void handleLocations(int id, String name, String password) {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("1. View all locations");
            System.out.println("2. Add new location");
            System.out.println("3. Update existing location");
            System.out.println("4. Delete existing location");
            System.out.println("5. Go back");
            System.out.println("Enter your choice (1/2/3/4 or 5): ");

            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice){
                case 1 -> {
                    System.out.println("\n");
                    fitnessController.displayAllLocations();
                }
                case 2 -> {
                    System.out.println("\nEnter new location name: ");
                    String locationName = scanner.nextLine();
                    System.out.println("\nEnter location address: ");
                    String locationAddress = scanner.nextLine();
                    Location newLocation = new Location(locationName, locationAddress);
                    newLocation.setId(HelperFunctions.randomId());
                    fitnessController.addLocation(newLocation);
                    System.out.println("\n------------------------------------------ ");
                }
                case 3 -> {
                    System.out.println("\n");
                    fitnessController.displayAllLocations();
                    System.out.println("\nChoose location to update (by ID):");
                    int Tchoice = Integer.parseInt(scanner.nextLine());
                    Location locationToUpdate = fitnessController.getLocation(Tchoice);
                    System.out.println("Current name: " + locationToUpdate.getName());
                    System.out.println("Enter new name:");
                    String newName = scanner.nextLine();
                    fitnessController.updateLocation(Tchoice, newName, locationToUpdate.getAddress());
                    System.out.println("\n------------------------------------------ ");
                }
                case 4 -> {
                    System.out.println("\n");
                    fitnessController.displayAllLocations();
                    System.out.println("\nChoose location to delete (by ID):");
                    int Tchoice = Integer.parseInt(scanner.nextLine());
                    fitnessController.deleteLocation(Tchoice);
                    System.out.println("\n------------------------------------------ ");
                }
                case 5 -> {
                    System.out.println("------------------------------------------ ");
                    System.out.println("Back to menu. \n");
                    trainerUI(id, name, password);
                }
            }
        }
    }

    // method that handles CRUD operations for Room type objects
    public void handleRooms(int id, String name, String password) {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("1. View all rooms");
            System.out.println("2. Add new room");
            System.out.println("3. Update existing room");
            System.out.println("4. Delete existing room");
            System.out.println("5. Go back");
            System.out.println("Enter your choice (1/2/3/4 or 5): ");

            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice){
                case 1 -> {
                    System.out.println("\n");
                    fitnessController.displayAllRooms();
                }
                case 2 -> {
                    System.out.println("\nEnter new room name: ");
                    String roomName = scanner.nextLine();
                    System.out.println("\nEnter room max capacity: ");
                    int maxCapacity = Integer.parseInt(scanner.nextLine());
                    System.out.println("\n");
                    fitnessController.displayAllLocations();
                    System.out.println("\nChoose a location for the new room (by ID):");
                    int locationID = Integer.parseInt(scanner.nextLine());
                    Location location = fitnessController.getLocation(locationID);
                    Room newRoom = new Room(roomName, maxCapacity, location);
                    newRoom.setId(HelperFunctions.randomId());
                    fitnessController.addRoom(newRoom);
                    System.out.println("\n------------------------------------------ ");
                }
                case 3 -> {
                    System.out.println("\n");
                    fitnessController.displayAllRooms();
                    System.out.println("\nChoose room to update (by ID):");
                    int Tchoice = Integer.parseInt(scanner.nextLine());
                    Room roomToUpdate = fitnessController.getRoom(Tchoice);
                    System.out.println("Current name: " + roomToUpdate.getName());
                    System.out.println("Enter new name:");
                    String newName = scanner.nextLine();
                    System.out.println("Current capacity: " + roomToUpdate.getMaxCapacity());
                    System.out.println("Enter new capacity:");
                    int newCapacity = Integer.parseInt(scanner.nextLine());
                    fitnessController.updateRoom(Tchoice, newName, newCapacity, roomToUpdate.getLocation());
                    System.out.println("\n------------------------------------------ ");
                }
                case 4 -> {
                    System.out.println("\n");
                    fitnessController.displayAllRooms();
                    System.out.println("\nChoose room to delete (by ID):");
                    int Tchoice = Integer.parseInt(scanner.nextLine());
                    fitnessController.deleteRoom(Tchoice);
                    System.out.println("\n------------------------------------------ ");
                }
                case 5 -> {
                    System.out.println("------------------------------------------ ");
                    System.out.println("Back to menu. \n");
                    trainerUI(id, name, password);
                }
            }
        }
    }

    // method that handles CRUD operations for Membership type objects
    public void handleMemberships(int id, String name, String password) {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("1. View all memberships");
            System.out.println("2. Add new membership");
            System.out.println("3. Update existing membership");
            System.out.println("4. Delete existing membership");
            System.out.println("5. Go back");
            System.out.println("Enter your choice (1/2/3/4 or 5): ");

            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice){
                case 1 -> {
                    System.out.println("\n");
                    fitnessController.displayAllMemberships();
                }
                case 2 -> {
                    System.out.println("\nEnter new membership type: ");
                    String membershipType = scanner.nextLine();
                    System.out.println("\nEnter membership price: ");
                    int membershipPrice = Integer.parseInt(scanner.nextLine());
                    Membership newMembership = new Membership(membershipType, membershipPrice);
                    newMembership.setId(HelperFunctions.randomId());
                    fitnessController.addMembership(newMembership);
                    System.out.println("\n------------------------------------------ ");
                }
                case 3 -> {
                    System.out.println("\n");
                    fitnessController.displayAllMemberships();
                    System.out.println("\nChoose membership to update (by ID):");
                    int Tchoice = Integer.parseInt(scanner.nextLine());
                    Membership membershipToUpdate = fitnessController.getMembership(Tchoice);
                    System.out.println("Current price: " + membershipToUpdate.getPrice());
                    System.out.println("Enter new price:");
                    int newPrice = Integer.parseInt(scanner.nextLine());
                    fitnessController.updateMembership(Tchoice, membershipToUpdate.getType(), newPrice);
                    System.out.println("\n------------------------------------------ ");
                }
                case 4 -> {
                    System.out.println("\n");
                    fitnessController.displayAllMemberships();
                    System.out.println("\nChoose membership to delete (by ID):");
                    int Tchoice = Integer.parseInt(scanner.nextLine());
                    fitnessController.deleteMembership(Tchoice);
                    System.out.println("\n------------------------------------------ ");
                }
                case 5 -> {
                    System.out.println("------------------------------------------ ");
                    System.out.println("Back to menu. \n");
                    trainerUI(id, name, password);
                }
            }
        }
    }

    // menu that handles member permissions
    public void memberUI(int id, String name, String password) {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning){
            System.out.println("==== Menu ====");
            System.out.println("1. View upcoming classes");
            System.out.println("2. Register to class");
            System.out.println("3. Drop class");
            System.out.println("4. Get similar classes");
            System.out.println("5. Leave feedback");
            System.out.println("6. View feedback");
            System.out.println("7. Update account");
            System.out.println("8. Logout");
            System.out.println("Enter your choice (1/2/3/4/5/6/7 or 8): ");

            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice){
                case 1 -> {
                    System.out.println("------------------------------------------ ");
                    fitnessController.displaySortedUpcomingClasses();
                    System.out.println("------------------------------------------ ");
                }
                case 2 -> {
                    System.out.println("------------------------------------------ ");
                    fitnessController.getAllUpcomingClasses_MemberNotRegisteredYet(id);
                    System.out.println("Which class would you like to book? (enter class ID): ");
                    int classId = Integer.parseInt(scanner.nextLine());
                    fitnessController.registerToClass(id, classId);
                    System.out.println("------------------------------------------ ");
                }
                case 3 -> {
                    System.out.println("------------------------------------------ ");
                    fitnessController.displaySortedUpcomingClasses();
                    System.out.println("Which class would you like to drop? (enter class ID): ");
                    int classId = Integer.parseInt(scanner.nextLine());
                    fitnessController.dropClass(id, classId);
                    System.out.println("------------------------------------------ ");
                }
                case 4 -> {
                    System.out.println("------------------------------------------ ");
                    fitnessController.displayClassesByMember(id);
                    System.out.println("Choose a class to get a recommendations for similar classes (enter class ID): ");
                    int classId = Integer.parseInt(scanner.nextLine());
                    FitnessClass targetClass = fitnessController.findClassById(classId);
                    fitnessController.getSimilarClasses(targetClass);
                    System.out.println("------------------------------------------ ");
                }
                case 5 -> {
                    System.out.println("------------------------------------------ ");
                    fitnessController.displayPastClassesAttendedByMember(id);
                    System.out.println("Choose a class to leave a feedback to (by ID): ");
                    int classId = Integer.parseInt(scanner.nextLine());
                    System.out.println("Leave a rating (out of 5): ");
                    int rating = Integer.parseInt(scanner.nextLine());
                    System.out.println("Leave a comment: ");
                    String comment = scanner.nextLine();
                    fitnessController.addFeedbackForClass(id, classId, comment, rating);
                    System.out.println("------------------------------------------ ");
                }
                case 6 -> {
                    System.out.println("------------------------------------------ ");
                    fitnessController.displayAllFitnessClasses();
                    System.out.println("Choose a class to view a feedback (by ID): ");
                    int classId = Integer.parseInt(scanner.nextLine());
                    fitnessController.displayFeedback(classId);
                    System.out.println("------------------------------------------ ");
                }
                case 7 -> {
                    System.out.println("------------------------------------------ ");
                    updateAccount(name, password);
                }
                case 8 -> {
                    System.out.println("------------------------------------------ ");
                    System.out.println("Logging out...");
                    System.out.println("Back to main menu. \n");
                    menu();
                }
            }
        }
    }

    public static void main(String[] args) {
        // loading data

        // trainers
        Trainer trainer1 = new Trainer("Sabin Matei", "1234", "weight lifting, cardio");
        Trainer trainer2 = new Trainer("Tudor Ratiu", "1234", "cardio, weight lifting, kickboxing");
        Trainer trainer3 = new Trainer("Rusai Denis", "1234", "kickboxing, weight lifting");
        Trainer trainer4 = new Trainer("Ana Popescu", "1234", "yoga, pilates");
        Trainer trainer5 = new Trainer("Alina Carcea", "1234", "pilates, yoga");
        trainer1.setId(HelperFunctions.randomId());
        trainer2.setId(HelperFunctions.randomId());
        trainer3.setId(HelperFunctions.randomId());
        trainer4.setId(HelperFunctions.randomId());
        trainer5.setId(HelperFunctions.randomId());

        List<Trainer> trainersList = new ArrayList<>();
        trainersList.add(trainer1);
        trainersList.add(trainer2);
        trainersList.add(trainer3);
        trainersList.add(trainer4);
        trainersList.add(trainer5);

        // memberships
        Membership studentMembership = new Membership("Student", 150);
        studentMembership.setId(HelperFunctions.randomId());

        Membership premiumMembership = new Membership("Premium", 250);
        premiumMembership.setId(HelperFunctions.randomId());

        Membership basicMembership = new Membership("Basic", 200);
        basicMembership.setId(HelperFunctions.randomId());


        // locations
        Location location1 = new Location("Revo Fit", "Str. Manastur nr.24");
        Location location2 = new Location("Revo Star", "Str. Buna Ziua nr.102");
        location1.setId(HelperFunctions.randomId());
        location2.setId(HelperFunctions.randomId());

        ArrayList<Location> locationsList = new ArrayList<>();
        locationsList.add(location1);
        locationsList.add(location2);

        // rooms
        Room room1 = new Room("Champion’s Arena Room", 30, location1);
        Room room2 = new Room("Olympian’s Den Room", 25, location2);
        Room room3 = new Room("Endurance Edge Room", 18, location2);
        Room room4 = new Room("Powerhouse Pavilion Room", 29, location1);
        Room room5 = new Room("Agility Arena Room", 31, location1);
        room1.setId(HelperFunctions.randomId());
        room2.setId(HelperFunctions.randomId());
        room3.setId(HelperFunctions.randomId());
        room4.setId(HelperFunctions.randomId());
        room5.setId(HelperFunctions.randomId());

        ArrayList<Room> roomList = new ArrayList<>();
        roomList.add(room1);
        roomList.add(room2);
        roomList.add(room3);
        roomList.add(room4);
        roomList.add(room5);

        // members
        ArrayList<FitnessClass> member1Classes = new ArrayList<>();
        Member member1 = new Member("Hrihor Mihaela", "1234", LocalDateTime.of(2024, 1, 12, 12, 30), basicMembership, member1Classes);
        member1.setId(HelperFunctions.randomId());

        ArrayList<FitnessClass> member2Classes = new ArrayList<>();
        Member member2 = new Member("Matei Dana", "1234", LocalDateTime.of(2024, 1, 19, 11, 10), studentMembership, member2Classes);
        member2.setId(HelperFunctions.randomId());

        ArrayList<FitnessClass> member3Classes = new ArrayList<>();
        Member member3 = new Member("Mitri Miruna", "1234", LocalDateTime.of(2024, 1, 22, 16, 20), basicMembership, member3Classes);
        member3.setId(HelperFunctions.randomId());

        ArrayList<FitnessClass> member4Classes = new ArrayList<>();
        Member member4 = new Member("Berceaengels Kathrin", "1234", LocalDateTime.of(2024, 1, 2, 13, 30), premiumMembership, member4Classes);
        member4.setId(HelperFunctions.randomId());

        ArrayList<FitnessClass> member5Classes = new ArrayList<>();
        Member member5 = new Member("Bleoca Cristiana", "1234", LocalDateTime.of(2024, 1, 12, 12, 30), studentMembership, member5Classes);
        member5.setId(HelperFunctions.randomId());

        List<Member> membersList = new ArrayList<>();
        membersList.add(member1);
        membersList.add(member2);
        membersList.add(member3);
        membersList.add(member4);
        membersList.add(member5);

        // equipments
        ArrayList<FitnessClass> classesWithWeights = new ArrayList<>();
        Equipment weights = new Equipment("weights", 50, classesWithWeights);
        weights.setId(HelperFunctions.randomId());

        ArrayList<FitnessClass> classesWithMattresses = new ArrayList<>();
        Equipment mattresses = new Equipment("mattresses", 30, classesWithMattresses);
        mattresses.setId(HelperFunctions.randomId());

        ArrayList<FitnessClass> classesWithTreadmills = new ArrayList<>();
        Equipment treadmill = new Equipment("treadmill", 10, classesWithTreadmills);
        treadmill.setId(HelperFunctions.randomId());

        ArrayList<FitnessClass> classesWithLegPresses = new ArrayList<>();
        Equipment legPress = new Equipment("leg press", 5, classesWithLegPresses);
        legPress.setId(HelperFunctions.randomId());

        ArrayList<FitnessClass> classesWithFoamRollers = new ArrayList<>();
        Equipment foamRoller = new Equipment("foam roller", 10, classesWithFoamRollers);
        foamRoller.setId(HelperFunctions.randomId());

        ArrayList<FitnessClass> classesWithResistenceBands = new ArrayList<>();
        Equipment resistanceBands = new Equipment("resistance bands", 12, classesWithResistenceBands);
        resistanceBands.setId(HelperFunctions.randomId());

        ArrayList<FitnessClass> classesWithPilatesRings = new ArrayList<>();
        Equipment pilatesRing = new Equipment("pilates ring", 8, classesWithPilatesRings);
        pilatesRing.setId(HelperFunctions.randomId());

        ArrayList<FitnessClass> classesWithYogaBlocks = new ArrayList<>();
        Equipment yogaBlocks = new Equipment("yoga blocks", 40, classesWithYogaBlocks);
        yogaBlocks.setId(HelperFunctions.randomId());

        ArrayList<FitnessClass> classesWithJumpRopes = new ArrayList<>();
        Equipment jumpRope = new Equipment("jump rope", 25, classesWithJumpRopes);
        jumpRope.setId(HelperFunctions.randomId());

        ArrayList<FitnessClass> classesWithAirBikes = new ArrayList<>();
        Equipment airBike = new Equipment("air bike", 10, classesWithAirBikes);
        airBike.setId(HelperFunctions.randomId());

        ArrayList<FitnessClass> classesWithBoxingGloves = new ArrayList<>();
        Equipment boxingGloves = new Equipment("boxing gloves", 10, classesWithBoxingGloves);
        boxingGloves.setId(HelperFunctions.randomId());

        ArrayList<FitnessClass> classesWithHeadgears = new ArrayList<>();
        Equipment headgear = new Equipment("headgear", 10, classesWithHeadgears);
        headgear.setId(HelperFunctions.randomId());

        ArrayList<FitnessClass> classesWithPunchingBags = new ArrayList<>();
        Equipment punchingBags = new Equipment("punching bags", 10, classesWithPunchingBags);
        punchingBags.setId(HelperFunctions.randomId());

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
        class1.setId(HelperFunctions.randomId());
        member2Classes.add(class1);
        member3Classes.add(class1);
        member5Classes.add(class1);
        classesWithMattresses.add(class1);
        classesWithFoamRollers.add(class1);
        classesWithResistenceBands.add(class1);
        classesWithYogaBlocks.add(class1);

        ArrayList<Feedback> feedbackListForClass2 = new ArrayList<>();
        ArrayList<Member> membersForClass2 = new ArrayList<>();
        membersForClass2.add(member2);
        membersForClass2.add(member3);
        membersForClass2.add(member1);
        FitnessClass class2 = new FitnessClass("yoga", LocalDateTime.of(2024, 7, 3, 13, 0), LocalDateTime.of(2024, 7, 3, 15, 0), trainer5, room2, 20, room2.getLocation(), feedbackListForClass2, membersForClass2, yogaEquipment);
        class2.setId(HelperFunctions.randomId());
        member1Classes.add(class2);
        member2Classes.add(class2);
        member3Classes.add(class2);
        classesWithMattresses.add(class2);
        classesWithFoamRollers.add(class2);
        classesWithResistenceBands.add(class2);
        classesWithYogaBlocks.add(class2);

        ArrayList<Feedback> feedbackListForClass3 = new ArrayList<>();
        ArrayList<Member> membersForClass3 = new ArrayList<>();
        membersForClass3.add(member1);
        membersForClass3.add(member3);
        membersForClass3.add(member4);
        FitnessClass class3 = new FitnessClass("yoga", LocalDateTime.of(2025, 12, 5, 10, 0), LocalDateTime.of(2025, 12, 5, 12, 0), trainer5, room1, 20, room1.getLocation(), feedbackListForClass3, membersForClass3, yogaEquipment);
        class3.setId(HelperFunctions.randomId());
        member1Classes.add(class3);
        member3Classes.add(class3);
        member4Classes.add(class3);
        classesWithMattresses.add(class3);
        classesWithFoamRollers.add(class3);
        classesWithResistenceBands.add(class3);
        classesWithYogaBlocks.add(class3);

        ArrayList<Feedback> feedbackListForClass4 = new ArrayList<>();
        ArrayList<Member> membersForClass4 = new ArrayList<>();
        membersForClass4.add(member1);
        membersForClass4.add(member5);
        membersForClass4.add(member2);
        FitnessClass class4 = new FitnessClass("pilates", LocalDateTime.of(2024, 7, 5, 11, 30), LocalDateTime.of(2024, 7, 5, 13, 0), trainer4, room3, 20, room3.getLocation(), feedbackListForClass4, membersForClass4, pilatesEquipment);
        class4.setId(HelperFunctions.randomId());
        member1Classes.add(class4);
        member2Classes.add(class4);
        member5Classes.add(class4);
        classesWithMattresses.add(class4);
        classesWithFoamRollers.add(class4);
        classesWithResistenceBands.add(class4);
        classesWithPilatesRings.add(class4);

        ArrayList<Feedback> feedbackListForClass5 = new ArrayList<>();
        ArrayList<Member> membersForClass5 = new ArrayList<>();
        membersForClass5.add(member1);
        membersForClass5.add(member5);
        membersForClass5.add(member2);
        membersForClass5.add(member4);
        FitnessClass class5 = new FitnessClass("pilates", LocalDateTime.of(2024, 10, 10, 10, 30), LocalDateTime.of(2024, 10, 10, 12, 30), trainer4, room3, 20, room3.getLocation(), feedbackListForClass5, membersForClass5, pilatesEquipment);
        class5.setId(HelperFunctions.randomId());
        member1Classes.add(class5);
        member2Classes.add(class5);
        member4Classes.add(class5);
        member5Classes.add(class5);
        classesWithMattresses.add(class5);
        classesWithFoamRollers.add(class5);
        classesWithResistenceBands.add(class5);
        classesWithPilatesRings.add(class5);

        ArrayList<Feedback> feedbackListForClass6 = new ArrayList<>();
        ArrayList<Member> membersForClass6 = new ArrayList<>();
        membersForClass6.add(member1);
        membersForClass6.add(member2);
        membersForClass6.add(member3);
        membersForClass6.add(member4);
        FitnessClass class6 = new FitnessClass("pilates", LocalDateTime.of(2025, 12, 11, 10, 30), LocalDateTime.of(2025, 12, 11, 12, 30), trainer5, room4, 20, room4.getLocation(), feedbackListForClass6, membersForClass6, pilatesEquipment);
        class6.setId(HelperFunctions.randomId());
        member1Classes.add(class6);
        member2Classes.add(class6);
        member3Classes.add(class6);
        member4Classes.add(class6);
        classesWithMattresses.add(class6);
        classesWithFoamRollers.add(class6);
        classesWithResistenceBands.add(class6);
        classesWithPilatesRings.add(class6);

        ArrayList<Feedback> feedbackListForClass7 = new ArrayList<>();
        ArrayList<Member> membersForClass7 = new ArrayList<>();
        membersForClass7.add(member1);
        membersForClass7.add(member5);
        membersForClass7.add(member4);
        FitnessClass class7 = new FitnessClass("kickboxing", LocalDateTime.of(2024, 4, 20, 17, 30), LocalDateTime.of(2024, 4, 20, 19, 30), trainer2, room5, 20, room5.getLocation(), feedbackListForClass7, membersForClass7, boxEquipment);
        class7.setId(HelperFunctions.randomId());
        member1Classes.add(class7);
        member4Classes.add(class7);
        member5Classes.add(class7);
        classesWithWeights.add(class7);
        classesWithBoxingGloves.add(class7);
        classesWithHeadgears.add(class7);
        classesWithPunchingBags.add(class7);

        ArrayList<Feedback> feedbackListForClass8 = new ArrayList<>();
        ArrayList<Member> membersForClass8 = new ArrayList<>();
        membersForClass8.add(member1);
        membersForClass8.add(member5);
        membersForClass8.add(member2);
        FitnessClass class8 = new FitnessClass("kickboxing", LocalDateTime.of(2024, 8, 20, 17, 30), LocalDateTime.of(2024, 8, 20, 19, 30), trainer3, room2, 20, room2.getLocation(), feedbackListForClass8, membersForClass8, boxEquipment);
        class8.setId(HelperFunctions.randomId());
        member1Classes.add(class8);
        member2Classes.add(class8);
        member5Classes.add(class8);
        classesWithWeights.add(class8);
        classesWithBoxingGloves.add(class8);
        classesWithHeadgears.add(class8);
        classesWithPunchingBags.add(class8);

        ArrayList<Feedback> feedbackListForClass9 = new ArrayList<>();
        ArrayList<Member> membersForClass9 = new ArrayList<>();
        membersForClass9.add(member3);
        membersForClass9.add(member4);
        membersForClass9.add(member2);
        FitnessClass class9 = new FitnessClass("kickboxing", LocalDateTime.of(2025, 12, 22, 15, 30), LocalDateTime.of(2025, 12, 22, 17, 30), trainer2, room4, 20, room4.getLocation(), feedbackListForClass9, membersForClass9, boxEquipment);
        class9.setId(HelperFunctions.randomId());
        member2Classes.add(class9);
        member3Classes.add(class9);
        member4Classes.add(class9);
        classesWithWeights.add(class9);
        classesWithBoxingGloves.add(class9);
        classesWithHeadgears.add(class9);
        classesWithPunchingBags.add(class9);

        ArrayList<Feedback> feedbackListForClass10 = new ArrayList<>();
        ArrayList<Member> membersForClass10 = new ArrayList<>();
        membersForClass10.add(member3);
        membersForClass10.add(member4);
        membersForClass10.add(member2);
        membersForClass10.add(member1);
        FitnessClass class10 = new FitnessClass("cardio", LocalDateTime.of(2024, 9, 21, 13, 30), LocalDateTime.of(2024, 9, 21, 15, 30), trainer1, room4, 20, room4.getLocation(), feedbackListForClass10, membersForClass10, cardioEquipment);
        class10.setId(HelperFunctions.randomId());
        member1Classes.add(class10);
        member2Classes.add(class10);
        member3Classes.add(class10);
        member4Classes.add(class10);
        classesWithTreadmills.add(class10);
        classesWithJumpRopes.add(class10);
        classesWithAirBikes.add(class10);

        ArrayList<Feedback> feedbackListForClass11 = new ArrayList<>();
        ArrayList<Member> membersForClass11 = new ArrayList<>();
        membersForClass11.add(member4);
        membersForClass11.add(member5);
        membersForClass11.add(member1);
        FitnessClass class11 = new FitnessClass("cardio", LocalDateTime.of(2024, 1, 1, 12, 30), LocalDateTime.of(2024, 11, 1, 14, 30), trainer2, room3, 20, room3.getLocation(), feedbackListForClass11, membersForClass11, cardioEquipment);
        class11.setId(HelperFunctions.randomId());
        member1Classes.add(class11);
        member4Classes.add(class11);
        member5Classes.add(class11);
        classesWithTreadmills.add(class11);
        classesWithJumpRopes.add(class11);
        classesWithAirBikes.add(class11);

        ArrayList<Feedback> feedbackListForClass12 = new ArrayList<>();
        ArrayList<Member> membersForClass12 = new ArrayList<>();
        membersForClass12.add(member2);
        membersForClass12.add(member3);
        membersForClass12.add(member1);
        FitnessClass class12 = new FitnessClass("cardio", LocalDateTime.of(2025, 12, 12, 12, 30), LocalDateTime.of(2025, 12, 12, 14, 30), trainer1, room5, 20, room5.getLocation(), feedbackListForClass12, membersForClass12, cardioEquipment);
        class12.setId(HelperFunctions.randomId());
        member1Classes.add(class12);
        member2Classes.add(class12);
        member3Classes.add(class12);
        classesWithTreadmills.add(class12);
        classesWithJumpRopes.add(class12);
        classesWithAirBikes.add(class12);

        ArrayList<Feedback> feedbackListForClass13 = new ArrayList<>();
        ArrayList<Member> membersForClass13 = new ArrayList<>();
        membersForClass13.add(member4);
        membersForClass13.add(member5);
        membersForClass13.add(member1);
        FitnessClass class13 = new FitnessClass("weight lifting", LocalDateTime.of(2024, 10, 12, 10, 30), LocalDateTime.of(2024, 10, 12, 12, 30), trainer1, room1, 20, room1.getLocation(), feedbackListForClass13, membersForClass13, weightLiftingEquipment);
        class13.setId(HelperFunctions.randomId());
        member1Classes.add(class13);
        member4Classes.add(class13);
        member5Classes.add(class13);
        classesWithWeights.add(class13);
        classesWithLegPresses.add(class13);
        classesWithAirBikes.add(class13);

        ArrayList<Feedback> feedbackListForClass14 = new ArrayList<>();
        ArrayList<Member> membersForClass14 = new ArrayList<>();
        membersForClass14.add(member4);
        membersForClass14.add(member3);
        membersForClass14.add(member2);
        FitnessClass class14 = new FitnessClass("weight lifting", LocalDateTime.of(2024, 11, 2, 10, 30), LocalDateTime.of(2024, 11, 2, 12, 30), trainer2, room4, 20, room4.getLocation(), feedbackListForClass14, membersForClass14, weightLiftingEquipment);
        class14.setId(HelperFunctions.randomId());
        member2Classes.add(class14);
        member3Classes.add(class14);
        member4Classes.add(class14);
        classesWithWeights.add(class14);
        classesWithLegPresses.add(class14);
        classesWithAirBikes.add(class14);

        ArrayList<Feedback> feedbackListForClass15 = new ArrayList<>();
        ArrayList<Member> membersForClass15 = new ArrayList<>();
        membersForClass15.add(member4);
        membersForClass15.add(member1);
        membersForClass15.add(member2);
        membersForClass15.add(member5);
        FitnessClass class15 = new FitnessClass("weight lifting", LocalDateTime.of(2025, 12, 20, 10, 30), LocalDateTime.of(2025, 12, 20, 12, 30), trainer3, room3, 20, room3.getLocation(), feedbackListForClass15, membersForClass15, weightLiftingEquipment);
        class15.setId(HelperFunctions.randomId());
        member1Classes.add(class15);
        member2Classes.add(class15);
        member4Classes.add(class15);
        member5Classes.add(class15);
        classesWithWeights.add(class15);
        classesWithLegPresses.add(class15);
        classesWithAirBikes.add(class15);

        // feedbacks
        Feedback feedback1 = new Feedback(member2, class1, 5, "Nice energy. I really enjoyed this class!");
        feedback1.setId(HelperFunctions.randomId());
        feedbackListForClass1.add(feedback1);
        Feedback feedback2 = new Feedback(member3, class2, 4, "Great class!");
        feedback2.setId(HelperFunctions.randomId());
        feedbackListForClass2.add(feedback2);
        Feedback feedback3 = new Feedback(member4, class3, 4, "I'll definitely come again!");
        feedback3.setId(HelperFunctions.randomId());
        feedbackListForClass3.add(feedback3);
        Feedback feedback4 = new Feedback(member1, class4, 3, "Niceeee!!");
        feedback4.setId(HelperFunctions.randomId());
        feedbackListForClass4.add(feedback4);
        Feedback feedback5 = new Feedback(member4, class5, 5, "10/10");
        feedback5.setId(HelperFunctions.randomId());
        feedbackListForClass5.add(feedback5);
        Feedback feedback6 = new Feedback(member1, class6, 5, "10/10");
        feedback6.setId(HelperFunctions.randomId());
        feedbackListForClass6.add(feedback6);
        Feedback feedback7 = new Feedback(member5, class7, 3, "It was fine..");
        feedback7.setId(HelperFunctions.randomId());
        feedbackListForClass7.add(feedback7);
        Feedback feedback8 = new Feedback(member1, class8, 5, "Had a great time. I'll definitely come again!");
        feedback8.setId(HelperFunctions.randomId());
        feedbackListForClass8.add(feedback8);
        Feedback feedback9 = new Feedback(member2, class9, 4, "Great class!");
        feedback9.setId(HelperFunctions.randomId());
        feedbackListForClass9.add(feedback9);
        Feedback feedback10 = new Feedback(member3, class10, 5, "Amasingggg!!");
        feedback10.setId(HelperFunctions.randomId());
        feedbackListForClass10.add(feedback10);
        Feedback feedback11 = new Feedback(member4, class11, 3, "It was exhausting..");
        feedback11.setId(HelperFunctions.randomId());
        feedbackListForClass11.add(feedback11);
        Feedback feedback12 = new Feedback(member1, class12, 5, "10/10");
        feedback12.setId(HelperFunctions.randomId());
        feedbackListForClass12.add(feedback12);
        Feedback feedback13 = new Feedback(member5, class13, 2, "Boring..");
        feedback13.setId(HelperFunctions.randomId());
        feedbackListForClass13.add(feedback13);
        Feedback feedback14 = new Feedback(member3, class14, 1, "Eww.");
        feedback14.setId(HelperFunctions.randomId());
        feedbackListForClass14.add(feedback14);
        Feedback feedback15 = new Feedback(member1, class15, 4, "Had a great time!");
        feedback15.setId(HelperFunctions.randomId());
        feedbackListForClass15.add(feedback15);

        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("Choose a data storage method");
            System.out.println("1. InMemory storage");
            System.out.println("2. File storage");
            System.out.println("3. Data Base storage");
            System.out.println("Your option: ");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> {
                    InMemoryRepository<Equipment> equipmentInMemoRepo = new InMemoryRepository<>();
                    equipmentInMemoRepo.create(weights);
                    equipmentInMemoRepo.create(mattresses);
                    equipmentInMemoRepo.create(treadmill);
                    equipmentInMemoRepo.create(legPress);
                    equipmentInMemoRepo.create(foamRoller);
                    equipmentInMemoRepo.create(resistanceBands);
                    equipmentInMemoRepo.create(pilatesRing);
                    equipmentInMemoRepo.create(yogaBlocks);
                    equipmentInMemoRepo.create(jumpRope);
                    equipmentInMemoRepo.create(airBike);
                    equipmentInMemoRepo.create(boxingGloves);
                    equipmentInMemoRepo.create(headgear);
                    equipmentInMemoRepo.create(punchingBags);

                    InMemoryRepository<Feedback> feedbackInMemoRepo = new InMemoryRepository<>();
                    feedbackInMemoRepo.create(feedback1);
                    feedbackInMemoRepo.create(feedback2);
                    feedbackInMemoRepo.create(feedback3);
                    feedbackInMemoRepo.create(feedback4);
                    feedbackInMemoRepo.create(feedback5);
                    feedbackInMemoRepo.create(feedback6);
                    feedbackInMemoRepo.create(feedback7);
                    feedbackInMemoRepo.create(feedback8);
                    feedbackInMemoRepo.create(feedback9);
                    feedbackInMemoRepo.create(feedback10);
                    feedbackInMemoRepo.create(feedback11);
                    feedbackInMemoRepo.create(feedback12);
                    feedbackInMemoRepo.create(feedback13);
                    feedbackInMemoRepo.create(feedback14);
                    feedbackInMemoRepo.create(feedback15);

                    InMemoryRepository<FitnessClass> fitnessClassInMemoRepo = new InMemoryRepository<>();
                    fitnessClassInMemoRepo.create(class1);
                    fitnessClassInMemoRepo.create(class2);
                    fitnessClassInMemoRepo.create(class3);
                    fitnessClassInMemoRepo.create(class4);
                    fitnessClassInMemoRepo.create(class5);
                    fitnessClassInMemoRepo.create(class6);
                    fitnessClassInMemoRepo.create(class7);
                    fitnessClassInMemoRepo.create(class8);
                    fitnessClassInMemoRepo.create(class9);
                    fitnessClassInMemoRepo.create(class10);
                    fitnessClassInMemoRepo.create(class11);
                    fitnessClassInMemoRepo.create(class12);
                    fitnessClassInMemoRepo.create(class13);
                    fitnessClassInMemoRepo.create(class14);
                    fitnessClassInMemoRepo.create(class15);

                    InMemoryRepository<Location> locationInMemoRepo = new InMemoryRepository<>();
                    locationInMemoRepo.create(location1);
                    locationInMemoRepo.create(location2);

                    InMemoryRepository<Member> memberInMemoRepo = new InMemoryRepository<>();
                    memberInMemoRepo.create(member1);
                    memberInMemoRepo.create(member2);
                    memberInMemoRepo.create(member3);
                    memberInMemoRepo.create(member4);
                    memberInMemoRepo.create(member5);

                    InMemoryRepository<Membership> membershipInMemoRepo = new InMemoryRepository<>();
                    membershipInMemoRepo.create(basicMembership);
                    membershipInMemoRepo.create(studentMembership);
                    membershipInMemoRepo.create(premiumMembership);

                    InMemoryRepository<Room> roomInMemoRepo = new InMemoryRepository<>();
                    roomInMemoRepo.create(room1);
                    roomInMemoRepo.create(room2);
                    roomInMemoRepo.create(room3);
                    roomInMemoRepo.create(room4);
                    roomInMemoRepo.create(room5);

                    InMemoryRepository<Trainer> trainerInMemoRepo = new InMemoryRepository<>();
                    trainerInMemoRepo.create(trainer1);
                    trainerInMemoRepo.create(trainer2);
                    trainerInMemoRepo.create(trainer3);
                    trainerInMemoRepo.create(trainer4);
                    trainerInMemoRepo.create(trainer5);

                    FitnessService inMemoryService = new FitnessService(equipmentInMemoRepo, feedbackInMemoRepo, fitnessClassInMemoRepo, locationInMemoRepo, memberInMemoRepo, membershipInMemoRepo, roomInMemoRepo, trainerInMemoRepo);
                    FitnessController controller = new FitnessController(inMemoryService);
                    UI ui = new UI(controller, membersList, trainersList);
                    ui.menu();
                }

                case 2 -> {
                    String filePath = "C:\\Users\\Dell\\IdeaProjects\\FitnessApp\\src\\files\\";

                    FileRepository<Location> locationFileRepo = new FileRepository<>(filePath + "Location.txt");
                    locationFileRepo.create(location1);
                    locationFileRepo.create(location2);

                    FileRepository<Room> roomFileRepo = new FileRepository<>(filePath + "Room.txt");
                    roomFileRepo.create(room1);
                    roomFileRepo.create(room2);
                    roomFileRepo.create(room3);
                    roomFileRepo.create(room4);
                    roomFileRepo.create(room5);

                    FileRepository<Trainer> trainerFileRepo = new FileRepository<>(filePath + "Trainer.txt");
                    trainerFileRepo.create(trainer1);
                    trainerFileRepo.create(trainer2);
                    trainerFileRepo.create(trainer3);
                    trainerFileRepo.create(trainer4);
                    trainerFileRepo.create(trainer5);

                    FileRepository<Equipment> equipmentFileRepo = new FileRepository<>(filePath + "Equipment.txt");
                    equipmentFileRepo.create(weights);
                    equipmentFileRepo.create(mattresses);
                    equipmentFileRepo.create(treadmill);
                    equipmentFileRepo.create(legPress);
                    equipmentFileRepo.create(foamRoller);
                    equipmentFileRepo.create(resistanceBands);
                    equipmentFileRepo.create(pilatesRing);
                    equipmentFileRepo.create(yogaBlocks);
                    equipmentFileRepo.create(jumpRope);
                    equipmentFileRepo.create(airBike);
                    equipmentFileRepo.create(boxingGloves);
                    equipmentFileRepo.create(headgear);
                    equipmentFileRepo.create(punchingBags);

                    FileRepository<Membership> membershipFileRepo = new FileRepository<>(filePath + "Membership.txt");
                    membershipFileRepo.create(basicMembership);
                    membershipFileRepo.create(studentMembership);
                    membershipFileRepo.create(premiumMembership);

                    FileRepository<Member> memberFileRepo = new FileRepository<>(filePath + "Member.txt");
                    memberFileRepo.create(member1);
                    memberFileRepo.create(member2);
                    memberFileRepo.create(member3);
                    memberFileRepo.create(member4);
                    memberFileRepo.create(member5);

                    FileRepository<FitnessClass> fitnessClassFileRepo = new FileRepository<>(filePath + "FitnessClass.txt");
                    fitnessClassFileRepo.create(class1);
                    fitnessClassFileRepo.create(class2);
                    fitnessClassFileRepo.create(class3);
                    fitnessClassFileRepo.create(class4);
                    fitnessClassFileRepo.create(class5);
                    fitnessClassFileRepo.create(class6);
                    fitnessClassFileRepo.create(class7);
                    fitnessClassFileRepo.create(class8);
                    fitnessClassFileRepo.create(class9);
                    fitnessClassFileRepo.create(class10);
                    fitnessClassFileRepo.create(class11);
                    fitnessClassFileRepo.create(class12);
                    fitnessClassFileRepo.create(class13);
                    fitnessClassFileRepo.create(class14);
                    fitnessClassFileRepo.create(class15);

                    FileRepository<Feedback> feedbackFileRepo = new FileRepository<>(filePath + "Feedback.txt");
                    feedbackFileRepo.create(feedback1);
                    feedbackFileRepo.create(feedback2);
                    feedbackFileRepo.create(feedback3);
                    feedbackFileRepo.create(feedback4);
                    feedbackFileRepo.create(feedback5);
                    feedbackFileRepo.create(feedback6);
                    feedbackFileRepo.create(feedback7);
                    feedbackFileRepo.create(feedback8);
                    feedbackFileRepo.create(feedback9);
                    feedbackFileRepo.create(feedback10);
                    feedbackFileRepo.create(feedback11);
                    feedbackFileRepo.create(feedback12);
                    feedbackFileRepo.create(feedback13);
                    feedbackFileRepo.create(feedback14);
                    feedbackFileRepo.create(feedback15);

                    FitnessService fileService = new FitnessService(equipmentFileRepo, feedbackFileRepo, fitnessClassFileRepo, locationFileRepo, memberFileRepo, membershipFileRepo, roomFileRepo, trainerFileRepo);
                    FitnessController controller = new FitnessController(fileService);
                    UI ui = new UI(controller, membersList, trainersList);
                    ui.menu();
                }

                case 3 -> {
                    String DB_URL = "jdbc:postgresql://localhost:5432/Fitness";
                    String DB_USER = "postgres";
                    String DB_PASSWORD = "danamat07";

                    LocationDBRepository locationDBRepo = new LocationDBRepository(DB_URL, DB_USER, DB_PASSWORD);
                    locationDBRepo.create(location1);
                    locationDBRepo.create(location2);

                    RoomDBRepository roomDBRepo = new RoomDBRepository(DB_URL, DB_USER, DB_PASSWORD);
                    roomDBRepo.create(room1);
                    roomDBRepo.create(room2);
                    roomDBRepo.create(room3);
                    roomDBRepo.create(room4);
                    roomDBRepo.create(room5);

                    TrainerDBRepository trainerDBRepo = new TrainerDBRepository(DB_URL, DB_USER, DB_PASSWORD);
                    trainerDBRepo.create(trainer1);
                    trainerDBRepo.create(trainer2);
                    trainerDBRepo.create(trainer3);
                    trainerDBRepo.create(trainer4);
                    trainerDBRepo.create(trainer5);

                    EquipmentDBRepository equipmentDBRepo = new EquipmentDBRepository(DB_URL, DB_USER, DB_PASSWORD);
                    equipmentDBRepo.create(weights);
                    equipmentDBRepo.create(mattresses);
                    equipmentDBRepo.create(treadmill);
                    equipmentDBRepo.create(legPress);
                    equipmentDBRepo.create(foamRoller);
                    equipmentDBRepo.create(resistanceBands);
                    equipmentDBRepo.create(pilatesRing);
                    equipmentDBRepo.create(yogaBlocks);
                    equipmentDBRepo.create(jumpRope);
                    equipmentDBRepo.create(airBike);
                    equipmentDBRepo.create(boxingGloves);
                    equipmentDBRepo.create(headgear);
                    equipmentDBRepo.create(punchingBags);

                    MembershipDBRepository membershipDBRepo = new MembershipDBRepository(DB_URL, DB_USER, DB_PASSWORD);
                    membershipDBRepo.create(basicMembership);
                    membershipDBRepo.create(studentMembership);
                    membershipDBRepo.create(premiumMembership);

                    MemberDBRepository memberDBRepo = new MemberDBRepository(DB_URL, DB_USER, DB_PASSWORD);
                    memberDBRepo.create(member1);
                    memberDBRepo.create(member2);
                    memberDBRepo.create(member3);
                    memberDBRepo.create(member4);
                    memberDBRepo.create(member5);

                    FitnessClassDBRepository fitnessClassDBRepo = new FitnessClassDBRepository(DB_URL, DB_USER, DB_PASSWORD);
                    fitnessClassDBRepo.create(class1);
                    fitnessClassDBRepo.create(class2);
                    fitnessClassDBRepo.create(class3);
                    fitnessClassDBRepo.create(class4);
                    fitnessClassDBRepo.create(class5);
                    fitnessClassDBRepo.create(class6);
                    fitnessClassDBRepo.create(class7);
                    fitnessClassDBRepo.create(class8);
                    fitnessClassDBRepo.create(class9);
                    fitnessClassDBRepo.create(class10);
                    fitnessClassDBRepo.create(class11);
                    fitnessClassDBRepo.create(class12);
                    fitnessClassDBRepo.create(class13);
                    fitnessClassDBRepo.create(class14);
                    fitnessClassDBRepo.create(class15);

                    FeedbackDBRepository feedbackDBRepo = new FeedbackDBRepository(DB_URL, DB_USER, DB_PASSWORD);
                    feedbackDBRepo.create(feedback1);
                    feedbackDBRepo.create(feedback2);
                    feedbackDBRepo.create(feedback3);
                    feedbackDBRepo.create(feedback4);
                    feedbackDBRepo.create(feedback5);
                    feedbackDBRepo.create(feedback6);
                    feedbackDBRepo.create(feedback7);
                    feedbackDBRepo.create(feedback8);
                    feedbackDBRepo.create(feedback9);
                    feedbackDBRepo.create(feedback10);
                    feedbackDBRepo.create(feedback11);
                    feedbackDBRepo.create(feedback12);
                    feedbackDBRepo.create(feedback13);
                    feedbackDBRepo.create(feedback14);
                    feedbackDBRepo.create(feedback15);

                    FitnessService DBService = new FitnessService(equipmentDBRepo, feedbackDBRepo, fitnessClassDBRepo, locationDBRepo, memberDBRepo, membershipDBRepo, roomDBRepo, trainerDBRepo);
                    FitnessController controller = new FitnessController(DBService);
                    UI ui = new UI(controller, membersList, trainersList);
                    ui.menu();
                }
            }
        }
        scanner.close();
    }
}