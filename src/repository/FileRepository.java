package repository;

import model.HasId;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Provides a file-based repository for managing objects of type T.
 * This class uses serialization to store and retrieve objects from a file.
 * @param <T> The type of objects to be stored in the repository, which must implement the HasId interface.
 */
public class FileRepository<T extends HasId> implements IRepository<T> {

    /**
     * The path to the file where data is stored.
     */
    private final String filePath;

    /**
     * Constructs a FileRepository instance with the given file path.
     * @param filePath The file path to be used for data storage.
     */
    public FileRepository(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Creates a new object in the repository.
     * Adds the object if it does not already exist in the file.
     * @param obj The object to be added.
     */
    @Override
    public void create(T obj) {
        doInFile(data -> data.putIfAbsent(obj.getId(), obj));
    }

    /**
     * Reads an object from the repository by its ID.
     * @param id The ID of the object to be retrieved.
     * @return The object with the specified ID, or null if no such object exists.
     */
    @Override
    public T read(int id) {
        return readDataFromFile().get(id);
    }

    /**
     * Updates an existing object in the repository.
     * Replaces the object with the same ID in the file.
     * @param obj The updated object to replace the existing entry.
     */
    @Override
    public void update(T obj) {
        doInFile(data -> data.replace(obj.getId(), obj));
    }

    /**
     * Deletes an object from the repository by its ID.
     * @param id The ID of the object to be deleted.
     */
    @Override
    public void delete(int id) {
        doInFile(data -> data.remove(id));
    }

    /**
     * Retrieves all objects from the repository.
     * @return A list of all objects currently stored in the repository.
     */
    @Override
    public List<T> getAll() {
        return readDataFromFile().values().stream().toList();
    }

    /**
     * Performs a modification on the in-memory data and writes the changes back to the file.
     * @param function A consumer that modifies the in-memory data map.
     */
    private void doInFile(Consumer<Map<Integer, T>> function) {
        Map<Integer, T> data = readDataFromFile();
        function.accept(data);
        writeDataToFile(data);
    }

    /**
     * Reads all data from the file into a Map.
     * If the file does not exist or is empty, an empty map is returned.
     * @return A map containing all objects read from the file, keyed by their ID.
     */
    private Map<Integer, T> readDataFromFile() {
        File file = new File(filePath);
        if (!file.exists()) {
            return new HashMap<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (Map<Integer, T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    /**
     * Writes all data from the given Map into the file.
     * @param data The data map to be serialized and written to the file.
     */
    private void writeDataToFile(Map<Integer, T> data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}