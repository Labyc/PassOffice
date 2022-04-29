package app;


import app.models.Passport;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataStorage {
    @Getter
    private List<Passport> passportList = new ArrayList<>(); //TODO refactor to map???

    public void addPassport(Passport passport){
        this.passportList.add(passport);
    }

    public Passport findExistingPassport(String series, String number){
        for (Passport passport: passportList){
            if (passport.getSeries().equals(series) && passport.getNumber().equals(number)){
                return passport;
            }
        }
        return null; //bad idea
    }
}
