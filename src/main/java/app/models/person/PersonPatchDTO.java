package app.models.person;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.UUID;

public record PersonPatchDTO(@Pattern(regexp = "\\p{Upper}\\w*") String name,
                             @Pattern(regexp = "\\p{Upper}\\w*") String surname,
                             @Pattern(regexp = "\\p{Upper}\\w*") String patronymic,
                             @Pattern(regexp = "\\p{Upper}.*") String placeOfBirth,
                             @PastOrPresent Date dateOfBirth,
                             @PastOrPresent Date dateOfDeath ) {

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
