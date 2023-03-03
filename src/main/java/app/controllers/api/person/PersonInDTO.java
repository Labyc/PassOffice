package app.controllers.api.person;

import app.models.person.Person;
import com.fasterxml.jackson.annotation.JsonSetter;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;


public record PersonInDTO(
        @NotNull @Pattern(regexp = "\\p{Lu}(\\p{L}|\\s)*") String name,
        @NotNull @Pattern(regexp = "\\p{Lu}(\\p{L}|\\s)*") String surname,
        @Pattern(regexp = "(\\p{Lu}(\\p{L}|\\s)*)|^(?![\\s\\S])") String patronymic,
        @NotNull @Pattern(regexp = "\\p{Lu}(\\p{L}|\\s)*") @JsonSetter String placeOfBirth,
        @NotNull @PastOrPresent LocalDate dateOfBirth,
        @PastOrPresent LocalDate dateOfDeath) {

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
