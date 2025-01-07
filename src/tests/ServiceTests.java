package tests;

import service.*;
import model.*;
import repository.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Unit tests for the FitnessService methods.
 * These tests validate the behavior of complex methods such as sorting, filtering and recommendation algorithm.
 */
public class ServiceTests {

    private FitnessService service;
    private IRepository<FitnessClass> fitnessClassIRepository;

    @BeforeEach
    public void setUp() {
        IRepository<Equipment> equipmentIRepository = new InMemoryRepository<>();
        IRepository<Feedback> feedbackIRepository = new InMemoryRepository<>();
        fitnessClassIRepository = new InMemoryRepository<>();
        IRepository<Location> locationIRepository = new InMemoryRepository<>();
        IRepository<Member> memberIRepository = new InMemoryRepository<>();
        IRepository<Membership> membershipIRepository = new InMemoryRepository<>();
        IRepository<Room> roomIRepository = new InMemoryRepository<>();
        IRepository<Trainer> trainerIRepository = new InMemoryRepository<>();

        service = new FitnessService(
                equipmentIRepository,
                feedbackIRepository,
                fitnessClassIRepository,
                locationIRepository,
                memberIRepository,
                membershipIRepository,
                roomIRepository,
                trainerIRepository);
    }

    /**
     * Test the behavior of the `getSimilarClasses` method when the input target class is `null`.
     * This test verifies that the method throws an `IllegalArgumentException` when it receives a `null` argument as the target class.
     * Expected outcome: The method should throw an `IllegalArgumentException`.
     */
    @Test
    void testGetSimilarClassesWithNullTarget() {
        assertThrows(IllegalArgumentException.class, () -> service.getSimilarClasses(null));
    }

    /**
     * Test the functionality of the `getSimilarClasses` method when there are similar classes in the repository.
     * This test sets up a repository with multiple fitness classes, some of which are similar to the target class (based on type, trainer, equipment, and future start time).
     * It verifies that the method correctly identifies similar classes and excludes the target class, classes with different trainers, and classes that have passed.
     * Expected outcome:
     * - The method should return a list of similar classes, excluding the target class and any unrelated or past classes.
     * - Only classes with matching type, trainer, and equipment, and that have a future start time should be included.
     */
    @Test
    void testGetSimilarClasses() {
        // Setup repo with data

        // Trainers fot classes
        Trainer trainer1 = new Trainer("John Doe", "1234", "none");
        Trainer trainer2 = new Trainer("Jane Smith", "1234", "none");

        Location location = new Location("Gym A", "123 Main St");
        Room room1 = new Room("room1", 30, location);

        // Equipment fot classes
        Equipment yogaEquipment = new Equipment("Yoga Mat", 3, new ArrayList<>());
        List<Equipment> yogaList = new ArrayList<>();
        yogaList.add(yogaEquipment);
        Equipment cardioEquipment = new Equipment("Treadmill", 5, new ArrayList<>());
        List<Equipment> cardioList = new ArrayList<>();
        cardioList.add(cardioEquipment);

        List<Member> members = new ArrayList<>();
        FitnessClass class1 = new FitnessClass("yoga",
                LocalDateTime.of(2025, 8, 10, 10, 0),
                LocalDateTime.of(2025, 8, 10, 11, 0),
                trainer1, room1, 20, location, new ArrayList<>(), members, yogaList);
        class1.setId(1);

        FitnessClass class2 = new FitnessClass("yoga",
                LocalDateTime.of(2025, 9, 12, 10, 0),
                LocalDateTime.of(2025, 9, 12, 11, 0),
                trainer1, room1, 20, location, new ArrayList<>(), members, yogaList);
        class2.setId(2);

        FitnessClass class3 = new FitnessClass("cardio",
                LocalDateTime.of(2025, 10, 5, 10, 0),
                LocalDateTime.of(2025, 10, 5, 11, 0),
                trainer1, room1, 20, location, new ArrayList<>(), members, cardioList);
        class3.setId(3);

        FitnessClass class4 = new FitnessClass("yoga",
                LocalDateTime.of(2023, 8, 10, 10, 0),
                LocalDateTime.of(2023, 8, 10, 11, 0),
                trainer2, room1, 20, location, new ArrayList<>(), members, cardioList);
        class4.setId(4); // Past date

        // Add classes to the repository
        fitnessClassIRepository.create(class1);
        fitnessClassIRepository.create(class2);
        fitnessClassIRepository.create(class3);
        fitnessClassIRepository.create(class4);

        // Call method to get similar classes
        List<FitnessClass> result = service.getSimilarClasses(class1);

        // Assertions
        assertNotNull(result);
        assertEquals(1, result.size()); // Just class2 should be similar
        assertTrue(result.contains(class2));
        assertFalse(result.contains(class1)); // Target class should not be included
        assertFalse(result.contains(class3)); // Different trainer
        assertFalse(result.contains(class4)); // Past date
    }

