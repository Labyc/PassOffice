package app.controllers.api;

import app.DataStorage;
import app.ErrorResponse;
import app.PersonNotFoundException;
import app.models.person.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Controller
@RequestMapping("/person")
@Slf4j
public class PersonApiController {

    private final DataStorage dataStorage;

    public PersonApiController(DataStorage dataStorage) {
        this.dataStorage = Objects.requireNonNull(dataStorage);
    }


    @PostMapping
    public ResponseEntity<?> createPerson(@Valid @RequestBody Person.Request newPerson) {
        log.info("person: {}, {}", newPerson.getName());
        Person.Response createdPerson = dataStorage.addPerson(newPerson);
        return new ResponseEntity<>(createdPerson, HttpStatus.CREATED);
    }

    @GetMapping("/{personId}")
    public ResponseEntity<?> getPersonById(@PathVariable("personId") @Valid @NotNull @NotBlank String personId) {
        log.info("Get person by personId: '{}'", personId);
        Person.Response foundPerson = dataStorage.findPersonById(personId);
        log.debug("Found person: '{}'", foundPerson);
        return new ResponseEntity<>(foundPerson, HttpStatus.OK);
    }

    @PatchMapping("/{personId}")
    public ResponseEntity<?> patchPersonById(@PathVariable("personId") @Valid @NotNull @NotBlank String personId,
                                             @Valid @RequestBody Person.PatchRequest editedPerson) {
        log.info("Try patch person with id: '{}'", personId);
        Person.Response patchedPerson = dataStorage.patchPerson(personId, editedPerson);
        return new ResponseEntity<>(patchedPerson, HttpStatus.OK);
    }

    @PutMapping("/{personId}")
    public ResponseEntity<?> putPersonById(@PathVariable("personId") @Valid @NotNull @NotBlank String personId,
                                             @Valid @RequestBody Person.Request editedPerson) {
        log.info("Try put person with id: '{}'", personId);
        Person.Response puttedPerson = dataStorage.putPerson(personId, editedPerson);
        return new ResponseEntity<>(puttedPerson, HttpStatus.OK);
    }

    @DeleteMapping("/{personId}")
    public ResponseEntity<?> deletePersonById(@PathVariable("personId") @Valid @NotNull @NotBlank String personId){
        log.info("Try delete person with id: '{}'", personId);
        if(dataStorage.deletePersonById(personId))
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException e) {
        log.error("Error ID: '', error message: '{}'", e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(PersonNotFoundException e) {
        log.error("", e);
        ErrorResponse response = new ErrorResponse(new ErrorResponse.ResponseError("PERSON_NOT_FOUND", "Can't find person with id " + e.getPersonId()));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
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
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
