package app.controllers.api.person;

import app.DataStorage;
import app.ErrorResponse;
import app.PersonNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<?> createPerson(@Valid @RequestBody PersonInDTO newPerson) {
        log.info("person: {}, {}", newPerson.name());
        PersonOutDTO createdPerson = dataStorage.addPerson(newPerson);
        return new ResponseEntity<>(createdPerson, HttpStatus.CREATED);
    }

    @GetMapping("/{personId}")
    public ResponseEntity<?> getPersonById(@PathVariable("personId") String personId) {
        log.info("Get person by personId: '{}'", personId);
        PersonOutDTO foundPerson = dataStorage.findPersonById(personId);
        log.debug("Found person: '{}'", foundPerson);
        return new ResponseEntity<>(foundPerson, HttpStatus.OK);
    }

    @PatchMapping("/{personId}")
    public ResponseEntity<?> patchPersonById(@PathVariable("personId") String personId,
                                             @Valid @RequestBody PersonPatchDTO editedPerson) {
        log.info("Try patch person with id: '{}'", personId);
        PersonOutDTO patchedPerson = dataStorage.patchPerson(personId, editedPerson);
        return new ResponseEntity<>(patchedPerson, HttpStatus.OK);
    }

    @PutMapping("/{personId}")
    public ResponseEntity<?> putPersonById(@PathVariable("personId") String personId,
                                             @Valid @RequestBody PersonInDTO editedPerson) {
        log.info("Try put person with id: '{}'", personId);
        PersonOutDTO puttedPerson = dataStorage.putPerson(personId, editedPerson);
        return new ResponseEntity<>(puttedPerson, HttpStatus.OK);
    }

    @DeleteMapping("/{personId}")
    public ResponseEntity<?> deletePersonById(@PathVariable("personId") String personId){
        log.info("Try delete person with id: '{}'", personId);
        if(dataStorage.deletePersonById(personId))
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(PersonNotFoundException e) {
        log.error("", e);
        ErrorResponse response = new ErrorResponse(new ErrorResponse.ResponseError("PERSON_NOT_FOUND", "Can't find person with id " + e.getPersonId()));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
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
        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
