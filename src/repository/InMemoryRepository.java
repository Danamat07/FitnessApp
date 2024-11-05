package repository;
import model.Identifiable;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class InMemoryRepository<T extends Identifiable> implements IRepository<T> {

    private final Map<Integer, T> storage = new HashMap<>();

    // Add a new entity to the repository if its ID doesn't already exist
    @Override
    public void create(T obj) {
        if (storage.containsKey(obj.getID())) {
            throw new IllegalArgumentException("Entity with ID " + obj.getID() + " already exists.");
        }
        storage.putIfAbsent(obj.getID(), obj);
    }

    // Get an entity by ID
    @Override
    public T read(int id) {
        return storage.get(id);
    }

    // Updates an existing entity
    @Override
    public void update(T obj) {
        if (!storage.containsKey(obj.getID())) {
            throw new IllegalArgumentException("Entity with ID " + obj.getID() + " doesn't exist.");
        }
        storage.replace(obj.getID(), obj);
    }

    // Delete entity by ID
    @Override
    public void delete(int id) {
        if (!storage.containsKey(id)) {
            throw  new IllegalArgumentException("Entity with ID " + id + " doesn't exist.");
        }
        storage.remove(id);
    }

    // Get all repo-managed entities
    @Override
    public List<T> getAll() {
        return new ArrayList<>(storage.values());
    }

}
