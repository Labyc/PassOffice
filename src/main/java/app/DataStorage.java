package app;


import app.models.BasePassport;
import app.models.Person;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataStorage {
    @Getter
    private List<BasePassport> passportsList = new ArrayList<>(); //TODO refactor to map???
    @Getter
    private List<Person> personsList = new ArrayList<>(); //TODO refactor to map???

    public boolean addPerson(Person newPerson){
        for (Person person: personsList){
            if(person.getId().equals(newPerson.getId()))
                return false;
        }
        personsList.add(newPerson);
        return true;
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

    public Person findPerson(int personId){
        for (Person person: personsList){
            if (person.getId()==personId){
                return person;
            }
        }
        throw new EntityNotFoundException(String.format("Person with id: '%s' not found.", personId));
    }
}
