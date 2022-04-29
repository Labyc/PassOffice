package app.controllers.api;

import app.DataStorage;
import app.models.Passport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/api")
public class PassportAPIController {

    @Autowired
    DataStorage dataStorage;

    @GetMapping("/passports")
    public ResponseEntity<?> getPassports(){
        return new ResponseEntity<>(dataStorage, HttpStatus.OK);
    }

    @PostMapping("/passport")
    public ResponseEntity<?> postPassport(@Valid Passport passport, Errors errors){
        if (errors.hasErrors()){
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        if (dataStorage.findExistingPassport(passport.getSeries(), passport.getNumber())!=null){ //TODO some more validations
            return new ResponseEntity<>("Passport with the same series and number is already exists" ,HttpStatus.NOT_ACCEPTABLE); //TODO normal exception (in Json format)???
        }
        dataStorage.addPassport(passport);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
