package duke.exceptions;

/**
 * Class that handles all exceptions specific to Duke.
 *
 */
public class DukeException extends Exception {
    /**
     * Constructor to initialize all DukeExceptions.
     * @param message To be printed when error in Dukewu occurs.
     */
    public DukeException(String message) {
        super(message + '\n');
    }
}
