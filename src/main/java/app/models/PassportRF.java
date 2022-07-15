package app.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Digits;
import java.util.List;

@Data
@AllArgsConstructor
public class PassportRF extends BasePassport{

    @Digits(integer = 4, fraction = 0, message = "Invalid Passport series")
    private String series;
    @Digits(integer = 6, fraction = 0, message = "Invalid Passport number")
    private String number;
    private String givenDepartmentCode;
    private List<PersonRegistration> registrationList;
    public static BasePassport createRandomPassport() {
        return null;
    }
}
