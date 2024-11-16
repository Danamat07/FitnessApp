package app;

import model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Trainer t1 = new Trainer("Alex Coroama", "alexcoroama@gmail.com", "0746912336", "weight lifting");
        Trainer t2 = new Trainer("Liviu Coroama", "liviucoroama@gmail.com", "0733912375", "cardio");
        Trainer t3 = new Trainer("Rusai Denis", "rusaidenis@gmail.com", "0734119845", "kickboxing");
        Trainer t4 = new Trainer("Ana Popescu", "anapopescu@gmail.com", "0734829450", "yoga");
        Trainer t5 = new Trainer("Alina Carcea", "alinacarcea@gmail.com", "0745012367", "pilates");

        ArrayList<Trainer> trainersList = new ArrayList<>();
        trainersList.add(t1);
        trainersList.add(t2);
        trainersList.add(t3);
        trainersList.add(t4);
        trainersList.add(t5);

        Location l1 = new Location("Revo Fit", "Str. Manastur nr.24");
        Location l2 = new Location("Revo Star", "Str. Buna Ziua nr.102");

        Room r1 = new Room("Room 1", 30, l1);
        Room r2 = new Room("Room 2", 25, l2);
        Room r3 = new Room("Room 3", 18, l2);
        Room r4 = new Room("Room 4", 29, l1);
        Room r5 = new Room("Room 5", 31, l1);

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

        ArrayList<Member> studentMs = new ArrayList<>();
        studentMs.add(m2);
        studentMs.add(m5);

        Membership studentMembership = new Membership("Student", studentMs, 150);

        ArrayList<Member> premiumMs = new ArrayList<>();
        premiumMs.add(m1);
        premiumMs.add(m4);

        Membership premiumMembership = new Membership("Premium", premiumMs, 250);

        ArrayList<Member> basicMs = new ArrayList<>();
        basicMs.add(m3);

        Membership basicMembership = new Membership("Basic", basicMs, 200);

        ArrayList<Feedback> feedbacksfc1 = new ArrayList<>();

        ArrayList<Member> membersfc1 = new ArrayList<>();

        ArrayList<Equipment> equipmentfc1 = new ArrayList<>();

        FitnessClass fc1 = new FitnessClass("Yoga", LocalDateTime.of(2024, 11, 15, 10, 30), LocalDateTime.of(2024, 11, 15, 12, 30), t4, r1, 10, r1.getLocation(), feedbacksfc1, membersfc1, equipmentfc1);

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
            Member member = findMemberByCredentials(membersList, userName, userEmail, userPhoneNumber);
            Trainer trainer = findTrainerByCredentials(trainersList, userName, userEmail, userPhoneNumber);

//            // Check login results and redirect to appropriate UI
//            if (member != null) {
//                System.out.println("Welcome, " + member.getName() + " (Member)!");
//                int memberID = member.getId();
//                MemberUI memberUI = new MemberUI(memberID);
//                memberUI.showMemberOptions(memberID);
//                isRunning = false; // Exit after successful login
//            } else if (trainer != null) {
//                System.out.println("Welcome, " + trainer.getName() + " (Trainer)!");
//                int trainerID = trainer.getId();
//                TrainerUI trainerUI = new TrainerUI(trainerID);
//                trainerUI.showTrainerOptions(trainerID);
//                isRunning = false; // Exit after successful login
//            } else {
//                // If no matching member or trainer is found
//                System.out.println("No user found with the given credentials.");
//                System.out.println("Do you want to try again? (y/n): ");
//                String tryAgain = scanner.nextLine();
//                if (!tryAgain.equalsIgnoreCase("y")) {
//                    isRunning = false; // Exit if user doesn't want to try again
//                }
//            }
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


}