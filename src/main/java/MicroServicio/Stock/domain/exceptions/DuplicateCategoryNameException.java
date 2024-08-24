package MicroServicio.Stock.domain.exceptions;

public class DuplicateCategoryNameException extends RuntimeException{
    public DuplicateCategoryNameException(String message){
        super(message);
    }
}
