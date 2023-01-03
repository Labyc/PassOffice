package app;

import app.exceptions.EntityNotFoundException;
import app.models.person.Person;
import app.repositories.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@AllArgsConstructor
public class PersonProcessor {
    private final PersonRepository personRepository;

    public Person createEntity(Person person) {
        return personRepository.add(person);
    }

    public Person updateEntity(String personId, Person requestPerson) {
        Person oldPerson = personRepository.findById(personId).orElseThrow(() -> new EntityNotFoundException(personId));
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
        return personRepository.replace(personId, oldPerson, mergedPerson);
    } //TODO make jackson understand null and no field

    public Person replaceEntity(String personId, Person requestPerson) {
        Person oldPerson = personRepository.findById(personId).orElseThrow(() -> new EntityNotFoundException(personId));
        requestPerson = requestPerson.withId(personId).withVersion(oldPerson.version());
        return personRepository.replace(personId, oldPerson, requestPerson);
    }

    public Person findById(String personId) {
        return personRepository.findById(personId).orElseThrow(() -> new EntityNotFoundException(personId));
    }

    public Person deleteById(String personId) {
        return personRepository.deleteById(personId).orElseThrow(() -> new EntityNotFoundException(personId));
    }

    public List<Person> findPerson(String personName, String surName, LocalDate birthStartDate, LocalDate birthEndDate) {
        return personRepository.findPerson(personName, surName, birthStartDate, birthEndDate);
    }
}