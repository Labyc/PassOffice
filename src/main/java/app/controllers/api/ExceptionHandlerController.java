package app.controllers.api;

import app.ErrorResponse;
import app.exceptions.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {
    org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ExceptionHandlerController.class);

    @ExceptionHandler
    ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getClass().getSimpleName(), e.getMessage());
        log.error("{}\n{}", errorResponse, e);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        for (ObjectError error : e.getBindingResult().getAllErrors()) {
            if (error instanceof FieldError fieldError) {
                errorResponse.getErrors().add(new ErrorResponse.ResponseError(fieldError.getCode(),
                        String.format("Error in field '%s' with value '%s', it '%s'.",
                                fieldError.getField(), fieldError.getRejectedValue(), fieldError.getDefaultMessage())));
            } else {
                errorResponse.getErrors().add(new ErrorResponse.ResponseError("Unknown Error", error.toString()));
            }
        }
        log.error("{}\n{}", errorResponse, e);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
        ErrorResponse errorResponse = new ErrorResponse(e.getClass().getSimpleName(), e.getMessage());
        log.error("{}\n{}", errorResponse, e);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    ResponseEntity<ErrorResponse> handleAllExceptions(Throwable e){
        ErrorResponse errorResponse = new ErrorResponse(e.getClass().getSimpleName(), e.getMessage());
        log.error("{}\n{}", errorResponse, e);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
