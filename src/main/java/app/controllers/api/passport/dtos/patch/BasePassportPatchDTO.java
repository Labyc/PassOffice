package app.controllers.api.passport.dtos.patch;


import app.models.passport.BasePassport;
import app.models.passport.PassportType;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "passportType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PassportRFPatchDTO.class, name = "PASSPORT_RF"),
        @JsonSubTypes.Type(value = PassportNonRFPatchDTO.class, name = "PASSPORT_NON_RF"),
        @JsonSubTypes.Type(value = PassportRFForeignPatchDTO.class, name = "PASSPORT_RF_FOREIGN")
})
@Getter
@EqualsAndHashCode
public abstract class BasePassportPatchDTO {

    private final PassportType passportType;
    private final String personId;
    private final String number;
    private final LocalDate givenDate;
    private final String givenDepartment;

    public abstract BasePassport toPassport();
}
