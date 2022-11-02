package passportOfficeTests.personApiTests;

import app.controllers.api.person.PersonInDTO;
import app.controllers.api.person.PersonOutDTO;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import passportOfficeTests.PassportOfficeBaseTest;

import java.time.LocalDate;
import java.util.stream.Stream;

public class PersonTestsPositive extends PassportOfficeBaseTest {


    private static Stream<PersonInDTO> postPersonTestDP() {
        return Stream.of(
                new PersonInDTO("Name", "Surname", "Patronymic", "PlaceOfBirth", LocalDate.of(2022, 1, 1), LocalDate.of(2022, 1, 2)),
                new PersonInDTO("Name1", "Surname1", null, "PlaceOfBirth1", LocalDate.of(1980, 1, 1), null)
        );
    }

    @ParameterizedTest
    @MethodSource("postPersonTestDP")
    public void postPersonTest(PersonInDTO personRequest) {

        PersonOutDTO personResponse = RestAssured.with().body(personRequest).post("/person")
                .then().statusCode(201).extract().body().as(PersonOutDTO.class);

        Assertions.assertNotEquals(null, personResponse.id());
        Assertions.assertEquals(personRequest.name(), personResponse.name());
        Assertions.assertEquals(personRequest.surname(), personResponse.surname());
        Assertions.assertEquals(personRequest.patronymic(), personResponse.patronymic());
        Assertions.assertEquals(personRequest.placeOfBirth(), personResponse.placeOfBirth());
        Assertions.assertEquals(personRequest.dateOfBirth(), personResponse.dateOfBirth());
        Assertions.assertEquals(personRequest.dateOfDeath(), personResponse.dateOfDeath());
    }

    private static Stream<Arguments> putPersonTestDP() {
        return Stream.of(
                Arguments.of(new PersonInDTO("Name", "Surname", "Patronymic", "PlaceOfBirth", LocalDate.of(2022, 1, 1), null),
                        new PersonInDTO("Name", "Surname", "Patronymic", "PlaceOfBirth", LocalDate.of(2022, 1, 1), null),
                        "No Changes"),
                Arguments.of(new PersonInDTO("Name", "Surname", "Patronymic", "PlaceOfBirth", LocalDate.of(2022, 1, 1), null),
                        new PersonInDTO("NameEdited", "Surname", "Patronymic", "PlaceOfBirth", LocalDate.of(2022, 1, 1), null),
                        "Edited Name"),
                Arguments.of(new PersonInDTO("Name", "Surname", "Patronymic", "PlaceOfBirth", LocalDate.of(2022, 1, 1), null),
                        new PersonInDTO("Name", "SurnameEdited", "Patronymic", "PlaceOfBirth", LocalDate.of(2022, 1, 1), null),
                        "Edited Surname"),
                Arguments.of(new PersonInDTO("Name", "Surname", null, "PlaceOfBirth", LocalDate.of(2022, 1, 1), null),
                        new PersonInDTO("Name", "Surname", "Patronymic", "PlaceOfBirth", LocalDate.of(2022, 1, 1), null),
                        "Patronymic from null to smth"),
                Arguments.of(new PersonInDTO("Name", "Surname", "Patronymic", "PlaceOfBirth", LocalDate.of(2022, 1, 1), null),
                        new PersonInDTO("Name", "Surname", null, "PlaceOfBirth", LocalDate.of(2022, 1, 1), null),
                        "Patronymic from smth to null"),
                Arguments.of(new PersonInDTO("Name", "Surname", "Patronymic", "PlaceOfBirth", LocalDate.of(2022, 1, 1), null),
                        new PersonInDTO("Name", "Surname", "PatronymicEdited", "PlaceOfBirth", LocalDate.of(2022, 1, 1), null),
                        "Edited Patronymic"),
                Arguments.of(new PersonInDTO("Name", "Surname", "Patronymic", "PlaceOfBirth", LocalDate.of(2022, 1, 1), null),
                        new PersonInDTO("Name", "Surname", "Patronymic", "PlaceOfBirthEdited", LocalDate.of(2022, 1, 1), null),
                        "Edited Place of birth"),
                Arguments.of(new PersonInDTO("Name", "Surname", "Patronymic", "PlaceOfBirth", LocalDate.of(2022, 1, 1), null),
                        new PersonInDTO("Name", "Surname", "Patronymic", "PlaceOfBirth", LocalDate.of(1995, 3, 2), null),
                        "Edited Date of birth"),
                Arguments.of(new PersonInDTO("Name", "Surname", "Patronymic", "PlaceOfBirth", LocalDate.of(2022, 1, 1), null),
                        new PersonInDTO("Name", "Surname", "Patronymic", "PlaceOfBirth", LocalDate.of(2022, 1, 1), LocalDate.of(2022, 1, 1)),
                        "Date of death from null to smth"),
                Arguments.of(new PersonInDTO("Name", "Surname", "Patronymic", "PlaceOfBirth", LocalDate.of(2022, 1, 1), LocalDate.of(2022, 1, 1)),
                        new PersonInDTO("Name", "Surname", "Patronymic", "PlaceOfBirth", LocalDate.of(2022, 1, 1), null),
                        "Date of death from smth to null"),
                Arguments.of(new PersonInDTO("Name", "Surname", "Patronymic", "PlaceOfBirth", LocalDate.of(2022, 1, 1), LocalDate.of(2022, 1, 1)),
                        new PersonInDTO("Name", "Surname", "Patronymic", "PlaceOfBirth", LocalDate.of(2022, 1, 1), LocalDate.of(2022, 1, 2)),
                        "Edited date of death")
                );
    }

