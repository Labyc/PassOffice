package app;

import lombok.Getter;

@Getter
public class PersonNotFoundException extends RuntimeException{
    private final int personId;

    public PersonNotFoundException(int personId) {
        super(String.format("person %s is not found", personId));
        this.personId = personId;

    }
}
