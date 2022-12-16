package app.repositories;

import app.models.person.Person;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PersonRepository extends EntityRepository<Person, String>{
    List<Person> findPerson(Optional<String> personName, Optional<String> surName, Optional<LocalDate> birthStartDate, Optional<LocalDate> birthEndDate);

}
