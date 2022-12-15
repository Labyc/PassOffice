package app;


import app.exceptions.EntityNotFoundException;

public interface EntityProcessor<PatchRequestDTO, RequestDTO, ResponseDTO> {

    /**
     * Creates new entity using its requestDTO
     *
     * @param entityRequestDTO
     */
    ResponseDTO createEntity(RequestDTO entityRequestDTO);

    /**
     * Patches entity
     *
     * @throws EntityNotFoundException if no entity with provided entityId found
     */
    ResponseDTO updateEntity(String personId, PatchRequestDTO personRequestDTO);

    /**
     * Puts new entity instead of existing one
     *
     * @throws EntityNotFoundException if no entity with provided entityId found
     */
    ResponseDTO replaceEntity(String entityId, RequestDTO entityRequestDTO);

    /**
     * Find entity
     *
     * @throws EntityNotFoundException if no entity with provided entityId found
     */
    ResponseDTO findById(String entityId);

    ResponseDTO deleteById(String personId);
}
