package app.controllers.api.passport.dtos.output;

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
        @JsonSubTypes.Type(value = PassportRFOutDTO.class, name = "PASSPORT_RF"),
        @JsonSubTypes.Type(value = PassportNonRFOutDTO.class, name = "PASSPORT_NON_RF"),
        @JsonSubTypes.Type(value = PassportRFForeignOutDTO.class, name = "PASSPORT_RF_FOREIGN")
})
@AllArgsConstructor
public class BasePassportOutDTO {
    @NotNull
    private final PassportType passportType;
    @NotNull
    private final String passportID;
    @NotNull
    private final String personId;
    @NotNull
    @NumberFormat
    private final String number;
    @NotNull
    @PastOrPresent
    private final LocalDate givenDate;
    @NotNull
    @Pattern(regexp = "\\p{Upper}\\w*")
    private final String givenDepartment;
    private final List<String> otherPassportIds;
}
