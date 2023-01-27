package app.controllers.api.passport.dtos.input;

import app.models.passport.PassportType;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;


public class PassportRFPostDTO extends BasePassportPostDTO {

    private final String series;
    private final String givenDepartmentCode;

    public PassportRFPostDTO(
            @NotNull PassportType passportType,
            @NotNull String personId,
            @Digits(integer = 4, fraction = 0, message = "Invalid Passport series") String series,
            @Digits(integer = 6, fraction = 0, message = "Invalid Passport number") String number,
            @PastOrPresent LocalDate givenDate,
            @NotNull @Pattern(regexp = "\\p{Upper}\\w*") String givenDepartment,
            String givenDepartmentCode,
            List<String> otherPassportIds) {
        super(passportType, personId, number, givenDate, givenDepartment, otherPassportIds);
        this.series = series;
        this.givenDepartmentCode = givenDepartmentCode;
    }
}
