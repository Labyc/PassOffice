package app.models.person;

import lombok.AllArgsConstructor;
import lombok.With;

import java.time.LocalDate;
import java.util.UUID;

public record Person(UUID id, @With String name, @With String surname, @With String patronymic, @With String placeOfBirth, @With LocalDate dateOfBirth,
                     @With LocalDate dateOfDeath, @With int version) {

}
