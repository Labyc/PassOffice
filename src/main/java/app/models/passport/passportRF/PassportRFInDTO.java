package app.models.passport.passportRF;

import app.models.passport.BasePassport;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class PassportRFInDTO extends BasePassport {

        @NotNull
        @Pattern(regexp = "\\d{4}]")
        String series;
        @NotNull
        @Pattern(regexp = "\\d{6}]")
        String number;
        @NotNull
        @Pattern(regexp = "\\d{3}-\\d{3}")
        String givenDepartmentCode;

}
