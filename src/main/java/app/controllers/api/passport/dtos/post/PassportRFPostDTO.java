package app.controllers.api.passport.dtos.post;

import app.models.passport.BasePassport;
import app.models.passport.PassportRF;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
public final class PassportRFPostDTO extends BasePassportPostDTO {

    private final String series;
    @NotNull
    private final String givenDepartmentCode;


    public PassportRFPostDTO(
            @NotNull String personId,
            @NotNull String series,
            @NotNull @NumberFormat(pattern = "######") String number,
            @PastOrPresent LocalDate givenDate,
            @NotNull @Pattern(regexp = "\\p{Upper}\\w*") String givenDepartment,
            @NumberFormat(pattern = "###-###") String givenDepartmentCode) {
        super(personId, number, givenDate, givenDepartment);
        this.series = series;
        this.givenDepartmentCode = givenDepartmentCode;
    }

    @Override
    public BasePassport toPassport() {
        return new PassportRF(null, this.getPersonId(), this.getSeries(), this.getNumber(), this.getGivenDate(), this.getGivenDepartment(), this.getGivenDepartmentCode());
    }

    @Override
    public BasePassportPostDTO withPersonId(@NotNull String personId) {
        return personId.equals(this.getPersonId()) ? this : new PassportRFPostDTO(
                personId, this.getSeries(), this.getNumber(),
                this.getGivenDate(), this.getGivenDepartment(), this.getGivenDepartmentCode());
    }
}
