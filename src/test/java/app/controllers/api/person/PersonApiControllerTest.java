package app.controllers.api.person;

import app.BaseTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class PersonApiControllerTest extends BaseTest {

    private static Stream<PersonInDTO> createPerson() {
        return Stream.of(
                new PersonInDTO("Name", "Surname", null, "Place of birth", LocalDate.of(1995, 3, 2), null),
                new PersonInDTO("Namee", "Surnamee", "Patronymicc", "PlaceOfBirthh", LocalDate.of(2022, 1, 1), LocalDate.of(2022, 1, 2)),
                new PersonInDTO("Name two", "Surname", null, "Place of birth", LocalDate.of(2002, 10, 1), null),
                new PersonInDTO("Name three", "Surname two", null, "Place of birth", LocalDate.of(2000, 1, 1), null),
                new PersonInDTO("Name", "Surname three", null, "Place of birth", LocalDate.of(2000, 1, 1), null)
        );

    }


    @ParameterizedTest(name = "Create (* ^ ω ^)")
    @MethodSource
    public void createPerson(PersonInDTO personInDTO) {
        ValidatableResponse response = RestAssured.with().body(personInDTO).post("/person").then();
        response.statusCode(201)
                .body("name", Matchers.equalTo(personInDTO.name()))
                .body("surname", Matchers.equalTo(personInDTO.surname()))
                .body("patronymic", Matchers.equalTo(personInDTO.patronymic()))
                .body("placeOfBirth", Matchers.equalTo(personInDTO.placeOfBirth()))
                .body("dateOfBirth", Matchers.equalTo(personInDTO.dateOfBirth().toString()))
                .body("dateOfDeath", Matchers.equalTo(personInDTO.dateOfDeath() != null ? personInDTO.dateOfDeath().toString() : null));

    }


    @Test
    void getPersonById() {
    }

    private static Stream<Arguments> putPersonByIdDP() {
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

    @ParameterizedTest(name = "Put ╯°□°）╯ with '{2}'")
    @MethodSource("putPersonByIdDP")
    public void putPersonById(PersonInDTO personPostRequest, PersonInDTO personPutRequest, String verification) {
        //Todo internally post person via service

        PersonOutDTO personResponse = RestAssured.with().body(personPostRequest).post("/person")
                .then().statusCode(201).extract().body().as(PersonOutDTO.class);
        personResponse = RestAssured.with().body(personPutRequest).pathParam("personId", personResponse.id()).put("/person/{personId}")
                .then().statusCode(200).extract().body().as(PersonOutDTO.class);

        //Todo verify person via service
        Assertions.assertNotEquals(null, personResponse.id());
        Assertions.assertEquals(personPutRequest.name(), personResponse.name());
        Assertions.assertEquals(personPutRequest.surname(), personResponse.surname());
        Assertions.assertEquals(personPutRequest.patronymic(), personResponse.patronymic());
        Assertions.assertEquals(personPutRequest.placeOfBirth(), personResponse.placeOfBirth());
        Assertions.assertEquals(personPutRequest.dateOfBirth(), personResponse.dateOfBirth());
        Assertions.assertEquals(personPutRequest.dateOfDeath(), personResponse.dateOfDeath());
    }

    private static Stream<Arguments> patchPersonById() {
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
                        new PersonInDTO(null, null, null, null, null, null),
                        "Date of death from smth to null") //TODO
        );
    }

    @ParameterizedTest
    @MethodSource({"patchPersonById", "putPersonByIdDP"})
    @DisplayName("(◕‿◕)")
    public void patchPersonById(PersonInDTO personPostRequest, PersonInDTO personPatchRequest, String verification) {
        //Todo internally post person via service

        PersonOutDTO personResponse = RestAssured.with().body(personPostRequest).post("/person")
                .then().statusCode(201).extract().body().as(PersonOutDTO.class);
        personResponse = RestAssured.with().body(personPatchRequest).pathParam("personId", personResponse.id()).patch("/person/{personId}")
                .then().statusCode(200).extract().body().as(PersonOutDTO.class);

        //Todo verify person via service

        Assertions.assertNotEquals(null, personResponse.id());
        Assertions.assertEquals(personPatchRequest.name() != null ? personPatchRequest.name() : personPostRequest.name(), personResponse.name());
        Assertions.assertEquals(personPatchRequest.surname() != null ? personPatchRequest.surname() : personPostRequest.surname(), personResponse.surname());
        Assertions.assertEquals(personPatchRequest.patronymic() != null ? personPatchRequest.patronymic() : personPostRequest.patronymic(), personResponse.patronymic());
        Assertions.assertEquals(personPatchRequest.placeOfBirth() != null ? personPatchRequest.placeOfBirth() : personPostRequest.placeOfBirth(), personResponse.placeOfBirth());
        Assertions.assertEquals(personPatchRequest.dateOfBirth() != null ? personPatchRequest.dateOfBirth() : personPostRequest.dateOfBirth(), personResponse.dateOfBirth());
        Assertions.assertEquals(personPatchRequest.dateOfDeath() != null ? personPatchRequest.dateOfDeath() : personPostRequest.dateOfDeath(), personResponse.dateOfDeath());
    }

    @Test
    void deletePersonById() {
        //Todo internally post person via service
        //delete person
        //Verify no such person via service
    }

    private static List<Map<String, String>> findPerson() {
        List<Map<String, String>> requests = new ArrayList<>();

        HashMap<String, String> map = new HashMap<>();
        map.put("personName", "Name");
        map.put("personSurname", "Surname");
        map.put("birthStartDate", LocalDate.MIN.toString());
        map.put("birthEndDate", LocalDate.now().toString());
        requests.add(map);

        map = new HashMap<>();
        map.put("personName", "Name");
        map.put("personSurname", "Surname");
        requests.add(map);

        map = new HashMap<>();
        map.put("personName", "Name");
        requests.add(map);

        map = new HashMap<>();
        map.put("personSurname", "Surname");
        requests.add(map);

        map = new HashMap<>();
        map.put("personName", "Name");
        map.put("personSurname", "Surname");
        map.put("birthStartDate", LocalDate.MIN.toString());
        requests.add(map);

        map = new HashMap<>();
        map.put("birthStartDate", LocalDate.MIN.toString());
        map.put("birthEndDate", LocalDate.now().toString());
        requests.add(map);

        map = new HashMap<>();
        map.put("birthEndDate", LocalDate.now().toString());
        requests.add(map);

        return requests;
    }

    @ParameterizedTest
    @DisplayName("(⌐■_■)")
    @MethodSource
    public void findPerson(Map<String, String> requestQueryParameters) {
        //Todo internally post person via service

        List<PersonOutDTO> foundPersonsList = RestAssured.with().queryParams(requestQueryParameters).get("/person").then().statusCode(200).extract().body().as(new ObjectMapper().getTypeFactory().constructCollectionType(List.class, PersonOutDTO.class));//.class);
        for (PersonOutDTO person : foundPersonsList) {
            if (requestQueryParameters.get("personName") != null)
                Assertions.assertEquals(person.name(), requestQueryParameters.get("personName"));
            if (requestQueryParameters.get("personSurname") != null)
                Assertions.assertEquals(person.surname(), requestQueryParameters.get("personSurname"));
            if (requestQueryParameters.get("birthStartDate") != null)
                Assertions.assertTrue(person.dateOfBirth().isAfter(LocalDate.parse(requestQueryParameters.get("birthStartDate"))));
            if (requestQueryParameters.get("birthEndDate") != null)
                Assertions.assertTrue(person.dateOfBirth().isBefore(LocalDate.parse(requestQueryParameters.get("birthEndDate"))));
        }
    }
}