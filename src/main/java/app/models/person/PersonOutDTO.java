package app.models.person;

import java.util.Date;
import java.util.UUID;

public record PersonOutDTO(UUID id, String name, String surname, String patronymic, String placeOfBirth, Date dateOfBirth, Date dateOfDeath) {
    public static PersonOutDTO fromPerson(Person person){
        return new PersonOutDTO(person.getId(), person.getName(), person.getSurname(), person.getPatronymic(), person.getPlaceOfBirth(), person.getDateOfBirth(), person.getDateOfDeath());
    }

}
