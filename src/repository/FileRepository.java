package repository;

import model.Identifiable;

import java.io.*;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;

/**
 * A generic file-based repository implementation for managing entities of type T
 * that extends the Identifiable interface. The repository provides CRUD operations
 * and stores data in a CSV-like format in a file, with an in-memory HashMap for caching.
 * @param <T> The type of objects managed by this repository. T must extend Identifiable.
 */
public class FileRepository<T extends Identifiable> implements IRepository<T> {

    private final String fileName;
    private final Class<T> type;
    private final Map<Integer, T> storage = new HashMap<>();

    /**
     * Constructor for creating a FileRepository instance.
     * @param fileName The name of the file used for storing the data.
     * @param type     The Class type of the objects to be managed.
     */
    public FileRepository(String fileName, Class<T> type) {
        this.fileName = fileName;
        this.type = type;
        loadFromFile(); // Populate storage from file during initialization.
    }

    /**
     * Adds a new entity of type T to the storage and file.
     * @param obj The object to be added. Must not be null.
     */
    @Override
    public void create(T obj) {
        if (storage.containsKey(obj.getId())) {
            throw new IllegalArgumentException("Entity with ID " + obj.getId() + " already exists.");
        }
        storage.put(obj.getId(), obj);
        saveToFile();
    }

    /**
     * Reads and retrieves an entity from the storage by its unique ID.
     * @param id The unique identifier of the object to retrieve.
     * @return The object of type T with the specified ID, or null if not found.
     */
    @Override
    public T read(int id) {
        return storage.get(id);
    }

    /**
     * Updates an existing entity in the storage and saves changes to the file.
     * @param obj The object containing updated data. Must already exist in the storage.
     */
    @Override
    public void update(T obj) {
        if (!storage.containsKey(obj.getId())) {
            throw new IllegalArgumentException("Entity with ID " + obj.getId() + " does not exist.");
        }
        storage.put(obj.getId(), obj);
        saveToFile();
    }

    /**
     * Deletes an entity from the storage and file by its unique ID.
     * @param id The unique identifier of the object to delete.
     */
    @Override
    public void delete(int id) {
        if (storage.remove(id) == null) {
            throw new IllegalArgumentException("Entity with ID " + id + " does not exist.");
        }
        saveToFile();
    }

    /**
     * Retrieves all entities of type T from the storage.
     * @return A list of all objects stored in the storage.
     */
    @Override
    public List<T> getAll() {
        return new ArrayList<>(storage.values());
    }

    /**
     * Saves all objects from the storage to the file in CSV format.
     */
    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (T obj : storage.values()) {
                writer.write(toCsv(obj));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving to file: " + e.getMessage());
        }
    }

    /**
     * Loads all objects from the file into the storage HashMap.
     */
    private void loadFromFile() {
        storage.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                T obj = fromCsv(line);
                if (obj != null) {
                    storage.put(obj.getId(), obj);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName + ". Starting with an empty repository.");
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
    }

    /**
     * Converts an object of type T into a CSV string representation.
     * @param obj The object to convert to CSV.
     * @return A string in CSV format representing the object.
     */
    private String toCsv(T obj) {
        StringBuilder csv = new StringBuilder();
        Field[] fields = type.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(obj);
                if (value instanceof LocalDateTime) {
                    csv.append(((LocalDateTime) value).toString()).append(",");
                } else if (value instanceof Identifiable) {
                    csv.append(((Identifiable) value).getId()).append(","); // Save only the ID
                } else {
                    csv.append(value).append(",");
                }
            } catch (IllegalAccessException e) {
                System.err.println("Error accessing field: " + e.getMessage());
            }
        }
        return csv.substring(0, csv.length() - 1);
    }


    /**
     * Converts a CSV string into an object of type T.
     * @param line The CSV string to convert.
     * @return An object of type T with values populated from the CSV string, or null in case of errors.
     */
    private T fromCsv(String line) {
        try {
            T obj = type.getDeclaredConstructor().newInstance();
            String[] values = line.split(",");
            Field[] fields = type.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                fields[i].set(obj, convertValue(fields[i].getType(), values[i]));
            }
            return obj;
        } catch (Exception e) {
            //System.err.println("Error converting from CSV: " + type.getSimpleName() + " -> " + e.getMessage());
            return null; // Skip problematic line
        }
    }



    /**
     * Converts a string value to the appropriate type based on the field type.
     * @param fieldType The Class type of the field.
     * @param value     The string value to convert.
     * @return The converted value as an Object, or the original string if no conversion is applicable.
     */
    private Object convertValue(Class<?> fieldType, String value) {
        if (fieldType == int.class || fieldType == Integer.class) {
            return Integer.parseInt(value);
        } else if (fieldType == double.class || fieldType == Double.class) {
            return Double.parseDouble(value);
        } else if (fieldType == boolean.class || fieldType == Boolean.class) {
            return Boolean.parseBoolean(value);
        } else if (fieldType == String.class) {
            return value;
        } else if (fieldType == LocalDateTime.class) {
            return LocalDateTime.parse(value); // Ensure the CSV uses ISO-8601 format
        } else if (Identifiable.class.isAssignableFrom(fieldType)) {
            // Handle associations with other entities (e.g., Trainer, Member, etc.)
            int id = Integer.parseInt(value); // Assume the value is an ID
            return new FileRepository<>(fieldType.getSimpleName() + ".csv", (Class<Identifiable>) fieldType).read(id);
        }
        throw new IllegalArgumentException("Unsupported field type: " + fieldType.getName());
    }

}
