package app.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EntityNotFoundException extends RuntimeException {
    private String id;

    public EntityNotFoundException(String id) {
        super(String.format("Entity with id '%s' is not found", id));
        this.id = id;
    }
}
