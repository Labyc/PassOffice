package app.models.passport.passportRFForeign;

import app.models.passport.PassportType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
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
