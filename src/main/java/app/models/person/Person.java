package app.models.person;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class Person {
    @Setter
    UUID id;
    String name;
    String surname;
    String patronymic;
    String placeOfBirth;
    Date dateOfBirth;
    Date dateOfDeath;
    @Setter
    int version;

    public void patch(Person patchedPerson) {
        //if (this.version == patchedPerson.version) //todo
        this.version++;
        if (!this.name.equals(patchedPerson.name) && patchedPerson.name != null)
            this.name = patchedPerson.name;
        if (!this.surname.equals(patchedPerson.surname) && patchedPerson.surname != null)
            this.surname = patchedPerson.surname;
        if (!this.patronymic.equals(patchedPerson.patronymic))
            this.patronymic = patchedPerson.patronymic;
        if (!this.placeOfBirth.equals(patchedPerson.placeOfBirth) && patchedPerson.placeOfBirth != null)
            this.placeOfBirth = patchedPerson.placeOfBirth;
        if (!this.dateOfBirth.equals(patchedPerson.dateOfBirth) && patchedPerson.dateOfBirth != null)
            this.dateOfBirth = patchedPerson.dateOfBirth;
        if (!this.dateOfDeath.equals(patchedPerson.dateOfDeath))
            this.dateOfDeath = patchedPerson.dateOfDeath;

    }

}
