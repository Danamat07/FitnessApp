package repository;
import model.Identifiable;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class FileRepository<T extends Identifiable> implements IRepository<T> {

    private final String fileName;
    private final Class<T> type;

    // Constructor
    public FileRepository(String fileName, Class<T> type) {
        this.fileName = fileName;
        this.type = type;
    }

    // Add a new entity to the file
    @Override
    public void create(T obj) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(toCsv(obj));
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    // Read an entity from the file by its ID
    @Override
    public T read(int id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                T entity = fromCsv(line);
                // Check if the entity matches the given ID
                if (entity.getId() == id) {
                    return entity;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
        return null;  // Return null if no entity with the given ID is found
    }

    // Update an entity in the file
    @Override
    public void update(T obj) {
        List<T> allEntities = getAll();
        int id = obj.getId();  // Using the getId method from Identifiable

        // Replace the existing entity with the updated one
        for (int i = 0; i < allEntities.size(); i++) {
            if (allEntities.get(i).getId() == id) {
                allEntities.set(i, obj);
                break;
            }
        }

        // Re-write the file with the updated entities
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (T entity : allEntities) {
                writer.write(toCsv(entity));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error updating file: " + e.getMessage());
        }
    }

    // Delete an entity from the file by its ID
    @Override
    public void delete(int id) {
        List<T> allEntities = getAll();
        allEntities.removeIf(entity -> entity.getId() == id);

        // Re-write the file with the remaining entities
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (T entity : allEntities) {
                writer.write(toCsv(entity));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error deleting from file: " + e.getMessage());
        }
    }

    // Retrieve all entities from the file
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

    // Convert an object to a CSV string
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
        return csv.substring(0, csv.length() - 1);  // Remove last comma
    }

    // Convert a CSV string to an object
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

    // Convert a string value to the appropriate field type
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
        // Add other types as needed
        return value;
    }
}
