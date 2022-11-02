package app.controllers.api.person;

import app.models.person.Person;

import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public record PersonPatchDTO(@Pattern(regexp = "\\p{Upper}\\w*") String name,
                             @Pattern(regexp = "\\p{Upper}\\w*") String surname,
                             @Pattern(regexp = "\\p{Upper}\\w*") String patronymic,
                             @Pattern(regexp = "\\p{Upper}.*") String placeOfBirth,
                             @PastOrPresent LocalDate dateOfBirth,
                             @PastOrPresent LocalDate dateOfDeath ) {

    public Person toPerson() {
        return new Person(UUID.randomUUID(),
                name,
                surname,
                patronymic,
                placeOfBirth,
                dateOfBirth,
                dateOfDeath,
                0);
    }

    public Person toPerson(UUID existingPersonId) {
        return new Person(existingPersonId,
                name,
                surname,
                patronymic,
                placeOfBirth,
                dateOfBirth,
                dateOfDeath,
                0);
    }
}
