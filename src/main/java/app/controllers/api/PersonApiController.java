package app.controllers.api;

import app.DataStorage;
import app.models.Person;
import app.models.PersonInDTO;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
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
    public ResponseEntity<?> createPerson(@Validated @RequestBody PersonInDTO newPerson, Errors errors) {
        log.info("person: {}, {}", newPerson.getName(), newPerson.getSurname());
        dataStorage.addPerson(newPerson);
        return new ResponseEntity<>(newPerson, HttpStatus.OK);
    }

    @GetMapping("/{personId}")
    public ResponseEntity<?> getPersonById(@PathVariable("personId") @Positive int personId){
        log.info("Get person by personId: '{}'", personId);
        Person foundPerson = dataStorage.findPerson(personId);
        log.debug("Found person: '{}'", foundPerson);
        return new ResponseEntity<>(personId, HttpStatus.OK);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException e) {
        log.error("Error ID: '', error message: '{}'", e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
