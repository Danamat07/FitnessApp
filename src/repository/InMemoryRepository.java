package repository;
import model.HasId;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

/**
 * A generic in-memory repository implementation for managing entities of type T
 * that extends the Identifiable interface. This repository provides a simple
 * and efficient way to perform CRUD operations using an in-memory data structure.
 * @param <T> The type of objects managed by this repository. T must extend Identifiable.
 */
public class InMemoryRepository<T extends HasId> implements IRepository<T> {

    private final Map<Integer, T> storage = new HashMap<>();

    /**
     * Adds a new entity to the repository. The entity's ID must be unique.
     * @param obj The object to be added to the repository. Must not be null.
     * @throws IllegalArgumentException if an entity with the same ID already exists.
     */
    @Override
    public void create(T obj) {
        if (storage.containsKey(obj.getId())) {
            throw new IllegalArgumentException("Entity with ID " + obj.getId() + " already exists.");
        }
        storage.putIfAbsent(obj.getId(), obj);
    }

    /**
     * Retrieves an entity by its unique ID.
     * @param id The unique identifier of the entity to retrieve.
     * @return The entity of type T associated with the given ID, or null if no such entity exists.
     */
    @Override
    public T read(int id) {
        return storage.get(id);
    }

    /**
     * Updates an existing entity in the repository.
     * @param obj The object containing updated data. Its ID must already exist in the repository.
     * @throws IllegalArgumentException if the entity does not exist in the repository.
     */
    @Override
    public void update(T obj) {
        if (!storage.containsKey(obj.getId())) {
            throw new IllegalArgumentException("Entity with ID " + obj.getId() + " doesn't exist.");
        }
        storage.replace(obj.getId(), obj);
    }

    /**
     * Deletes an entity from the repository by its unique ID.
     * @param id The unique identifier of the entity to delete.
     * @throws IllegalArgumentException if no entity with the given ID exists.
     */
    @Override
    public void delete(int id) {
        if (!storage.containsKey(id)) {
            throw  new IllegalArgumentException("Entity with ID " + id + " doesn't exist.");
        }
        storage.remove(id);
    }

    /**
     * Retrieves all entities currently stored in the repository.
     *
     * @return A list of all entities managed by the repository. If no entities exist, returns an empty list.
     */
    @Override
    public List<T> getAll() {
        return new ArrayList<>(storage.values());
    }

}
