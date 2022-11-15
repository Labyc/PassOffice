package app.repositories;

import app.exceptions.PersonNotFoundException;
import app.exceptions.UpdatingObjectsIdsMismatchException;
import app.models.person.Person;
import lombok.Synchronized;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.*;

@Component
public class PersonRepositoryInMemoryImplementation implements EntityRepository<Person, UUID> {

    Map<UUID, Person> personsMap = new HashMap<>();

    @Override
    public Person add(Person person) {
        Assert.notNull(person, "Creatable person must not be null!");
        //verify duplications???
        UUID personId = UUID.randomUUID();
        while (personsMap.containsKey(personId))
            personId = UUID.randomUUID();
        Person newPerson = new Person(personId, person.name(), person.surname(), person.patronymic(), person.placeOfBirth(), person.dateOfBirth(), person.dateOfDeath(), 0);
        personsMap.put(personId, newPerson);
        return newPerson;
    }

    @Override
    @Synchronized
    public synchronized Person replace(UUID personId, Person oldPerson, Person newPerson) {
        if (personId==null){
            throw new PersonNotFoundException("null");
        }
        if (!personId.equals(newPerson.id()) || !personId.equals(oldPerson.id())) {  //???
            throw new UpdatingObjectsIdsMismatchException(personId, oldPerson.id(), newPerson.id());
        }
        if (!personsMap.replace(personId, oldPerson, newPerson)) { //looks like this should guarantee that if the entity was changed in parallel, so it throws an exception
            throw new ObjectOptimisticLockingFailureException(Person.class, personId);
        }
        return personsMap.get(personId);
    }

    @Override
    public Optional<Person> findById(UUID uuid) {
        return personsMap.containsKey(uuid) ? Optional.ofNullable(personsMap.get(uuid)) : Optional.empty();
    }

    @Override
    public boolean existsById(UUID uuid) {
        return personsMap.containsKey(uuid);
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
    public Person deleteById(UUID uuid) {
        return personsMap.remove(uuid);
    }
}
