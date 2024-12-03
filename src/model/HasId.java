/**
 * Represents an entity that has a unique identifier.
 * This interface provides methods for getting and setting the unique ID of an object.
 * It extends the {@link Serializable} interface to allow implementing objects to be serialized,
 * enabling persistence and data transfer.
 */
package model;

import java.io.Serializable;

public interface HasId extends Serializable {
    /**
     * Retrieves the unique identifier of the object.
     * @return The integer ID of the object.
     */
    int getId();

    /**
     * Sets the unique identifier for the object.
     * @param id The integer ID to be set.
     */
    void setId(int id);
}
