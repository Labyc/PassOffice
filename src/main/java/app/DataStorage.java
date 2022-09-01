package app;


import app.models.passport.BasePassport;
import app.models.person.Person;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataStorage {
    @Getter
    private List<BasePassport> passportsList = new ArrayList<>(); //TODO refactor to map<id, entity>???
    @Getter
    private List<Person.Storage> personsList = new ArrayList<>(); //TODO refactor to map<id, entity>???

    public Person.Response addPerson(Person.Request newPerson){
        Person.Storage newStoragePerson = new Person.Storage(newPerson);
        //TODO verify unexistent ID
        //newStoragePerson.setId(UUID.randomUUID());
        personsList.add(newStoragePerson);
        return new Person.Response(newStoragePerson);
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
        throw new EntityNotFoundException(String.format("Passport with id: '%s' not found.", passportId));
    }

    public Person.Response findPersonById(String personId){
        return new Person.Response(findStoragePersonById(personId));
    }

    private Person.Storage findStoragePersonById(String personId){
        for (Person.Storage person: personsList){
            if (person.getId().toString().equals(personId)){
                return person;
            }
        }
        throw new EntityNotFoundException(String.format("Person with id: '%s' not found.", personId));
    }
    public Person.Response patchPerson(String personId, Person.PatchRequest editedPerson) {
        Person.Storage personToPatch = findStoragePersonById(personId);
        personToPatch.patch(editedPerson);
        return new Person.Response(personToPatch);
    }

    public Person.Response putPerson(String personId, Person.Request editedPerson) {
        return this.patchPerson(personId, editedPerson);
    }

    public boolean deletePersonById(String personId) {
        return personsList.remove(findStoragePersonById(personId));
    }
}
