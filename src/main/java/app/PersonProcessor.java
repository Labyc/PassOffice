package app;

import app.controllers.api.person.PersonInDTO;
import app.controllers.api.person.PersonOutDTO;
import app.controllers.api.person.PersonPatchDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PersonProcessor extends EntityProcessor<PersonPatchDTO, PersonInDTO, PersonOutDTO> {
    List<PersonOutDTO> findPerson(Optional<String> personName, Optional<String> surName, Optional<LocalDate> birthStartDate, Optional<LocalDate> birthEndDate);

}
