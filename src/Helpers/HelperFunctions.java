/**
 * Utility class providing helper methods for common operations.
 * This class is intended to group reusable utility functions, such as generating random IDs.
 * It uses a static `Random` instance to ensure efficient random number generation.
 */
package Helpers;

import java.util.Random;

public class HelperFunctions {
    private static final Random random = new Random();

    /**
     * Generates a random integer ID.
     * This method produces a random integer between 0 (inclusive) and 10,000 (exclusive),
     * which can be used as a unique identifier in various contexts.
     *
     * @return A random integer between 0 and 9999.
     */
    public static int randomId() {
        return random.nextInt(10000);
    }
}
