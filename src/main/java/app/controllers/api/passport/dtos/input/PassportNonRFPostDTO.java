package app.controllers.api.passport.dtos.input;

import app.controllers.api.passport.dtos.input.BasePassportPostDTO;
import app.models.passport.PassportType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.util.List;

public class PassportNonRFPostDTO extends BasePassportPostDTO {
    private String country;

    public PassportNonRFPostDTO(@NotNull PassportType passportType, @NotNull String personId, @NotNull String number, @PastOrPresent LocalDate givenDate, @NotNull @Pattern(regexp = "\\p{Upper}\\w*") String givenDepartment, List<String> otherPassportIds) {
        super(passportType, personId, number, givenDate, givenDepartment, otherPassportIds);
    }
}
