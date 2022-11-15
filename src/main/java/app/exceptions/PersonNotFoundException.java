package app.exceptions;

import lombok.Getter;

import java.util.UUID;

@Getter
public class PersonNotFoundException extends RuntimeException{
    private final String personId;

    public PersonNotFoundException(String personId) {
        super(String.format("person %s is not found", personId));
        this.personId = personId;

    }
}
