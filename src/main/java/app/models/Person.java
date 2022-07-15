package app.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
public class Person {
    @NotNull
    private String id;
    private String name;
    private String surname;
    private String patronymic;
    @Digits(integer = 4, fraction = 0, message = "Invalid Passport series")
    private String placeOfBirth;
    @NonNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date dateOfBirth;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date dateOfDeath;
}
