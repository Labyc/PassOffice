package app.controllers.api.passport.dtos.post;

import app.models.passport.BasePassport;
import app.models.passport.PassportNonRF;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
public class PassportNonRFPostDTO extends BasePassportPostDTO {
    private final String country;

    public PassportNonRFPostDTO(
            @NotNull String personId,
            @NotNull @NumberFormat String number,
            @PastOrPresent LocalDate givenDate,
            @NotNull @Pattern(regexp = "\\p{Upper}\\w*") String givenDepartment,
            @NotNull @Pattern(regexp = "\\p{Upper}\\w*") String country) {
        super(personId, number, givenDate, givenDepartment);
        this.country = country;
    }


    @Override
    public BasePassport toPassport() {
        return new PassportNonRF(null, this.getPersonId(), this.getNumber(), this.getGivenDate(), this.getGivenDepartment(), this.getCountry());
    }

    @Override
    public BasePassportPostDTO withPersonId(@NotNull String personId) {
        return personId.equals(this.getPersonId()) ? this : new PassportNonRFPostDTO(
                personId, this.getNumber(),
                this.getGivenDate(), this.getGivenDepartment(), this.getCountry());
    }
}
