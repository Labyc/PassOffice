package app.controllers.api.person;

import app.PersonProcessor;
import app.controllers.api.ExceptionHandlingController;
import app.exceptions.IncorrectQueryParametersException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PastOrPresent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@Controller
@RequestMapping(value = "/person", produces = "application/json")
@Slf4j
@Tag(name = "get/add/edit/delete Person info")
@Validated
public class PersonApiController implements ExceptionHandlingController {

    private final PersonProcessor personProcessor;

    public PersonApiController(@Autowired PersonProcessor personProcessor) {
        this.personProcessor = Objects.requireNonNull(personProcessor);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<PersonOutDTO> createPerson(@Valid @RequestBody PersonInDTO newPerson) {
        log.info("person: '{}'", newPerson.name());
        PersonOutDTO createdPerson = PersonOutDTO.fromPerson(personProcessor.createEntity(newPerson.toPerson()));
        log.info("Created new person:\n'{}'", createdPerson);
        return new ResponseEntity<>(createdPerson, HttpStatus.CREATED);
    }

    @GetMapping("/{personId}")
    public ResponseEntity<PersonOutDTO> getPersonById(@PathVariable("personId") String personId) {
        log.info("Get person by personId: '{}'", personId);
        PersonOutDTO foundPerson = PersonOutDTO.fromPerson(personProcessor.findById(personId));
        log.info("Found person:\n'{}'", foundPerson);
        return new ResponseEntity<>(foundPerson, HttpStatus.OK);
    }

    @PatchMapping(path = "/{personId}", consumes = "application/json")
    public ResponseEntity<PersonOutDTO> patchPersonById(@PathVariable("personId") String personId,
                                                        @Valid @RequestBody PersonPatchDTO editedPerson) {
        log.info("Try patch person with id: '{}'", personId);
        PersonOutDTO patchedPerson = PersonOutDTO.fromPerson(personProcessor.updateEntity(personId, editedPerson.toPerson()));
        log.info("Patched person:\n'{}'", patchedPerson);
        return new ResponseEntity<>(patchedPerson, HttpStatus.OK);
    }

    @PutMapping(path = "/{personId}", consumes = "application/json")
    public ResponseEntity<PersonOutDTO> putPersonById(@PathVariable("personId") String personId,
                                                      @Valid @RequestBody PersonInDTO editedPerson) {
        log.info("Try put person with id: '{}'", personId);
        PersonOutDTO puttedPerson = PersonOutDTO.fromPerson(personProcessor.replaceEntity(personId, editedPerson.toPerson()));
        log.info("Putted person:\n'{}'", puttedPerson);
        return new ResponseEntity<>(puttedPerson, HttpStatus.OK);
    }

    @DeleteMapping("/{personId}")
    public ResponseEntity<?> deletePersonById(@PathVariable("personId") String personId) {
        log.info("Try delete person with id: '{}'", personId);
        if (personProcessor.deleteById(personId) != null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); //Map returns deleted value, shouldn't we?
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping
    public ResponseEntity<List<PersonOutDTO>> findPerson(
            @RequestParam("personName") Optional<String> personName, @RequestParam("personSurname") Optional<String> surName,
            @RequestParam("birthStartDate") Optional<@PastOrPresent LocalDate> birthStartDate,
            @RequestParam("birthEndDate") Optional<@PastOrPresent LocalDate> birthEndDate) {
        if (personName.isEmpty() && surName.isEmpty() && birthStartDate.isEmpty() && birthEndDate.isEmpty()) {
            throw new IncorrectQueryParametersException("At least one of query parameters must not be null.");
        }
        if (birthStartDate.isPresent() && birthEndDate.isPresent() && birthStartDate.get().isAfter(birthEndDate.get())) {
            throw new IncorrectQueryParametersException("Start date must be before the end date.");
        }
        log.info("Try find person with parameters: Name='{}', Surname='{}', birthStartDate='{}', birthEndDate='{}'", personName, surName, birthStartDate, birthEndDate);
        List<PersonOutDTO> foundPersons = personProcessor
                .findPerson(personName.orElse(null), surName.orElse(null), birthStartDate.orElse(null), birthEndDate.orElse(null))
                .stream().map(PersonOutDTO::fromPerson).toList();
        log.info("Found person: '{}'", foundPersons);
        return new ResponseEntity<>(foundPersons, HttpStatus.OK);
    }
}
