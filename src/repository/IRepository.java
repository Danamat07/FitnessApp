package repository;

import java.util.List;

/**
 * The IRepository interface defines a generic contract for basic CRUD (Create, Read, Update, Delete)
 * operations and fetching all entities. It can be implemented for different types of objects.
 * @param <T> The type of objects that this repository will manage.
 */
public interface IRepository<T> {

    /**
     * Creates a new entity of type T in the repository.
     * @param obj The object to be created. Must not be null.
     */
    void create(T obj);

    /**
     * Reads and retrieves an entity of type T by its unique identifier.
     * @param id The unique identifier of the object to be retrieved.
     * @return The object of type T associated with the specified ID,
     *         or null if no such object exists.
     */
    T read(int id);

    /**
     * Updates an existing entity of type T in the repository.
     * @param obj The object containing updated information.
     *            The object must already exist in the repository.
     */
    void update(T obj);

    /**
     * Deletes an entity of type T from the repository by its unique identifier.
     * @param id The unique identifier of the object to be deleted.
     */
    void delete(int id);

    /**
     * Retrieves all entities of type T from the repository.
     * @return A list of all objects of type T. If no objects exist, returns an empty list.
     */
    List<T> getAll();
}
