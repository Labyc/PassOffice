package app.controllers.api.passport;


import app.controllers.api.passport.dtos.input.BasePassportPostDTO;
import app.services.PassportService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Slf4j
@RestController
@RequestMapping(value = "/passport", produces = "application/json")
@Tag(name = "get/add/switch/delete passport")
@Validated
public class PassportApiController{

    private final PassportService passportService;


    public PassportApiController(PassportService passportService) {
        this.passportService = Objects.requireNonNull(passportService);
    }


    @Deprecated
    @GetMapping("/passports")
    public ResponseEntity<?> getPassports(){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> postPassport(@RequestBody BasePassportPostDTO passportPostDTO){

        return new ResponseEntity<>(passportPostDTO, HttpStatus.CREATED);
    }

}
