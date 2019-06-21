package gbb.exceptions;

/**
 * Unchecked exception for {@link gbb.Task}.
 *
 * @author <a href="mailto:aurelian.hreapca@info.uaic.ro">Aurelian Hreapca</a> (created on 6/18/19)
 */
public class TaskException extends RuntimeException {

    /**
     * Constructor for unchecked exception
     * with a specified message and a cause.
     * @param message specified message
     * @param cause the cause
     */
    public TaskException(String message, Throwable cause) {
        super(message, cause);
    }
}
