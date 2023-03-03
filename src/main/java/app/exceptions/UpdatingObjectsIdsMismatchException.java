package app.exceptions;

import lombok.Getter;

@Getter
public class UpdatingObjectsIdsMismatchException extends RuntimeException {
    private final String requiredId;
    private final String oldEntityId;
    private final String newEntityId;

    public UpdatingObjectsIdsMismatchException(String requiredId, String oldEntityId, String newEntityId) {
        super(String.format("Entered '%s', old entity '%s' and new entity '%s' ids must equals each other", requiredId, oldEntityId, newEntityId));
        this.requiredId = requiredId;
        this.oldEntityId = oldEntityId;
        this.newEntityId = newEntityId;
    }
}
