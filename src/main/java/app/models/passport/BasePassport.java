package app.models.passport;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class BasePassport {

    @NotNull
    PassportType passportType;
    @NotNull
    UUID personId;
    @NotNull
    @Pattern(regexp = "\\d{6}")
    String number;
    @PastOrPresent
    //@Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}")
    LocalDate givenDate;
    @NotNull
    @Pattern(regexp = "\\p{Upper}\\w*")
    String givenDepartment;
    List<UUID> otherPassportIds;

    private UUID passportId;
    private boolean isActive;

}
