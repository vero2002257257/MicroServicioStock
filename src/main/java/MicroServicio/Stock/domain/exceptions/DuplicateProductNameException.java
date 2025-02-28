package MicroServicio.Stock.domain.exceptions;

public class DuplicateProductNameException extends RuntimeException {
  public DuplicateProductNameException(String message) {
    super(message);
  }
}
