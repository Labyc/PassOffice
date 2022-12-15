package app.repositories;

import app.exceptions.EntityNotFoundException;
import app.exceptions.UpdatingObjectsIdsMismatchException;
import app.models.person.Person;
import jakarta.validation.constraints.NotNull;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.*;

@Component
public class PersonRepositoryInMemoryImplementation implements PersonRepository {

    Map<String, Person> personsMap = new HashMap<>();

    @Override
    public Person add(@NotNull Person person) {
        //TODO verify duplications???
        String personId;
        do {
            personId = UUID.randomUUID().toString();
        } while (personsMap.containsKey(personId));
        Person newPerson = person.withId(personId).withVersion(0);
        personsMap.put(personId, newPerson);
        return newPerson;
    }

    @Override
    public synchronized Person replace(String personId, Person oldPerson, Person newPerson) {
        if (personId == null) {
            throw new EntityNotFoundException("null");
        }
        if (!personId.equals(newPerson.id()) || !personId.equals(oldPerson.id())) {
            throw new UpdatingObjectsIdsMismatchException(personId, oldPerson.id(), newPerson.id());
        }
        if (!personsMap.replace(personId, oldPerson, newPerson)) {
            throw new ObjectOptimisticLockingFailureException(Person.class, personId);
        }
        return personsMap.get(personId);
    }

    @Override
    public Optional<Person> findById(String id) {
        return Optional.ofNullable(personsMap.get(id));
    }

    @Override
    public boolean existsById(String id) {
        return personsMap.containsKey(id);
    }

    @Override
    public Iterable<Person> findAll() {
        return personsMap.values();
    }

    @Override
    public long count() {
        return personsMap.size();
    }

    @Override
    public Optional<Person> deleteById(String id) {
        return Optional.of(personsMap.remove(id));
    }

    @Override
    public List<Person> findPerson(Optional<String> personName, Optional<String> surName, Optional<LocalDate> birthStartDate, Optional<LocalDate> birthEndDate) {
        return personsMap.values().stream()
                .filter(person -> personName.map(nameStr -> nameStr.equals(person.name())).orElse(true))
                .filter(person -> surName.map(surNameStr -> surNameStr.equals(person.surname())).orElse(true))
                .filter(person -> birthStartDate.map(startDate -> startDate.isBefore(person.dateOfBirth())).orElse(true))
                .filter(person -> birthEndDate.map(endDate -> endDate.isAfter(person.dateOfBirth())).orElse(true))
                .toList();
    }
}
