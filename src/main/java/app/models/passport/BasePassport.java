package app.models.passport;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public abstract class BasePassport {
    private final PassportType passportType;
    private final String passportID;
    private final String personId;
    private final String number;
    private final LocalDate givenDate;
    private final String givenDepartment;
    private final int version;

    public abstract BasePassport merge(BasePassport toPassport);

    public abstract BasePassport withPassportID(String passportId);

    public abstract BasePassport withPersonId(String personId);
}
