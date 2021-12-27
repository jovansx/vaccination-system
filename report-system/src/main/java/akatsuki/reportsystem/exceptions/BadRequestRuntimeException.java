package akatsuki.reportsystem.exceptions;

public class BadRequestRuntimeException extends RuntimeException {
    public BadRequestRuntimeException(String message) {
        super(message);
    }
}
