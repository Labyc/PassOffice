package app.controllers.api.person;

import app.models.person.Person;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public record PersonOutDTO(UUID id, String name, String surname, String patronymic, String placeOfBirth, LocalDate dateOfBirth, LocalDate dateOfDeath) {
    public static PersonOutDTO fromPerson(Person person){
        return new PersonOutDTO(person.id(), person.name(), person.surname(), person.patronymic(), person.placeOfBirth(), person.dateOfBirth(), person.dateOfDeath());
    }

}
