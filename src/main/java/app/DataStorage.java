package app;


import app.models.passport.BasePassport;
import app.models.person.Person;
import app.controllers.api.person.PersonInDTO;
import app.controllers.api.person.PersonOutDTO;
import app.controllers.api.person.PersonPatchDTO;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataStorage { //TODO implement interface and impl (inmemory/jdbs) +repository layer
    @Getter
    private List<BasePassport> passportsList = new ArrayList<>();
    @Getter
    private List<Person> personsList = new ArrayList<>();

    public PersonOutDTO addPerson(PersonInDTO newPersonIn){
        //TODO verify unexistent ID
        //newStoragePerson.setId(UUID.randomUUID());
        Person newPerson = newPersonIn.toPerson();
        personsList.add(newPerson);
        return PersonOutDTO.fromPerson(newPerson);
    }

    //TODO refactor
    public void addPassport(BasePassport passport){
        this.passportsList.add(passport);
    }

    //TODO refactor
    public BasePassport findExistingPassport(String passportId){
        for (BasePassport passport: passportsList){
            if (passport.getPassportId().equals(passportId)){
                return passport;
            }
        }
        throw new PassportNotFoundException( passportId );
    }

    public PersonOutDTO findPersonById(String personId){
        return PersonOutDTO.fromPerson(findStoragePersonById(personId));
    }

    private Person findStoragePersonById(String personId){
        for (Person person: personsList){
            if (person.id().toString().equals(personId)){
                return person;
            }
        }
        throw new PersonNotFoundException( personId );
    }
    public PersonOutDTO patchPerson(String personId, PersonPatchDTO editedPerson) {
        Person personToPatch = findStoragePersonById(personId);
        if(editedPerson.name() != null) personToPatch.withName(editedPerson.name());
        if(editedPerson.surname() != null) personToPatch.withSurname(editedPerson.surname());
        if(editedPerson.patronymic() != null) personToPatch.withPatronymic(editedPerson.patronymic());
        if(editedPerson.dateOfBirth() != null) personToPatch.withDateOfBirth(editedPerson.dateOfBirth());
        if(editedPerson.placeOfBirth() != null) personToPatch.withPlaceOfBirth(editedPerson.placeOfBirth());
        if(editedPerson.dateOfDeath() != null) personToPatch.withDateOfDeath(editedPerson.dateOfDeath());
        return PersonOutDTO.fromPerson(personToPatch);
    }

    public PersonOutDTO putPerson(String personId, PersonInDTO editedPerson) {
        Person person = findStoragePersonById(personId);
        person = editedPerson.toPerson(person.id(), person.version() + 1);
        return PersonOutDTO.fromPerson(person);
    }

    public boolean deletePersonById(String personId) {
        return personsList.remove(findStoragePersonById(personId));
    }
}
