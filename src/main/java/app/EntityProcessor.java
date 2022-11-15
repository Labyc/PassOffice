package app;


import app.controllers.api.person.PersonOutDTO;
import app.controllers.api.person.PersonPatchDTO;

import java.util.UUID;

public interface EntityProcessor<PatchRequestDTO, RequestDTO, ResponseDTO> {

    /**
     * Creates new entity using its requestDTO
     *
     * @param entityRequestDTO
     */
    public ResponseDTO createEntity(RequestDTO entityRequestDTO);

    /**
     * Patches entity
     *
     * @throws EntityNotFoundException if no entity with provided entityId found
     */
    public ResponseDTO updateEntity(UUID entityId, PatchRequestDTO entityRequestDTO);

    ResponseDTO updateEntity(String personId, PersonPatchDTO personRequestDTO);

    /**
     * Puts new entity instead of existing one
     *
     * @throws EntityNotFoundException if no entity with provided entityId found
     */
    public ResponseDTO replaceEntity(UUID entityId, RequestDTO entityRequestDTO);

    /**
     * Find entity
     *
     * @throws EntityNotFoundException if no entity with provided entityId found
     */
    public ResponseDTO findById(UUID entityId);

    public ResponseDTO findById(String entityId);

    ResponseDTO deleteById(String personId);
}
