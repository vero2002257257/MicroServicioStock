package MicroServicio.Stock.domain.exceptions;

public class ProductNotFoundForBrandException extends RuntimeException {
  public ProductNotFoundForBrandException(String message) {
    super(message);
  }
}
