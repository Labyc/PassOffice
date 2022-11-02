package app.models.passport.passportRF;

import app.models.RegistrationData;
import app.models.passport.BasePassport;
import app.models.passport.PassportType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
        List<RegistrationData> registrationDataList;

}
