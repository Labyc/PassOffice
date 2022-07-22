package app.models;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
public class PersonInDTO {
    @NotBlank
    @Pattern(regexp = "\\p{Upper}\\w*")
    private String name;
    @NotBlank
    @Pattern(regexp = "\\p{Upper}\\w*")
    private String surname;
    @Pattern(regexp = "\\p{Upper}\\w*")
    private String patronymic;
    @NotBlank
    @Pattern(regexp = "\\p{Upper}\\w*")
    private String placeOfBirth;
    @NotNull
    @PastOrPresent
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date dateOfBirth;
    @PastOrPresent
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date dateOfDeath;
}
