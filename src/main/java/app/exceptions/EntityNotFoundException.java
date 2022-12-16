package app.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EntityNotFoundException extends RuntimeException{
    private String personId;

    public EntityNotFoundException(String personId) {
        super(String.format("Person with id '%s' is not found", personId));
        this.personId = personId;
    }
}
