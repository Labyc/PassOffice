package app.models.passport.passportNonRF;

import app.models.passport.BasePassport;

import javax.validation.constraints.NotNull;


public class PassportNonRF extends BasePassport {
    private String country;
    @NotNull
    private String number;
}
