package MicroServicio.Stock.domain.exceptions;

public class ArticleNotFoundForCategoryException extends RuntimeException {
  public ArticleNotFoundForCategoryException(String message) {
    super(message);
  }
}
