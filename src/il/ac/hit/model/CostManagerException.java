package il.ac.hit.model;

/**
 * This class makes exceptions specifically for this project.
 * @author Inbal mishal and Tal levi.
 */
public class CostManagerException extends Exception {
    /**
     * Create exception with the specified detail message.
     * @param message The reason of the error.
     */
    public CostManagerException(String message) {
        super(message);
    }

    /**
     * Create exception with the specified detail message and cause.
     * @param message The reason of the error.
     * @param cause Which caused the error.
     */
    public CostManagerException(String message, Throwable cause) {
        super(message, cause);
    }
}
