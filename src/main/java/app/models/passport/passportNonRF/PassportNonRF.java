package app.models.passport.passportNonRF;

import app.models.passport.BasePassport;
import jakarta.validation.constraints.NotNull;

public class PassportNonRF extends BasePassport {
    private String country;
    @NotNull
    private String number;
}
