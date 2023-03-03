package app.controllers.api.passport.dtos.output;

import app.models.passport.PassportType;
import lombok.Value;

import java.time.LocalDate;

@Value
public class PassportRFForeignOutDTO extends BasePassportOutDTO {
    private final LocalDate expirationDate;

    public PassportRFForeignOutDTO(
            PassportType passportType,
            String passportID,
            String personId,
            String number,
            LocalDate givenDate,
            String givenDepartment,
            LocalDate expirationDate) {
        super(passportType, passportID, personId, number, givenDate, givenDepartment);
        this.expirationDate = expirationDate;
    }
}
