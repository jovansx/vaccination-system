package akatsuki.immunizationsystem.exceptions;

public class BadRequestRuntimeException extends RuntimeException {
    public BadRequestRuntimeException(String message) {
        super(message);
    }
}
