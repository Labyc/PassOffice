package app.controllers.api.passport.dtos.patch;

import app.models.passport.BasePassport;
import app.models.passport.PassportNonRF;
import app.models.passport.PassportType;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
public class PassportNonRFPatchDTO extends BasePassportPatchDTO {
    private final String country;

    public PassportNonRFPatchDTO(
            String personId,
            @NumberFormat String number,
            @PastOrPresent LocalDate givenDate,
            @Pattern(regexp = "\\p{Upper}\\w*") String givenDepartment,
            @Pattern(regexp = "\\p{Upper}\\w*") String country) {
        super(PassportType.PASSPORT_NON_RF, personId, number, givenDate, givenDepartment);
        this.country = country;
    }


    @Override
    public BasePassport toPassport() {
        return new PassportNonRF(null, this.getPersonId(), this.getNumber(), this.getGivenDate(), this.getGivenDepartment(), this.getCountry());
    }
}
