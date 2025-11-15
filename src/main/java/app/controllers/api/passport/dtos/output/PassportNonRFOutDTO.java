package app.controllers.api.passport.dtos.output;

import app.models.passport.PassportType;
import lombok.Value;

import java.time.LocalDate;

@Value
public class PassportNonRFOutDTO extends BasePassportOutDTO {
    private final String country;

    public PassportNonRFOutDTO(
            PassportType passportType,
            String passportID,
            String personId,
            String number,
            LocalDate givenDate,
            String givenDepartment,
            String country) {
        super(passportType, passportID, personId, number, givenDate, givenDepartment);
        this.country = country;
    }
}
