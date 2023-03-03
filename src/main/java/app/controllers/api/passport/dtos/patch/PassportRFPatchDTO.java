package app.controllers.api.passport.dtos.patch;

import app.models.passport.BasePassport;
import app.models.passport.PassportRF;
import app.models.passport.PassportType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.executable.ValidateOnExecution;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
public class PassportRFPatchDTO extends BasePassportPatchDTO {

    private final String series;
    private final String givenDepartmentCode;

    @Valid
    @ValidateOnExecution
    public PassportRFPatchDTO(
            String personId,
            @NumberFormat(pattern = "####") String series,
            @NumberFormat(pattern = "######") String number,
            @PastOrPresent LocalDate givenDate,
            @Pattern(regexp = "\\p{Upper}\\w*") String givenDepartment,
            @NumberFormat(pattern = "###-###") String givenDepartmentCode) {
        super(PassportType.PASSPORT_RF, personId, number, givenDate, givenDepartment);
        this.series = series;
        this.givenDepartmentCode = givenDepartmentCode;
    }

    @Override
    public BasePassport toPassport() {
        return new PassportRF(null, this.getPersonId(), this.getSeries(), this.getNumber(), this.getGivenDate(), this.getGivenDepartment(), this.getGivenDepartmentCode());
    }
}
