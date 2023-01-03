package app.repositories;

import app.exceptions.UpdatingObjectsIdsMismatchException;
import app.models.person.Person;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PersonRepository{

    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param entity must not be {@literal null}.
     * @return the saved entity; will never be {@literal null}.
     * @throws IllegalArgumentException in case the given {@literal entity} is {@literal null}.
     */
    Person add(Person entity);

    /**
     * Replaces old entity with a new one
     *
     * @param entityId
     * @param oldEntity
     * @param newEntity
     * @return stored Entity
     * @throws UpdatingObjectsIdsMismatchException if ids of entities are not equals to each other
     * @throws ObjectOptimisticLockingFailureException if somebody changed entity while we were preparing changes
     */
    Person replace(String entityId, Person oldEntity, Person newEntity);

    /**
     * Retrieves an entity by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id or {@literal Optional#empty()} if none found.
     * @throws IllegalArgumentException if {@literal id} is {@literal null}.
     */
    Optional<Person> findById(String id);

    /**
     * Returns whether an entity with the given id exists.
     *
     * @param id must not be {@literal null}.
     * @return {@literal true} if an entity with the given id exists, {@literal false} otherwise.
     * @throws IllegalArgumentException if {@literal id} is {@literal null}.
     */
    boolean existsById(String id);

    /**
     * Returns all instances of the type.
     *
     * @return all entities
     */
    Iterable<Person> findAll();

    /**
     * Returns the number of entities available.
     *
     * @return the number of entities.
     */
    long count();

    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@literal id} is {@literal null}
     */
    Optional<Person> deleteById(String id);

    /**
     * 
     * @param personName
     * @param surName
     * @param birthStartDate
     * @param birthEndDate
     * @return
     */
    List<Person> findPerson(String personName, String surName, LocalDate birthStartDate, LocalDate birthEndDate);

}
