package MicroServicio.Stock.domain.exceptions;

public class DuplicateArticleNameException extends RuntimeException {
  public DuplicateArticleNameException(String message) {
    super(message);
  }
}
