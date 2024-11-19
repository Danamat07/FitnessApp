package model;

/**
 * The Identifiable interface provides a contract for objects that can be uniquely
 * identified by an integer ID. Classes implementing this interface must provide
 * methods to get and set the ID of the object.
 */
public interface Identifiable {
    /**
     * Retrieves the unique ID of the object.
     * @return The unique identifier of the object.
     */
    int getId();

    /**
     * Sets the unique ID of the object.
     * @param id The unique identifier to be assigned to the object.
     */
    void setId(int id);
}
