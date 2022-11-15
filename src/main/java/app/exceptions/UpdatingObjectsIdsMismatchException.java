package app.exceptions;

import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdatingObjectsIdsMismatchException extends RuntimeException {
    private UUID requiredId;
    private UUID oldEntityId;
    private UUID newEntityId;

    public UpdatingObjectsIdsMismatchException(UUID requiredId, UUID oldEntityId, UUID newEntityId) {
        super(String.format("Entered '%s', old entity '%s' and new entity '%s' ids must equals each other", requiredId, oldEntityId, newEntityId));
        this.requiredId =requiredId;
        this.oldEntityId = oldEntityId;
        this.newEntityId = newEntityId;
    }
}
