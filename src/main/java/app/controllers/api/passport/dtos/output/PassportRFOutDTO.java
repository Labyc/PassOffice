package app.controllers.api.passport.dtos.output;

import app.models.passport.PassportType;
import lombok.Value;

import java.time.LocalDate;

@Value
public class PassportRFOutDTO extends BasePassportOutDTO {

    private final String series;
    private final String givenDepartmentCode;

    public PassportRFOutDTO(
            PassportType passportType,
            String passportID,
            String personId,
            String series,
            String number,
            LocalDate givenDate,
            String givenDepartment,
            String givenDepartmentCode) {
        super(passportType, passportID, personId, number, givenDate, givenDepartment);
        this.series = series;
        this.givenDepartmentCode = givenDepartmentCode;
    }
}
