package app.models.person;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.UUID;

public record PersonInDTO(@NotNull @Pattern(regexp = "\\p{Upper}\\w*") String name,
                          @NotNull @Pattern(regexp = "\\p{Upper}\\w*") String surname,
                          @Pattern(regexp = "\\p{Upper}\\w*") String patronymic,
                          @NotNull @Pattern(regexp = "\\p{Upper}.*") String placeOfBirth,
                          @NotNull @PastOrPresent /*@Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}") */Date dateOfBirth,
                          @PastOrPresent /*@Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}")*/ Date dateOfDeath ) {

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

    public Person toPerson(UUID existingPersonId, int version) {
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
