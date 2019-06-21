package gbb.exceptions;

/**
 * Unchecked exception for processing a {@link gbb.State}.
 *
 * @author <a href="mailto:aurelian.hreapca@info.uaic.ro">Aurelian Hreapca</a> (created on 6/18/19)
 */
public class StateException extends RuntimeException {

    /**
     * Constructor for unchecked exception
     * with a specified message and a cause.
     * @param message specified message
     * @param cause the cause
     */
    public StateException(String message, Throwable cause) {
         super(message, cause);
    }
}
