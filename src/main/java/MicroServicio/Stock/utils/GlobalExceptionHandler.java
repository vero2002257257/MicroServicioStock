package MicroServicio.Stock.utils;

import MicroServicio.Stock.domain.exceptions.ArticleNotFoundForBrandAndCategoryException;
import MicroServicio.Stock.domain.exceptions.ArticleNotFoundForBrandException;
import MicroServicio.Stock.domain.exceptions.ArticleNotFoundForCategoryException;
import MicroServicio.Stock.infrastructure.exception.ArticleNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ArticleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleArticleNotFoundException(ArticleNotFoundException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ArticleNotFoundForBrandException.class)
    public ResponseEntity<ErrorResponse> handleArticleNotFoundForBrandException(ArticleNotFoundForBrandException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ArticleNotFoundForCategoryException.class)
    public ResponseEntity<ErrorResponse> handleArticleNotFoundForCategoryException(ArticleNotFoundForCategoryException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ArticleNotFoundForBrandAndCategoryException.class)
    public ResponseEntity<ErrorResponse> handleArticleNotFoundForBrandAndCategoryException(ArticleNotFoundForBrandAndCategoryException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()) {
        };
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
