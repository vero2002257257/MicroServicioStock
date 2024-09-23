package MicroServicio.Stock.domain.exceptions;

public class ProductNotFoundForBrandAndCategoryException extends RuntimeException {
  public ProductNotFoundForBrandAndCategoryException(String message) {
    super(message);
  }
}
