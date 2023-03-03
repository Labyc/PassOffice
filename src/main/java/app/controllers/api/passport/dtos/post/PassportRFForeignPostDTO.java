package app.controllers.api.passport.dtos.post;

import app.models.passport.BasePassport;
import app.models.passport.PassportRFForeign;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDate;
import java.util.Objects;

@Value
@EqualsAndHashCode(callSuper = true)
public class PassportRFForeignPostDTO extends BasePassportPostDTO {
    private final LocalDate expirationDate;

    public PassportRFForeignPostDTO(
            @NotNull String personId,
            @NotNull @NumberFormat(pattern = "#######") String number,
            @PastOrPresent LocalDate givenDate,
            @NotNull @Pattern(regexp = "\\p{Upper}\\w*") String givenDepartment,
            @NotNull @Future LocalDate expirationDate) {
        super(personId, number, givenDate, givenDepartment);
        this.expirationDate = expirationDate;
    }

    @Override
    public BasePassport toPassport() {
        return new PassportRFForeign(null, this.getPersonId(), this.getNumber(), this.getGivenDate(), this.getGivenDepartment(), this.getExpirationDate());
    }

    @Override
    public BasePassportPostDTO withPersonId(@NotNull String personId) {
        return Objects.equals(this.getPersonId(), personId) ? this : new PassportRFForeignPostDTO(
                personId, this.getNumber(),
                this.getGivenDate(), this.getGivenDepartment(), this.getExpirationDate());
    }
}
