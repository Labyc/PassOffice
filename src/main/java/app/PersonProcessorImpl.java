package app;


import app.controllers.api.person.PersonInDTO;
import app.controllers.api.person.PersonOutDTO;
import app.controllers.api.person.PersonPatchDTO;
import app.exceptions.EntityNotFoundException;
import app.models.person.Person;
import app.repositories.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class PersonProcessorImpl implements PersonProcessor {
    private final PersonRepository personRepository;

    @Override
    public PersonOutDTO createEntity(PersonInDTO personRequestDTO) {
        Person newPerson = personRequestDTO.toPerson();
        newPerson = personRepository.add(newPerson);
        return PersonOutDTO.fromPerson(newPerson);
    }

    @Override
    public PersonOutDTO updateEntity(String personId, PersonPatchDTO personRequestDTO) {
        Person oldPerson = personRepository.findById(personId).orElseThrow(() -> new EntityNotFoundException(personId));
        Person requestPerson = personRequestDTO.toPerson();
        Person mergedPerson = new Person(
                oldPerson.id(),
                requestPerson.name() == null ? oldPerson.name() : requestPerson.name(),
                requestPerson.surname() == null ? oldPerson.surname() : requestPerson.surname(),
                requestPerson.patronymic() == null ? oldPerson.patronymic() : requestPerson.patronymic(),
                requestPerson.placeOfBirth() == null ? oldPerson.placeOfBirth() : requestPerson.placeOfBirth(),
                requestPerson.dateOfBirth() == null ? oldPerson.dateOfBirth() : requestPerson.dateOfBirth(),
                requestPerson.dateOfDeath() == null ? oldPerson.dateOfDeath() : requestPerson.dateOfDeath(),
                oldPerson.version() + 1);
        if (requestPerson.dateOfDeath() != null && requestPerson.dateOfDeath().equals(LocalDate.MIN)) {
            mergedPerson = mergedPerson.withDateOfDeath(null);
        }
        return PersonOutDTO.fromPerson(personRepository.replace(personId, oldPerson, mergedPerson));
    } //TODO make jackson understand null and no field

    @Override
    public PersonOutDTO replaceEntity(String personId, PersonInDTO personRequestDTO) {
        Person oldPerson = personRepository.findById(personId).orElseThrow(() -> new EntityNotFoundException(personId));
        Person requestPerson = personRequestDTO.toPerson(personId, oldPerson.version());
        return PersonOutDTO.fromPerson(personRepository.replace(personId, oldPerson, requestPerson));
    }

    @Override
    public PersonOutDTO findById(String personId) {
        return PersonOutDTO.fromPerson(personRepository.findById(personId).orElseThrow(() -> new EntityNotFoundException(personId)));
    }

    @Override
    public PersonOutDTO deleteById(String personId) {
        return PersonOutDTO.fromPerson(personRepository.deleteById(personId).orElseThrow(() -> new EntityNotFoundException(personId)));
    }

    @Override
    public List<PersonOutDTO> findPerson(Optional<String> personName, Optional<String> surName, Optional<LocalDate> birthStartDate, Optional<LocalDate> birthEndDate) {
        return personRepository.findPerson(personName, surName, birthStartDate, birthEndDate).stream().map(PersonOutDTO::fromPerson).toList();
    }
}