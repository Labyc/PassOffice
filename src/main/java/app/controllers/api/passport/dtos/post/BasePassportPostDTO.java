package app.controllers.api.passport.dtos.post;


import app.models.passport.BasePassport;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "passportType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PassportRFPostDTO.class, name = "PASSPORT_RF"),
        @JsonSubTypes.Type(value = PassportNonRFPostDTO.class, name = "PASSPORT_NON_RF"),
        @JsonSubTypes.Type(value = PassportRFForeignPostDTO.class, name = "PASSPORT_RF_FOREIGN")
})
@Getter
@EqualsAndHashCode
public abstract class BasePassportPostDTO {
    @NotNull
    private final String personId;
    @NotNull
    private final String number;
    @NotNull
    private final LocalDate givenDate;
    @NotNull
    private final String givenDepartment;

    public abstract BasePassport toPassport();

    public abstract BasePassportPostDTO withPersonId(@NotNull String personId);
}
