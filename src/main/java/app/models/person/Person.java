package app.models.person;

import lombok.With;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

public record Person(@Id @With String id, @With String name, @With String surname, @With String patronymic,
                     @With String placeOfBirth, @With LocalDate dateOfBirth,
                     @With LocalDate dateOfDeath, @With int version) {
}
