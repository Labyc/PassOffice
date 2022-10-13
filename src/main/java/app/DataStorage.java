package app;


import app.models.passport.BasePassport;
import app.models.person.Person;
import app.models.person.PersonInDTO;
import app.models.person.PersonOutDTO;
import app.models.person.PersonPatchDTO;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class DataStorage {
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
            if (person.getId().toString().equals(personId)){
                return person;
            }
        }
        throw new PersonNotFoundException( personId );
    }
    public PersonOutDTO patchPerson(String personId, PersonPatchDTO editedPerson) {
        Person personToPatch = findStoragePersonById(personId);
        personToPatch.patch(editedPerson.toPerson(personToPatch.getId()));
        return PersonOutDTO.fromPerson(personToPatch);
    }

    public PersonOutDTO putPerson(String personId, PersonInDTO editedPerson) {
        Person person = findStoragePersonById(personId);
        person = editedPerson.toPerson(person.getId(), person.getVersion() + 1);
        return PersonOutDTO.fromPerson(person);
    }

    public boolean deletePersonById(String personId) {
        return personsList.remove(findStoragePersonById(personId));
    }
}
