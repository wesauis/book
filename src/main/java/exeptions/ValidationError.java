package exeptions;

/**
 *
 * @author w2gam
 */
public class ValidationError extends RuntimeException {

    public ValidationError(String message) {
        super(message);
    }
    
}
