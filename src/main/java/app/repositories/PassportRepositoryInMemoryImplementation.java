package app.repositories;

import app.exceptions.EntityNotFoundException;
import app.exceptions.UpdatingObjectsIdsMismatchException;
import app.models.passport.BasePassport;
import app.models.person.Person;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
public class PassportRepositoryInMemoryImplementation implements PassportRepository {
    Map<String, BasePassport> passportsMap = new HashMap<>();

    @Override
    public synchronized BasePassport add(BasePassport passport) {
        String passportId;
        do {
            passportId = UUID.randomUUID().toString();
        } while (passportsMap.containsKey(passportId));
        BasePassport newPassport = passport.withPassportID(passportId);
        passportsMap.put(passportId, newPassport);
        return newPassport;
    }

    @Override
    public synchronized BasePassport replace(String passportId, BasePassport oldPassport, BasePassport newPassport) {
        if (passportId == null || !passportsMap.containsKey(passportId)) {
            throw new EntityNotFoundException(passportId);
        }
        if (!passportId.equals(newPassport.getPassportID()) || !passportId.equals(oldPassport.getPassportID())) {
            throw new UpdatingObjectsIdsMismatchException(passportId, oldPassport.getPassportID(), newPassport.getPassportID());
        }
        if (!passportsMap.replace(passportId, oldPassport, newPassport)) {
            throw new ObjectOptimisticLockingFailureException(Person.class, passportId);
        }
        return passportsMap.get(passportId);
    }

    @Override
    public Optional<BasePassport> findById(String passportId) {
        return Optional.ofNullable(passportsMap.get(passportId));
    }

    @Override
    public boolean existsById(String passportId) {
        return passportsMap.containsKey(passportId);
    }

    @Override
    public Iterable<BasePassport> findAll() {
        return passportsMap.values();
    }

    @Override
    public long count() {
        return passportsMap.size();
    }

    @Override
    public Optional<BasePassport> deleteById(String passportId) {
        return Optional.ofNullable(passportsMap.remove(passportId));
    }
}