    /**
     * Test the behavior of the `getSimilarClasses` method when no similar classes exist in the repository.
     * This test ensures that if no classes match the target class based on type, trainer, equipment, and future start time, the method returns an empty list.
     * The test sets up an unrelated class and checks that the result is empty.
     * Expected outcome: The method should return an empty list when no similar classes are found.
     */
    @Test
    void testGetSimilarClassesWithNoSimilarClasses() {
        // Set up the repository with no similar classes

        // Create unrelated class
        FitnessClass unrelatedClass = new FitnessClass("kickboxing",
                LocalDateTime.of(2025, 9, 12, 10, 0),
                LocalDateTime.of(2025, 9, 12, 11, 0),
                new Trainer("John Doe", "1234", "none"), new Room("room1", 30, new Location("Gym A", "123 Main St")),
                20, new Location("Gym A", "123 Main St"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        unrelatedClass.setId(5);

        // Add the unrelated class to the repository
        fitnessClassIRepository.create(unrelatedClass);

        // Target class is a yoga class
        FitnessClass targetClass = new FitnessClass("yoga",
                LocalDateTime.of(2024, 8, 10, 10, 0),
                LocalDateTime.of(2024, 8, 10, 11, 0),
                new Trainer("John Doe", "1234", "none"), new Room("room1", 30, new Location("Gym A", "123 Main St")),
                20, new Location("Gym A", "123 Main St"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        targetClass.setId(1);

        // Call the method to get similar classes
        List<FitnessClass> result = service.getSimilarClasses(targetClass);

        // Assertions
        assertNotNull(result);
        assertTrue(result.isEmpty()); // No similar classes
    }

    /**
     * Test the functionality of the `sortUpcomingClassesASC` method when there are upcoming classes.
     * This test verifies that the method correctly sorts the upcoming classes based on their start time in ascending order.
     * Expected outcome:
     * - The classes should be sorted in ascending order of their start time.
     * - The method should return a list with classes ordered correctly.
     */
    @Test
    void testSortUpcomingClassesASC() {
        // Setup upcoming classes with different start times
        Trainer trainer1 = new Trainer("John Doe", "1234", "none");
        Location location = new Location("Gym A", "123 Main St");
        Room room1 = new Room("room1", 30, location);

        List<Equipment> equipmentList = new ArrayList<>();
        equipmentList.add(new Equipment("Yoga Mat", 3, new ArrayList<>()));

        // Create classes with different start times
        FitnessClass class1 = new FitnessClass("yoga",
                LocalDateTime.of(2025, 8, 10, 10, 0),
                LocalDateTime.of(2025, 8, 10, 11, 0),
                trainer1, room1, 20, location, new ArrayList<>(), new ArrayList<>(), equipmentList);
        class1.setId(1);

        FitnessClass class2 = new FitnessClass("yoga",
                LocalDateTime.of(2025, 9, 12, 10, 0),
                LocalDateTime.of(2025, 9, 12, 11, 0),
                trainer1, room1, 20, location, new ArrayList<>(), new ArrayList<>(), equipmentList);
        class2.setId(2);

        FitnessClass class3 = new FitnessClass("yoga",
                LocalDateTime.of(2025, 7, 10, 10, 0),
                LocalDateTime.of(2025, 7, 10, 11, 0),
                trainer1, room1, 20, location, new ArrayList<>(), new ArrayList<>(), equipmentList);
        class3.setId(3);

        // Add classes to the repository
        fitnessClassIRepository.create(class1);
        fitnessClassIRepository.create(class2);
        fitnessClassIRepository.create(class3);

        // Call method to sort the upcoming classes
        List<FitnessClass> sortedClasses = service.sortUpcomingClassesASC();

        // Assertions
        assertNotNull(sortedClasses);
        assertEquals(3, sortedClasses.size()); // Should have all three classes
        assertSame(class3, sortedClasses.get(0)); // class3 should be first (earliest start time)
        assertSame(class1, sortedClasses.get(1)); // class1 should be second
        assertSame(class2, sortedClasses.get(2)); // class2 should be third
    }

    /**
     * Tests the scenario where the trainer has upcoming classes.
     * It verifies that the method returns the correct upcoming classes for the trainer,
     * excluding any past classes and ensuring it handles multiple upcoming classes.
     */
    @Test
    void testGetTrainerUpcomingClassesWithUpcomingClasses() {
        // Setup data for the test
        Trainer trainer1 = new Trainer("John Doe", "1234", "none");
        Trainer trainer2 = new Trainer("Jane Smith", "5678", "none");

        Location location = new Location("Gym A", "123 Main St");
        Room room1 = new Room("room1", 30, location);

        List<Equipment> equipmentList = new ArrayList<>();
        equipmentList.add(new Equipment("Yoga Mat", 3, new ArrayList<>()));

        // Create fitness classes for both trainers
        FitnessClass class1 = new FitnessClass("yoga",
                LocalDateTime.of(2025, 8, 10, 10, 0),
                LocalDateTime.of(2025, 8, 10, 11, 0),
                trainer1, room1, 20, location, new ArrayList<>(), new ArrayList<>(), equipmentList);
        class1.setId(1);

        FitnessClass class2 = new FitnessClass("yoga",
                LocalDateTime.of(2025, 9, 12, 10, 0),
                LocalDateTime.of(2025, 9, 12, 11, 0),
                trainer1, room1, 20, location, new ArrayList<>(), new ArrayList<>(), equipmentList);
        class2.setId(2);

        FitnessClass class3 = new FitnessClass("yoga",
                LocalDateTime.of(2024, 7, 10, 10, 0),
                LocalDateTime.of(2024, 7, 10, 11, 0),
                trainer2, room1, 20, location, new ArrayList<>(), new ArrayList<>(), equipmentList);
        class3.setId(3); // Past date

        // Add classes to the repository
        fitnessClassIRepository.create(class1);
        fitnessClassIRepository.create(class2);
        fitnessClassIRepository.create(class3);

        // Call method to get upcoming classes for trainer1
        List<FitnessClass> result = service.getTrainerUpcomingClasses(trainer1.getId());

        // Assertions
        assertNotNull(result);
        assertEquals(2, result.size()); // Two upcoming classes for trainer1
        assertTrue(result.contains(class1)); // class1 should be included
        assertTrue(result.contains(class2)); // class2 should be included
        assertFalse(result.contains(class3)); // class3 should not be included (past date)
    }

    /**
     * Tests the scenario where the trainer has no upcoming classes.
     * It ensures that the method returns an empty list when no upcoming classes exist.
     */
    @Test
    void testGetTrainerUpcomingClassesWithNoUpcomingClasses() {
        // Setup data for the test
        Trainer trainer1 = new Trainer("John Doe", "1234", "none");

        Location location = new Location("Gym A", "123 Main St");
        Room room1 = new Room("room1", 30, location);

        List<Equipment> equipmentList = new ArrayList<>();
        equipmentList.add(new Equipment("Yoga Mat", 3, new ArrayList<>()));

        // Create a past class for trainer1
        FitnessClass pastClass = new FitnessClass("yoga",
                LocalDateTime.of(2022, 8, 10, 10, 0),
                LocalDateTime.of(2022, 8, 10, 11, 0),
                trainer1, room1, 20, location, new ArrayList<>(), new ArrayList<>(), equipmentList);
        pastClass.setId(1);

        // Add class to the repository
        fitnessClassIRepository.create(pastClass);

        // Call method to get upcoming classes for trainer1 (expecting none)
        List<FitnessClass> result = service.getTrainerUpcomingClasses(trainer1.getId());

        // Assertions
        assertNotNull(result);
        assertTrue(result.isEmpty()); // No upcoming classes
    }

}
