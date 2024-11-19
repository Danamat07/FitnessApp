package repository;
import model.Identifiable;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * A generic file-based repository implementation for managing entities of type T
 * that extends the Identifiable interface. The repository provides CRUD operations
 * and stores data in a CSV-like format in a file.
 * @param <T> The type of objects managed by this repository. T must extend Identifiable.
 */
public class FileRepository<T extends Identifiable> implements IRepository<T> {

    private final String fileName;
    private final Class<T> type;

    /**
     * Constructor for creating a FileRepository instance.
     * @param fileName The name of the file used for storing the data.
     * @param type     The Class type of the objects to be managed.
     */
    public FileRepository(String fileName, Class<T> type) {
        this.fileName = fileName;
        this.type = type;
    }

    /**
     * Adds a new entity of type T to the file.
     * @param obj The object to be added. Must not be null.
     */
    @Override
    public void create(T obj) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(toCsv(obj));
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * Reads and retrieves an entity from the file by its unique ID.
     * @param id The unique identifier of the object to retrieve.
     * @return The object of type T with the specified ID, or null if not found.
     */
    @Override
    public T read(int id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                T entity = fromCsv(line);
                if (entity.getId() == id) {
                    return entity;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
        return null;
    }

    /**
     * Updates an existing entity in the file by replacing it with a new version.
     * @param obj The object containing updated data. Must already exist in the file.
     */
    @Override
    public void update(T obj) {
        List<T> allEntities = getAll();
        int id = obj.getId();
        for (int i = 0; i < allEntities.size(); i++) {
            if (allEntities.get(i).getId() == id) {
                allEntities.set(i, obj);
                break;
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (T entity : allEntities) {
                writer.write(toCsv(entity));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error updating file: " + e.getMessage());
        }
    }

    /**
     * Deletes an entity from the file by its unique ID.
     * @param id The unique identifier of the object to delete.
     */
    @Override
    public void delete(int id) {
        List<T> allEntities = getAll();
        allEntities.removeIf(entity -> entity.getId() == id);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (T entity : allEntities) {
                writer.write(toCsv(entity));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error deleting from file: " + e.getMessage());
        }
    }

    /**
     * Retrieves all entities of type T from the file.
     * @return A list of all objects stored in the file. Returns an empty list if no objects are found.
     */
    @Override
    public List<T> getAll() {
        List<T> entities = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                entities.add(fromCsv(line));
            }
        } catch (IOException e) {
            System.err.println("Error reading all from file: " + e.getMessage());
        }
        return entities;
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
                csv.append(field.get(obj)).append(",");
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
            System.err.println("Error converting from CSV: " + e.getMessage());
        }
        return null;
    }

    /**
     * Converts a string value to the appropriate type based on the field type.
     * @param fieldType The Class type of the field.
     * @param value     The string value to convert.
     * @return The converted value as an Object, or the original string if no conversion is applicable.
     */
    private Object convertValue(Class<?> fieldType, String value) {
        if (fieldType == int.class) {
            return Integer.parseInt(value);
        } else if (fieldType == double.class) {
            return Double.parseDouble(value);
        } else if (fieldType == String.class) {
            return value;
        } else if (fieldType == boolean.class) {
            return Boolean.parseBoolean(value);
        }
        return value;
    }
}
