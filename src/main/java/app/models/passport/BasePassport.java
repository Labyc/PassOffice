package app.models.passport;


import app.models.RegistrationData;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
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
