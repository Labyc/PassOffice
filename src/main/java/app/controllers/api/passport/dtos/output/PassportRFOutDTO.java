package app.controllers.api.passport.dtos.output;

import app.models.passport.PassportType;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class PassportRFOutDTO extends BasePassportOutDTO{

    private final String series;
    private final String givenDepartmentCode;

    public PassportRFOutDTO(
            @NotNull PassportType passportType,
            @NotNull String passportID,
            @NotNull String personId,
            @Digits(integer = 4, fraction = 0, message = "Invalid Passport series") String series,
            @Digits(integer = 6, fraction = 0, message = "Invalid Passport number") String number,
            @NotNull @PastOrPresent LocalDate givenDate,
            String givenDepartmentCode,
            @NotNull @Pattern(regexp = "\\p{Upper}\\w*") String givenDepartment,
            List<String> otherPassportIds ) {
        super(passportType, passportID, personId, number, givenDate, givenDepartment, otherPassportIds);
        this.series = series;
        this.givenDepartmentCode = givenDepartmentCode;
    }
}
