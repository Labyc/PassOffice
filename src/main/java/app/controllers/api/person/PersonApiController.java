package app.controllers.api.person;

import app.ErrorResponse;
import app.PersonProcessor;
import app.exceptions.EntityNotFoundException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@Controller
@RequestMapping("/person")
@Slf4j
@Tag(name = "Keyboard and some other devices API")
public class PersonApiController {      //TODO operate with DTO in controllers

    private final PersonProcessor personProcessor;

    public PersonApiController(@Autowired PersonProcessor personProcessor) {
        this.personProcessor = Objects.requireNonNull(personProcessor);
    }


    @PostMapping
    public ResponseEntity<PersonOutDTO> createPerson(@Valid @RequestBody PersonInDTO newPerson) {
        log.info("person: '{}'", newPerson.name());
        PersonOutDTO createdPerson = personProcessor.createEntity(newPerson);
        log.info("Created new person:\n'{}'", createdPerson);
        return new ResponseEntity<>(createdPerson, HttpStatus.CREATED);
    }

    @GetMapping("/{personId}")
    public ResponseEntity<PersonOutDTO> getPersonById(@PathVariable("personId") String personId) {
        log.info("Get person by personId: '{}'", personId);
        PersonOutDTO foundPerson = personProcessor.findById(personId);
        log.info("Found person:\n'{}'", foundPerson);
        return new ResponseEntity<>(foundPerson, HttpStatus.OK);
    }

    @PatchMapping("/{personId}")
    public ResponseEntity<PersonOutDTO> patchPersonById(@PathVariable("personId") String personId,
                                             @Valid @RequestBody PersonPatchDTO editedPerson) {
        log.info("Try patch person with id: '{}'", personId);
        PersonOutDTO patchedPerson = personProcessor.updateEntity(personId, editedPerson);
        log.info("Patched person:\n'{}'", patchedPerson);
        return new ResponseEntity<>(patchedPerson, HttpStatus.OK);
    }

    @PutMapping("/{personId}")
    public ResponseEntity<PersonOutDTO> putPersonById(@PathVariable("personId") String personId,
                                             @Valid @RequestBody PersonInDTO editedPerson) {
        log.info("Try put person with id: '{}'", personId);
        PersonOutDTO puttedPerson = personProcessor.replaceEntity(personId, editedPerson);
        log.info("Putted person:\n'{}'", puttedPerson);
        return new ResponseEntity<>(puttedPerson, HttpStatus.OK);
    }

    @DeleteMapping("/{personId}")
    public ResponseEntity<?> deletePersonById(@PathVariable("personId") String personId){
        log.info("Try delete person with id: '{}'", personId);
        if(personProcessor.deleteById(personId)!=null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); //Map returns deleted value, shouldn't we?
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        log.error("", e);
        ErrorResponse errorResponse = new ErrorResponse(e.getClass().getSimpleName(), e.getMessage());
        log.error(errorResponse.toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("", e);
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
        log.error(errorResponse.toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
        log.error("", e);
        ErrorResponse errorResponse = new ErrorResponse(e.getClass().getSimpleName(), e.getMessage());
        log.error(errorResponse.toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
