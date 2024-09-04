package MicroServicio.Stock.domain.exceptions;

public class DuplicateBrandNameException extends RuntimeException {
    public DuplicateBrandNameException(String message) {
        super(message);
    }
}
