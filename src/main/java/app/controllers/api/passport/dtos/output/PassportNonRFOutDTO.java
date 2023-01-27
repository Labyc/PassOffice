package app.controllers.api.passport.dtos.output;

import app.models.passport.PassportType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.util.List;

public class PassportNonRFOutDTO extends BasePassportOutDTO{
    private final String country;
    public PassportNonRFOutDTO(
            @NotNull PassportType passportType,
            @NotNull String passportID,
            @NotNull String personId,
            @NotNull String number,
            @NotNull @PastOrPresent LocalDate givenDate,
            @NotNull @Pattern(regexp = "\\p{Upper}\\w*") String givenDepartment,
            List<String> otherPassportIds,
            @NotNull  @Pattern(regexp = "\\p{Upper}\\w*") String country) {
        super(passportType, passportID, personId, number, givenDate, givenDepartment, otherPassportIds);
        this.country = country;
    }
}
