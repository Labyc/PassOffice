package passportOfficeTests.personApiTests;

import app.controllers.api.person.PersonInDTO;
import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import passportOfficeTests.PassportOfficeBaseTest;

import java.time.LocalDate;
import java.util.stream.Stream;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)

public class PersonTestsNegative extends PassportOfficeBaseTest {
    private static Stream<PersonInDTO> testPostPersonNegative() {
        return Stream.of(
        new PersonInDTO("Name1", "Surname1", null, "PlaceOfBirth1", LocalDate.of(1980, 1, 1), null));
    }


    @ParameterizedTest
    @MethodSource
    @DisplayName("¯\\_(ツ)_/¯")
    public void testPostPersonNegative(PersonInDTO personInDTO) {
        RestAssured.with().body(personInDTO).post("/person").then().statusCode(422);
        //TODO verify error response
                /*.body("name", Matchers.equalTo(personInDTO.name()))
                .body("surname", Matchers.equalTo(personInDTO.surname()))
                .body("patronymic", Matchers.equalTo(personInDTO.patronymic()))
                .body("placeOfBirth", Matchers.equalTo(personInDTO.placeOfBirth()))
                .body("dateOfBirth", Matchers.equalTo(personInDTO.dateOfBirth().toString()))
                .body("dateOfDeath", Matchers.equalTo(personInDTO.dateOfDeath() != null ? personInDTO.dateOfDeath().toString() : null));*/
    }

}
