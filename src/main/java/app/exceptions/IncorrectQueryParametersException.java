package app.exceptions;

public class IncorrectQueryParametersException extends RuntimeException{

    public IncorrectQueryParametersException(String message){
        super(message);
    }
}
