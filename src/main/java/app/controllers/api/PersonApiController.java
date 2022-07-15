package app.controllers.api;

import app.DataStorage;
import app.models.BasePassport;
import app.models.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/person")
@Slf4j
public class PersonApiController {

    @Autowired
    DataStorage dataStorage;

    @PostMapping
    public ResponseEntity<?> createPerson(@RequestBody @Validated Person person, Errors errors) {
        log.info("person: {}, {}", person.getName(), person.getSurname());
        dataStorage.addPerson(person);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @GetMapping("/{personId}") //parameters???
    public ResponseEntity<?> getPersonById(@PathVariable("personId") String personId){
        log.info("personId: {}", personId);
        return new ResponseEntity<>(personId, HttpStatus.OK);
    }
}
