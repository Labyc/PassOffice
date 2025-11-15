package app.controllers.api.passport;


import app.controllers.api.passport.dtos.output.BasePassportOutDTO;
import app.controllers.api.passport.dtos.patch.BasePassportPatchDTO;
import app.controllers.api.passport.dtos.post.BasePassportPostDTO;
import app.models.passport.BasePassport;
import app.services.PassportService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
public class PassportApiController {

    private final PassportService passportService;


    public PassportApiController(PassportService passportService) {
        this.passportService = Objects.requireNonNull(passportService);
    }

    @GetMapping("/{passportId}")
    public ResponseEntity<BasePassportOutDTO> getPassport(@PathVariable String passportId) {
        log.info("Get passport by passportId: '{}'", passportId);
        BasePassport passport = passportService.getPassport(passportId);
        log.info("Found passport:\n'{}'", passport);
        return new ResponseEntity<>(BasePassportOutDTO.fromPassport(passport), HttpStatus.OK);
    }

    @PostMapping
    public <T extends BasePassportPostDTO> ResponseEntity<BasePassportOutDTO> postPassport(@RequestBody @Valid T passportPostDTO) {
        log.info("Create new passport:\n'{}'", passportPostDTO);
        BasePassport createdPassport = passportService.createEntity(passportPostDTO.toPassport());
        log.info("Created new passport:\n'{}'", createdPassport);
        return new ResponseEntity<>(BasePassportOutDTO.fromPassport(createdPassport), HttpStatus.CREATED);
    }

    @PutMapping("/{passportId}")
    public ResponseEntity<BasePassportOutDTO> putPassport(@PathVariable String passportId, @RequestBody @Valid BasePassportPostDTO passportPostDTO) {
        log.info("Edit(put) passport:\n'{}', id '{}'", passportPostDTO, passportId);
        BasePassport editedPassport = passportService.updateEntity(passportId, passportPostDTO.toPassport());
        log.info("Edited(puted) passport:\n'{}'", editedPassport);
        return new ResponseEntity<>(BasePassportOutDTO.fromPassport(editedPassport), HttpStatus.OK);
    }

    @PatchMapping("/{passportId}")
    public ResponseEntity<BasePassportOutDTO> patchPassport(@PathVariable String passportId, @RequestBody @Valid BasePassportPatchDTO passportPatchDTO) {
        log.info("Edit(patch) passport:\n'{}', id '{}'", passportPatchDTO, passportId);
        BasePassport editedPassport = passportService.updateEntity(passportId, passportPatchDTO.toPassport());
        log.info("Edited(patched) passport:\n'{}'", editedPassport);
        return new ResponseEntity<>(BasePassportOutDTO.fromPassport(passportService.updateEntity(passportId, passportPatchDTO.toPassport())), HttpStatus.OK);
    }

    @DeleteMapping("/{passportId}")
    public ResponseEntity<?> deletePassport(@PathVariable String passportId) {
        log.info("Delete passport: '{}'", passportId);
        passportService.delete(passportId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
