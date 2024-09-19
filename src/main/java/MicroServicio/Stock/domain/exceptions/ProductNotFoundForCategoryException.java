package MicroServicio.Stock.domain.exceptions;

public class ProductNotFoundForCategoryException extends RuntimeException {
  public ProductNotFoundForCategoryException(String message) {
    super(message);
  }
}
