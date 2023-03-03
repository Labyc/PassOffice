package app.services;

import app.exceptions.EntityNotFoundException;
import app.models.passport.BasePassport;
import app.repositories.PassportRepository;
import app.repositories.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PassportService {

    PassportRepository passportRepository;
    PersonRepository personRepository;

    public BasePassport getPassport(String passportID) {
        return passportRepository.findById(passportID).orElseThrow(() -> new EntityNotFoundException(passportID));
    }

    public BasePassport createEntity(BasePassport passport) {
        if (personRepository.existsById(passport.getPersonId())) {
            return passportRepository.add(passport);
        }
        throw new EntityNotFoundException(passport.getPersonId());
    }

    public BasePassport updateEntity(String passportId, BasePassport toPassport) {
        BasePassport oldPassport = getPassport(passportId);
        BasePassport mergedPassport = oldPassport.merge(toPassport);
        if (personRepository.existsById(mergedPassport.getPersonId())) {
            return passportRepository.replace(passportId, oldPassport, mergedPassport);
        }
        throw new EntityNotFoundException(mergedPassport.getPersonId());
    }

    public void delete(String passportId) {
        passportRepository.deleteById(passportId).orElseThrow(() -> new EntityNotFoundException(passportId));
    }
}
