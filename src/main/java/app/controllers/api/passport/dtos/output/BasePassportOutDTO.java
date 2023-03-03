package app.controllers.api.passport.dtos.output;

import app.models.passport.*;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "passportType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PassportRFOutDTO.class, name = "PASSPORT_RF"),
        @JsonSubTypes.Type(value = PassportNonRFOutDTO.class, name = "PASSPORT_NON_RF"),
        @JsonSubTypes.Type(value = PassportRFForeignOutDTO.class, name = "PASSPORT_RF_FOREIGN")
})
@Getter
@AllArgsConstructor
public abstract class BasePassportOutDTO {
    private final PassportType passportType;
    private final String passportID;
    private final String personId;
    private final String number;
    private final LocalDate givenDate;
    private final String givenDepartment;

    public static BasePassportOutDTO fromPassport(BasePassport passport) {
        switch (passport.getPassportType()) {
            case PASSPORT_RF -> {
                PassportRF passportRF = (PassportRF) passport;
                return new PassportRFOutDTO(passportRF.getPassportType(), passportRF.getPassportID(),
                        passportRF.getPersonId(), passportRF.getSeries(), passportRF.getNumber(),
                        passportRF.getGivenDate(), passportRF.getGivenDepartment(), passportRF.getGivenDepartmentCode());
            }
            case PASSPORT_RF_FOREIGN -> {
                PassportRFForeign passportRFForeign = (PassportRFForeign) passport;
                return new PassportRFForeignOutDTO(passportRFForeign.getPassportType(), passportRFForeign.getPassportID(),
                        passportRFForeign.getPersonId(), passportRFForeign.getNumber(),
                        passportRFForeign.getGivenDate(), passportRFForeign.getGivenDepartment(),
                        passportRFForeign.getExpirationDate());
            }
            case PASSPORT_NON_RF -> {
                PassportNonRF passportNonRF = (PassportNonRF) passport;
                return new PassportNonRFOutDTO(passportNonRF.getPassportType(), passportNonRF.getPassportID(),
                        passportNonRF.getPersonId(), passportNonRF.getNumber(),
                        passportNonRF.getGivenDate(), passportNonRF.getGivenDepartment(),
                        passportNonRF.getCountry());
            }
        }
        return null;
    }

}
