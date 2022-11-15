package app;


import app.controllers.api.person.PersonInDTO;
import app.controllers.api.person.PersonOutDTO;
import app.controllers.api.person.PersonPatchDTO;
import app.exceptions.PersonNotFoundException;
import app.models.person.Person;
import app.repositories.EntityRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class PersonProcessor implements EntityProcessor<PersonPatchDTO, PersonInDTO, PersonOutDTO> {
    private final EntityRepository<Person, UUID> personRepository;

    @Override
    public PersonOutDTO createEntity(PersonInDTO personRequestDTO) {
        Person newPerson = personRequestDTO.toPerson();
        newPerson = personRepository.add(newPerson);
        return PersonOutDTO.fromPerson(newPerson);
    }

    @Override
    public PersonOutDTO updateEntity(UUID personId, PersonPatchDTO personRequestDTO) {
        if (!personRepository.existsById(personId)) {
            throw new PersonNotFoundException(personId.toString());
        }
        Person oldPerson = personRepository.findById(personId).get();
        Person requestPerson = personRequestDTO.toPerson();
        Person mergedPerson = new Person(
                oldPerson.id(),
                requestPerson.name() == null || requestPerson.name().equals("") ? oldPerson.name() : requestPerson.name(),
                requestPerson.surname() == null || requestPerson.surname().equals("") ? oldPerson.surname() : requestPerson.surname(),
                requestPerson.patronymic() == null ? oldPerson.patronymic() : requestPerson.patronymic(),
                requestPerson.placeOfBirth() == null ? oldPerson.placeOfBirth() : requestPerson.placeOfBirth(),
                requestPerson.dateOfBirth() == null ? oldPerson.dateOfBirth() : requestPerson.dateOfBirth(),
                requestPerson.dateOfDeath() == null || requestPerson.dateOfDeath().equals(LocalDate.MIN) ? oldPerson.dateOfDeath() : requestPerson.dateOfDeath(),
                oldPerson.version() + 1);

        return PersonOutDTO.fromPerson(personRepository.replace(personId, oldPerson, mergedPerson));
    } //TODO make jackson understand null and no field

    @Override
    public PersonOutDTO updateEntity(String personId, PersonPatchDTO personRequestDTO) {
        return this.updateEntity(UUID.fromString(personId), personRequestDTO);
    }


    @Override
    public PersonOutDTO replaceEntity(UUID personId, PersonInDTO personRequestDTO) {
        return personRepository.findById(personId).ifPresentOrElse(person -> {
            Person requestPerson = personRequestDTO.toPerson(personId, person.version());
            PersonOutDTO.fromPerson(personRepository.replace(personId, person, requestPerson));
        }, () -> {
            throw new PersonNotFoundException(personId.toString());
        });
    }

    @Override
    public PersonOutDTO findById(UUID personId) {
        Optional<Person> person = personRepository.findById(personId);
        if (person.isEmpty()) {
            throw new PersonNotFoundException(personId.toString());
        }
        return PersonOutDTO.fromPerson(person.get());
    }

    @Override
    public PersonOutDTO findById(String personId) {
        return this.findById(UUID.fromString(personId));
    }

    @Override
    public PersonOutDTO deleteById(String personId) {
        return PersonOutDTO.fromPerson(personRepository.deleteById(UUID.fromString(personId)));
    }


}
