package repository;

import model.Identifiable;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class InMemoryRepository<T extends Identifiable> implements IRepository<T> {
    private final Map<Integer, T> storage = new HashMap<>();

    @Override
    public void create(T obj) {
        storage.put(obj.getID(), obj);
    }

    @Override
    public T read(int id) {
        return storage.get(id);
    }

    @Override
    public void update(T obj) {
        storage.put(obj.getID(), obj);
    }

    @Override
    public void delete(int id) {
        storage.remove(id);
    }

    @Override
    public List<T> getAll() {
        return new ArrayList<>(storage.values());
    }

}
