package MicroServicio.Stock.infrastructure.exception;

public class DuplicateBrandNameException extends RuntimeException {
    public DuplicateBrandNameException(String message) {
        super(message);
    }
}
