package app.controllers.api.person;

import app.models.person.Person;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;


public record PersonInDTO(
        @NotNull @Pattern(regexp = "\\p{Lu}(\\p{L}|\\s)*") String name,
        @NotNull @Pattern(regexp = "\\p{Lu}(\\p{L}|\\s)*") String surname,
        @Pattern(regexp = "(\\p{Lu}(\\p{L}|\\s)*)|^(?![\\s\\S])") String patronymic,
        @NotNull @Pattern(regexp = "\\p{Lu}(\\p{L}|\\s)*") String placeOfBirth,
        @NotNull @PastOrPresent /*@Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}") */LocalDate dateOfBirth,
        @PastOrPresent /*@Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}")*/ LocalDate dateOfDeath) {

    public Person toPerson() {
        return new Person(null,
                name,
                surname,
                patronymic,
                placeOfBirth,
                dateOfBirth,
                dateOfDeath,
                0);
    }

    public Person toPerson(String existingPersonId, int version) {
        return new Person(existingPersonId,
                name,
                surname,
                patronymic,
                placeOfBirth,
                dateOfBirth,
                dateOfDeath,
                version);
    }
}
