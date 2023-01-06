package app.controllers.api.person;

import app.models.person.Person;
import com.fasterxml.jackson.annotation.JsonSetter;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import org.apache.logging.log4j.util.Strings;

import java.time.LocalDate;

public record PersonPatchDTO(@Pattern(regexp = "\\p{Lu}(\\p{L}|\\s)*") String name,
                             @Pattern(regexp = "\\p{Lu}(\\p{L}|\\s)*") String surname,
                             @Pattern(regexp = "(\\p{Lu}(\\p{L}|\\s)*)|^(?![\\s\\S])") String patronymic,
                             @Pattern(regexp = "\\p{Lu}(\\p{L}|\\s)*") String placeOfBirth,
                             @PastOrPresent LocalDate dateOfBirth,
                             @PastOrPresent LocalDate dateOfDeath ) {

    public Person toPerson() {
        return new Person(Strings.EMPTY,
                name,
                surname,
                patronymic,
                placeOfBirth,
                dateOfBirth,
                dateOfDeath,
                0);
    }

    public Person toPerson(String existingPersonId) {
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
