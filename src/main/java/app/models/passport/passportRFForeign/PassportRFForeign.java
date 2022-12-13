package app.models.passport.passportRFForeign;

import app.models.passport.BasePassport;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class PassportRFForeign extends BasePassport {
    @NotNull
    private LocalDate expirationDate;
    @Digits(integer = 8, fraction = 0, message = "Invalid Passport number")
    private String number;


}
