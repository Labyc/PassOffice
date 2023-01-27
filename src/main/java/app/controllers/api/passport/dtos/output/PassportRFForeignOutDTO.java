package app.controllers.api.passport.dtos.output;

import app.models.passport.PassportType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.util.List;

public class PassportRFForeignOutDTO extends BasePassportOutDTO{
    private final LocalDate expirationDate;

    public PassportRFForeignOutDTO(
            @NotNull PassportType passportType,
            @NotNull String passportID,
            @NotNull String personId,
            @NotNull String number,
            @NotNull @PastOrPresent LocalDate givenDate,
            @NotNull @Pattern(regexp = "\\p{Upper}\\w*") String givenDepartment,
            List<String> otherPassportIds,
            @Future LocalDate expirationDate) {
        super(passportType, passportID, personId, number, givenDate, givenDepartment, otherPassportIds);
        this.expirationDate = expirationDate;
    }
}
