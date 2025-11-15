package app.models.passport;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.beans.TypeMismatchException;

import java.time.LocalDate;
import java.util.Objects;

@Value
@EqualsAndHashCode(callSuper = true)
public class PassportNonRF extends BasePassport {
    String country;

    public PassportNonRF(String passportID, String personId, String number, LocalDate givenDate, String givenDepartment, String country) {
        super(PassportType.PASSPORT_NON_RF, passportID, personId, number, givenDate, givenDepartment, 0);
        this.country = country;
    }

    public PassportNonRF(String passportID, String personId, String number, LocalDate givenDate, String givenDepartment, String country, int version) {
        super(PassportType.PASSPORT_NON_RF, passportID, personId, number, givenDate, givenDepartment, version);
        this.country = country;
    }

    @Override
    public BasePassport merge(BasePassport toPassport) {
        if (toPassport instanceof PassportNonRF otherPassport) {
            return new PassportNonRF(
                    otherPassport.getPassportID() != null ? otherPassport.getPassportID() : this.getPassportID(),
                    otherPassport.getPersonId() != null ? otherPassport.getPersonId() : this.getPersonId(),
                    otherPassport.getNumber() != null ? otherPassport.getNumber() : this.getNumber(),
                    otherPassport.getGivenDate() != null ? otherPassport.getGivenDate() : this.getGivenDate(),
                    otherPassport.getGivenDepartment() != null ? otherPassport.getGivenDepartment() : this.getGivenDepartment(),
                    otherPassport.getCountry() != null ? otherPassport.getCountry() : this.getCountry(),
                    this.getVersion() + 1
            );
        }
        throw new TypeMismatchException(toPassport, PassportNonRF.class);
    }

    @Override
    public BasePassport withPassportID(@NotNull String passportID) {
        return Objects.equals(this.getPassportID(), passportID) ? this : new PassportNonRF(
                passportID, this.getPersonId(), this.getNumber(),
                this.getGivenDate(), this.getGivenDepartment(), this.getCountry());
    }

    @Override
    public BasePassport withPersonId(String personId) {
        return Objects.equals(this.getPersonId(), personId) ? this : new PassportNonRF(
                this.getPassportID(), personId, this.getNumber(),
                this.getGivenDate(), this.getGivenDepartment(), this.getCountry());
    }
}
