package app.controllers.api.passport.dtos.input;


import app.models.passport.PassportType;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "passportType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PassportRFPostDTO.class, name = "PASSPORT_RF"),
        @JsonSubTypes.Type(value = PassportNonRFPostDTO.class, name = "PASSPORT_NON_RF"),
        @JsonSubTypes.Type(value = PassportRFForeignPostDTO.class, name = "PASSPORT_RF_FOREIGN")
})
@AllArgsConstructor
public abstract class BasePassportPostDTO {

    @NotNull
    PassportType passportType;
    @NotNull
    String personId;
    @NotNull
    @NumberFormat
    String number;
    @NotNull
    @PastOrPresent
    //@Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}")
    LocalDate givenDate;
    @NotNull
    @Pattern(regexp = "\\p{Upper}\\w*")
    String givenDepartment;
    List<String> otherPassportIds;
}
