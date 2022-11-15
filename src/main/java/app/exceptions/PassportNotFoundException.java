package app.exceptions;

import lombok.Getter;

@Getter
public class PassportNotFoundException extends RuntimeException{
    private final String passportId;

    public PassportNotFoundException(String passportId) {
        super(String.format("person %s is not found", passportId));
        this.passportId = passportId;

    }
}
