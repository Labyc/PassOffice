package app.controllers.api.passport.dtos.patch;

import app.models.passport.BasePassport;
import app.models.passport.PassportRFForeign;
import app.models.passport.PassportType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
public class PassportRFForeignPatchDTO extends BasePassportPatchDTO {
    private final LocalDate expirationDate;

    public PassportRFForeignPatchDTO(
            String personId,
            @NumberFormat(pattern = "#######") String number,
            @PastOrPresent LocalDate givenDate,
            @Pattern(regexp = "\\p{Upper}\\w*") String givenDepartment,
            @Future LocalDate expirationDate) {
        super(PassportType.PASSPORT_RF_FOREIGN, personId, number, givenDate, givenDepartment);
        this.expirationDate = expirationDate;
    }

    @Override
    public BasePassport toPassport() {
        return new PassportRFForeign(null, this.getPersonId(), this.getNumber(), this.getGivenDate(), this.getGivenDepartment(), this.getExpirationDate());
    }
}
