package app.models.passport.passportRFForeign;

import app.models.passport.BasePassport;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class PassportRFForeign extends BasePassport {
    @NotNull
    private Date expirationDate;
    @Digits(integer = 8, fraction = 0, message = "Invalid Passport number")
    private String number;


}
