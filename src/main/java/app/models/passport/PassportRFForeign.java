package app.models.passport;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.beans.TypeMismatchException;

import java.time.LocalDate;
import java.util.Objects;

@Value
@EqualsAndHashCode(callSuper = true)
public class PassportRFForeign extends BasePassport {
    LocalDate expirationDate;

    public PassportRFForeign(String passportID, String personId, String number, LocalDate givenDate, String givenDepartment, LocalDate expirationDate) {
        super(PassportType.PASSPORT_RF_FOREIGN, passportID, personId, number, givenDate, givenDepartment, 0);
        this.expirationDate = expirationDate;
    }

    public PassportRFForeign(String passportID, String personId, String number, LocalDate givenDate, String givenDepartment, LocalDate expirationDate, int version) {
        super(PassportType.PASSPORT_RF_FOREIGN, passportID, personId, number, givenDate, givenDepartment, version);
        this.expirationDate = expirationDate;
    }

    @Override
    public BasePassport merge(BasePassport toPassport) {
        if (toPassport instanceof PassportRFForeign otherPassport) {
            return new PassportRFForeign(
                    otherPassport.getPassportID() != null ? otherPassport.getPassportID() : this.getPassportID(),
                    otherPassport.getPersonId() != null ? otherPassport.getPersonId() : this.getPersonId(),
                    otherPassport.getNumber() != null ? otherPassport.getNumber() : this.getNumber(),
                    otherPassport.getGivenDate() != null ? otherPassport.getGivenDate() : this.getGivenDate(),
                    otherPassport.getGivenDepartment() != null ? otherPassport.getGivenDepartment() : this.getGivenDepartment(),
                    otherPassport.getExpirationDate() != null ? otherPassport.getExpirationDate() : this.getExpirationDate()
            );
        }
        throw new TypeMismatchException(toPassport, PassportNonRF.class);
    }

    @Override
    public BasePassport withPassportID(String passportID) {
        return Objects.equals(this.getPassportID(), passportID) ? this : new PassportRFForeign(
                passportID, this.getPersonId(), this.getNumber(),
                this.getGivenDate(), this.getGivenDepartment(), this.getExpirationDate());
    }

    @Override
    public BasePassport withPersonId(String personId) {
        return Objects.equals(this.getPersonId(), personId) ? this : new PassportRFForeign(
                this.getPassportID(), personId, this.getNumber(),
                this.getGivenDate(), this.getGivenDepartment(), this.getExpirationDate());
    }
}