    @ParameterizedTest
    @MethodSource("putPersonTestDP")
    public void putPersonTest(PersonInDTO personPostRequest, PersonInDTO personPutRequest, String verification) {

        PersonOutDTO personResponse = RestAssured.with().body(personPostRequest).post("/person")
                .then().statusCode(201).extract().body().as(PersonOutDTO.class);
        personResponse = RestAssured.with().body(personPutRequest).pathParam("personId", personResponse.id()).put("/person/{personId}")
                .then().statusCode(200).extract().body().as(PersonOutDTO.class);

        Assertions.assertNotEquals(null, personResponse.id());
        Assertions.assertEquals(personPutRequest.name(), personResponse.name());
        Assertions.assertEquals(personPutRequest.surname(), personResponse.surname());
        Assertions.assertEquals(personPutRequest.patronymic(), personResponse.patronymic());
        Assertions.assertEquals(personPutRequest.placeOfBirth(), personResponse.placeOfBirth());
        Assertions.assertEquals(personPutRequest.dateOfBirth(), personResponse.dateOfBirth());
        Assertions.assertEquals(personPutRequest.dateOfDeath(), personResponse.dateOfDeath());
    }

    private static Stream<Arguments> patchPersonTestDP() {
        return Stream.of(
                Arguments.of(new PersonInDTO("Name", "Surname", "Patronymic", "PlaceOfBirth", LocalDate.of(2022, 1, 1), null),
                        new PersonInDTO(null, null, null, null, null, null),
                        "No Changes with nulls"),
                Arguments.of(new PersonInDTO("Name", "Surname", "Patronymic", "PlaceOfBirth", LocalDate.of(2022, 1, 1), null),
                        new PersonInDTO("NameEdited", null, null, null, null, null),
                        "Edited Name"),
                Arguments.of(new PersonInDTO("Name", "Surname", "Patronymic", "PlaceOfBirth", LocalDate.of(2022, 1, 1), null),
                        new PersonInDTO(null, "SurnameEdited", null, null, null, null),
                        "Edited Surname"),
                Arguments.of(new PersonInDTO("Name", "Surname", null, "PlaceOfBirth", LocalDate.of(2022, 1, 1), null),
                        new PersonInDTO(null, null, "Patronymic", null, null, null),
                        "Patronymic from null to smth"),
                Arguments.of(new PersonInDTO("Name", "Surname", "Patronymic", "PlaceOfBirth", LocalDate.of(2022, 1, 1), null),
                        new PersonInDTO("Name", "Surname", null, "PlaceOfBirth", LocalDate.of(2022, 1, 1), null),
                        "Patronymic from smth to null"), //TODO
                /*Arguments.of(new PersonInDTO("Name", "Surname", "Patronymic", "PlaceOfBirth", LocalDate.of(2022, 1, 1), null),
                        new PersonInDTO("Name", "Surname", "PatronymicEdited", "PlaceOfBirth", LocalDate.of(2022, 1, 1), null),
                        "Edited Patronymic"),*/
                Arguments.of(new PersonInDTO("Name", "Surname", "Patronymic", "PlaceOfBirth", LocalDate.of(2022, 1, 1), null),
                        new PersonInDTO(null, null, null, "PlaceOfBirthEdited", null, null),
                        "Edited Place of birth"),
                Arguments.of(new PersonInDTO("Name", "Surname", "Patronymic", "PlaceOfBirth", LocalDate.of(2022, 1, 1), null),
                        new PersonInDTO(null, null, null, null, LocalDate.of(1995, 3, 2), null),
                        "Edited Date of birth"),
                Arguments.of(new PersonInDTO("Name", "Surname", "Patronymic", "PlaceOfBirth", LocalDate.of(2022, 1, 1), null),
                        new PersonInDTO(null, null, null, null, null, LocalDate.of(2022, 1, 1)),
                        "Date of death from null to smth"),
                Arguments.of(new PersonInDTO("Name", "Surname", "Patronymic", "PlaceOfBirth", LocalDate.of(2022, 1, 1), LocalDate.of(2022, 1, 1)),
                        new PersonInDTO(null, null, null, null,null, null),
                        "Date of death from smth to null") //TODO
        );
    }

    @ParameterizedTest
    @MethodSource({"patchPersonTestDP", "putPersonTestDP"})
    public void patchPersonTest(PersonInDTO personPostRequest, PersonInDTO personPutRequest, String verification) {

        PersonOutDTO personResponse = RestAssured.with().body(personPostRequest).post("/person")
                .then().statusCode(201).extract().body().as(PersonOutDTO.class);
        personResponse = RestAssured.with().body(personPutRequest).pathParam("personId", personResponse.id()).put("/person/{personId}")
                .then().statusCode(200).extract().body().as(PersonOutDTO.class);

        Assertions.assertNotEquals(null, personResponse.id());
        Assertions.assertEquals(personPutRequest.name(), personResponse.name());
        Assertions.assertEquals(personPutRequest.surname(), personResponse.surname());
        Assertions.assertEquals(personPutRequest.patronymic(), personResponse.patronymic());
        Assertions.assertEquals(personPutRequest.placeOfBirth(), personResponse.placeOfBirth());
        Assertions.assertEquals(personPutRequest.dateOfBirth(), personResponse.dateOfBirth());
        Assertions.assertEquals(personPutRequest.dateOfDeath(), personResponse.dateOfDeath());
    }
}
