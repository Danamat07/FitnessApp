package tests;

import Helpers.HelperFunctions;
import model.HasId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.InMemoryRepository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * Unit tests for the InMemoryRepository class.
 * These tests validate the behavior of CRUD operations (Create, Read, Update, Delete)
 * for the InMemoryRepository class using HasId objects.
 */
public class InMemoryRepoTests {

    private InMemoryRepository<HasId> repository;

    /**
     * Set up a new repository instance before each test.
     */
    @BeforeEach
    public void setUp() {
        repository = new InMemoryRepository<>();
    }

    /**
     * Tests the create operation for adding a new entity to the repository.
     * Verifies that the entity is correctly added and retrieved.
     */
    @Test
    public void testCreateEntity() {
        // Create an object with a unique ID
        HasId entity = new HasId() {
            private int id = HelperFunctions.randomId();
            @Override public int getId() {return id;}
            @Override public void setId(int id) {this.id = id;}
        };
        // Add the entity
        repository.create(entity);
        // Verify the entity was added correctly
        HasId retrievedEntity = repository.read(entity.getId());
        assertNotNull(retrievedEntity);
        assertEquals(entity.getId(), retrievedEntity.getId());
    }

    /**
     * Tests the create operation when attempting to add an entity with a duplicate ID.
     * Verifies that an exception is thrown.
     */
    @Test
    public void testCreateEntityWithDuplicateId() {
        // Create two entities with the same ID
        HasId entity1 = new HasId() {
            private int id = HelperFunctions.randomId();
            @Override public int getId() {return id;}
            @Override public void setId(int id) {this.id = id;}
        };
        HasId entity2 = new HasId() {
            private int id = entity1.getId(); // Same ID as entity1
            @Override public int getId() {return id;}
            @Override public void setId(int id) {this.id = id;}
        };
        repository.create(entity1);
        // Test adding the entity with the duplicate ID
        assertThrows(IllegalArgumentException.class, () -> {
            repository.create(entity2);
        });
    }

    /**
     * Tests the read operation for retrieving an entity by its ID.
     * Verifies that the correct entity is returned.
     */
    @Test
    public void testReadEntity() {
        HasId entity = new HasId() {
            private int id = HelperFunctions.randomId();
            @Override public int getId() {return id;}
            @Override public void setId(int id) {this.id = id;}
        };
        repository.create(entity);
        HasId retrievedEntity = repository.read(entity.getId());
        assertNotNull(retrievedEntity);
        assertEquals(entity.getId(), retrievedEntity.getId());
    }

    /**
     * Tests the read operation when attempting to retrieve a non-existent entity.
     * Verifies that null is returned for non-existent entities.
     */
    @Test
    public void testReadNonExistentEntity() {
        HasId entity = repository.read(9999);
        assertNull(entity);
    }

    /**
     * Tests the update operation for modifying an existing entity in the repository.
     * Verifies that the entity is correctly updated.
     */
    @Test
    public void testUpdateEntity() {
        HasId entity = new HasId() {
            private int id = HelperFunctions.randomId();
            @Override public int getId() {return id;}
            @Override public void setId(int id) {this.id = id;}
        };
        repository.create(entity);
        // Update the entity
        HasId updatedEntity = new HasId() {
            private int id = entity.getId();
            @Override public int getId() {return id;}
            @Override public void setId(int id) {this.id = id;}
        };
        repository.update(updatedEntity);
        HasId retrievedEntity = repository.read(updatedEntity.getId());
        assertNotNull(retrievedEntity);
        assertEquals(updatedEntity.getId(), retrievedEntity.getId());
    }

    /**
     * Tests the update operation when attempting to update an entity that does not exist in the repository.
     * Verifies that an exception is thrown.
     */
    @Test
    public void testUpdateNonExistentEntity() {
        HasId entity = new HasId() {
            private int id = 9999;
            @Override public int getId() {return id;}
            @Override public void setId(int id) {this.id = id;}
        };
        assertThrows(IllegalArgumentException.class, () -> {
            repository.update(entity);
        });
    }

    /**
     * Tests the delete operation for removing an entity from the repository.
     * Verifies that the entity is correctly deleted.
     */
    @Test
    public void testDeleteEntity() {
        HasId entity = new HasId() {
            private int id = HelperFunctions.randomId();
            @Override public int getId() {return id;}
            @Override public void setId(int id) {this.id = id;}
        };
        repository.create(entity);
        repository.delete(entity.getId());
        HasId retrievedEntity = repository.read(entity.getId());
        assertNull(retrievedEntity);
    }

    /**
     * Tests the delete operation when attempting to delete an entity that does not exist.
     * Verifies that an exception is thrown.
     */
    @Test
    public void testDeleteNonExistentEntity() {
        assertThrows(IllegalArgumentException.class, () -> {
            repository.delete(9999);
        });
    }

    /**
     * Tests the getAll operation for retrieving all entities in the repository.
     * Verifies that the correct number of entities is returned.
     */
    @Test
    public void testGetAllEntities() {
        HasId entity1 = new HasId() {
            private int id = HelperFunctions.randomId();
            @Override public int getId() {return id;}
            @Override public void setId(int id) {this.id = id;}
        };
        HasId entity2 = new HasId() {
            private int id = HelperFunctions.randomId();
            @Override public int getId() {return id;}
            @Override public void setId(int id) {this.id = id;}
        };
        repository.create(entity1);
        repository.create(entity2);
        List<HasId> allEntities = repository.getAll();
        assertEquals(2, allEntities.size());
    }

    /**
     * Tests the getAll operation when the repository is empty.
     * Verifies that an empty list is returned.
     */
    @Test
    public void testGetAllEntitiesWhenEmpty() {
        List<HasId> allEntities = repository.getAll();
        assertTrue(allEntities.isEmpty());
    }
}
