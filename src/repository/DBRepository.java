package repository;

import model.HasId;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Abstract class that provides a base for database repositories.
 * This class is responsible for managing the connection to the database and is used as a foundation for repositories
 * that interact with specific types of objects in the database.
 * It implements the {@link IRepository} interface and {@link AutoCloseable} to ensure the proper management of database resources.
 * @param <T> The type of objects this repository handles, which must implement the {@link HasId} interface.
 */
public abstract class DBRepository<T extends HasId> implements IRepository<T>, AutoCloseable {

    /**
     * The database connection used by the repository to interact with the database.
     */
    protected final Connection connection;

    /**
     * Constructs a DBRepository instance with the provided database connection details.
     * Establishes a connection to the database using the specified URL, user, and password.
     * @param dbUrl      The URL of the database to connect to.
     * @param dbUser     The username to use when connecting to the database.
     * @param dbPassword The password to use when connecting to the database.
     * @throws RuntimeException If the connection cannot be established due to an SQLException.
     */
    public DBRepository(String dbUrl, String dbUser, String dbPassword) {
        try {
            // Initialize database connection
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

            // Optionally validate connection
            if (connection.isClosed() || !connection.isValid(2)) {
                throw new RuntimeException("The database connection is invalid or closed");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to the database", e);
        }
    }

    /**
     * Closes the database connection when the repository is no longer needed.
     * This method is called automatically when using the try-with-resources statement.
     * @throws Exception If an error occurs while closing the connection.
     */
    @Override
    public void close() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

}