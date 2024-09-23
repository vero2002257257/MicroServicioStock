package MicroServicio.Stock.utils;

import MicroServicio.Stock.domain.exceptions.ProductNotFoundForBrandAndCategoryException;
import MicroServicio.Stock.domain.exceptions.ProductNotFoundForBrandException;
import MicroServicio.Stock.domain.exceptions.ProductNotFoundForCategoryException;
import MicroServicio.Stock.infrastructure.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(ProductNotFoundException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundForBrandException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundForBrandException(ProductNotFoundForBrandException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundForCategoryException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundForCategoryException(ProductNotFoundForCategoryException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundForBrandAndCategoryException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundForBrandAndCategoryException(ProductNotFoundForBrandAndCategoryException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()) {
        };
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
