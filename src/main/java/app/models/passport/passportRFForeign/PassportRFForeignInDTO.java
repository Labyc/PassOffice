package app.models.passport.passportRFForeign;

import app.models.passport.PassportType;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public record PassportRFForeignInDTO(
        @NotNull
        PassportType passportType,
        @NotNull
        UUID personId,
        @NotNull
        @Pattern(regexp = "\\d{7}]")
        String number,
        @PastOrPresent
        @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}")
        LocalDate givenDate,
        @NotNull
        @Pattern(regexp = "\\p{Upper}\\w*")
        String givenDepartment,
        @Future
        @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}")
        LocalDate expirationDate,
        List<UUID> otherPassportIds
) {
}
