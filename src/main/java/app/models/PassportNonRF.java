package app.models;

import javax.validation.constraints.NotNull;

public class PassportNonRF extends BasePassport{
    private String country;
    @NotNull
    private String number;
}
