package app.models.passport;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.beans.TypeMismatchException;

import java.time.LocalDate;
import java.util.Objects;

@Value
@EqualsAndHashCode(callSuper = true)
public final class PassportRF extends BasePassport {
    private final String series;
    private final String givenDepartmentCode;

    public PassportRF(String passportID, String personId,
                      String series, String number, LocalDate givenDate, String givenDepartment, String givenDepartmentCode) {
        super(PassportType.PASSPORT_RF, passportID, personId, number, givenDate, givenDepartment, 0);
        this.series = series;
        this.givenDepartmentCode = givenDepartmentCode;
    }

    public PassportRF(String passportID, String personId,
                      String series, String number, LocalDate givenDate, String givenDepartment, String givenDepartmentCode, int version) {
        super(PassportType.PASSPORT_RF, passportID, personId, number, givenDate, givenDepartment, version);
        this.series = series;
        this.givenDepartmentCode = givenDepartmentCode;
    }

    @Override
    public BasePassport merge(BasePassport toPassport) {
        if (toPassport instanceof PassportRF otherPassport) {
            return new PassportRF(
                    otherPassport.getPassportID() != null ? otherPassport.getPassportID() : this.getPassportID(),
                    otherPassport.getPersonId() != null ? otherPassport.getPersonId() : this.getPersonId(),
                    otherPassport.getSeries() != null ? otherPassport.getSeries() : this.getSeries(),
                    otherPassport.getNumber() != null ? otherPassport.getNumber() : this.getNumber(),
                    otherPassport.getGivenDate() != null ? otherPassport.getGivenDate() : this.getGivenDate(),
                    otherPassport.getGivenDepartment() != null ? otherPassport.getGivenDepartment() : this.getGivenDepartment(),
                    otherPassport.getGivenDepartmentCode() != null ? otherPassport.getGivenDepartmentCode() : this.getGivenDepartmentCode(),
                    this.getVersion() + 1
            );
        }
        throw new TypeMismatchException(toPassport, PassportNonRF.class);
    }

    @Override
    public BasePassport withPassportID(@NotNull String passportID) {
        return Objects.equals(this.getPassportID(), passportID) ? this : new PassportRF(
                passportID, this.getPersonId(), this.getSeries(), this.getNumber(),
                this.getGivenDate(), this.getGivenDepartment(), this.getGivenDepartmentCode());
    }

    @Override
    public BasePassport withPersonId(@NotNull String personId) {
        return Objects.equals(this.getPersonId(), personId) ? this : new PassportRF(
                this.getPassportID(), personId, this.getSeries(), this.getNumber(),
                this.getGivenDate(), this.getGivenDepartment(), this.getGivenDepartmentCode());
    }

}
