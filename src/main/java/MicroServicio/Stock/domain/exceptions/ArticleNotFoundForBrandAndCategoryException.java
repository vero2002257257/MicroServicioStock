package MicroServicio.Stock.domain.exceptions;

public class ArticleNotFoundForBrandAndCategoryException extends RuntimeException {
  public ArticleNotFoundForBrandAndCategoryException(String message) {
    super(message);
  }
}
