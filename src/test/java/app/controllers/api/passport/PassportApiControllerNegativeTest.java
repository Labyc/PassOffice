package app.controllers.api.passport;

import app.BaseTest;
import app.ErrorResponse;
import app.controllers.api.passport.dtos.output.BasePassportOutDTO;
import app.controllers.api.passport.dtos.output.PassportNonRFOutDTO;
import app.controllers.api.passport.dtos.output.PassportRFForeignOutDTO;
import app.controllers.api.passport.dtos.output.PassportRFOutDTO;
import app.controllers.api.passport.dtos.patch.PassportRFPatchDTO;
import app.controllers.api.passport.dtos.post.BasePassportPostDTO;
import app.controllers.api.passport.dtos.post.PassportNonRFPostDTO;
import app.controllers.api.passport.dtos.post.PassportRFForeignPostDTO;
import app.controllers.api.passport.dtos.post.PassportRFPostDTO;
import app.exceptions.EntityNotFoundException;
import app.models.person.Person;
import app.services.PassportService;
import app.services.PersonService;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class PassportApiControllerNegativeTest extends BaseTest {
    @Autowired
    PassportService passportService;
    @Autowired
    PersonService personService;

    @Test
    void getPassports() {
    }

    private static Stream<BasePassportPostDTO> postPassportWrongParams() {
        Random random = new Random();
        return Stream.of(
                new PassportRFPostDTO(UUID.randomUUID().toString(), null, String.format("%06d", random.nextInt(999999)), LocalDate.now(), "Department", String.format("%03d-%03d", random.nextInt(999), random.nextInt(999))),
                new PassportRFPostDTO(UUID.randomUUID().toString(), String.format("%04d", random.nextInt(9999)), String.format("%03d", random.nextInt(9999)), LocalDate.now(), "Department with spaces", String.format("%03d-%02d", random.nextInt(999), random.nextInt(99))),
                new PassportRFForeignPostDTO(UUID.randomUUID().toString(), String.format("%06d", random.nextInt(999999)), LocalDate.now(), "Department", LocalDate.now()),
                new PassportRFForeignPostDTO(UUID.randomUUID().toString(), String.format("%06d", random.nextInt(999999)), LocalDate.now(), "Department with Spaces", LocalDate.now().minusDays(1)),
                new PassportNonRFPostDTO(UUID.randomUUID().toString(), String.format("%06d", random.nextInt(999999)), null, "Department", "countryname"),
                new PassportNonRFPostDTO(UUID.randomUUID().toString(), String.format("%06d", random.nextInt(999999)), LocalDate.now(), "department", "Country Name with spaces")
        );
    }


    @ParameterizedTest(name = "Create (* ^ ω ^)")
    @MethodSource
    void postPassportWrongParams(BasePassportPostDTO passportPostDTO) {

        Person person = primitivePerson();
        person = personService.createEntity(person);
        passportPostDTO = passportPostDTO.withPersonId(person.id());
        ValidatableResponse response = RestAssured.with().body(passportPostDTO).post("/passport").then();
        ErrorResponse out = response.statusCode(422)  //TODO WTF
                .extract().response().body().as(ErrorResponse.class);
        //TODO verify errors
    }

    private static Stream<BasePassportPostDTO> postPassportNoPerson() {
        Random random = new Random();
        return Stream.of(
                new PassportRFPostDTO(UUID.randomUUID().toString(), String.format("%04d", random.nextInt(9999)), String.format("%06d", random.nextInt(999999)), LocalDate.now(), "Department", String.format("%03d-%03d", random.nextInt(999), random.nextInt(999))),
                new PassportRFForeignPostDTO(UUID.randomUUID().toString(), String.format("%06d", random.nextInt(999999)), LocalDate.now(), "Department", LocalDate.now().plusYears(10)),
                new PassportNonRFPostDTO(UUID.randomUUID().toString(), String.format("%06d", random.nextInt(999999)), LocalDate.now(), "Department", "Countryname")
        );
    }

    @ParameterizedTest(name = "Create (* ^ ω ^)")
    @MethodSource
    void postPassportNoPerson(BasePassportPostDTO passportPostDTO) {
        ValidatableResponse response = RestAssured.with().body(passportPostDTO).post("/passport").then();
        ErrorResponse out = response.statusCode(404)
                .extract().response().body().as(ErrorResponse.class);
        ErrorResponse expectedError = new ErrorResponse("EntityNotFoundException", new EntityNotFoundException(passportPostDTO.getPersonId()).getMessage());
        Assertions.assertEquals(expectedError.getErrors(), out.getErrors());
    }


    @Test
    @DisplayName("Get (@ * ω *)")
    void getNoPassport() {
        RestAssured.with().pathParam("passportId", UUID.randomUUID().toString())
                .get("/passport/{passportId}").then().statusCode(404); //TODO verify empty body
    }

    private static Stream<Arguments> putPassport() {
        Random random = new Random();
        return Stream.of(
                Arguments.of(new PassportRFPostDTO(UUID.randomUUID().toString(), String.format("%04d", random.nextInt(9999)), String.format("%06d", random.nextInt(999999)), LocalDate.now(), "Department", String.format("%03d-%03d", random.nextInt(999), random.nextInt(999))),
                        new PassportRFPostDTO(UUID.randomUUID().toString(), String.format("%04d", random.nextInt(9999)), String.format("%05d", random.nextInt(99999)), LocalDate.now(), "Department with spaces", String.format("%03d-%03d", random.nextInt(999), random.nextInt(999))),
                        422),
                Arguments.of(new PassportRFForeignPostDTO(UUID.randomUUID().toString(), String.format("%06d", random.nextInt(999999)), LocalDate.now(), "Department", LocalDate.now().plusYears(10)),
                        new PassportRFForeignPostDTO(UUID.randomUUID().toString(), String.format("%06d", random.nextInt(999999)), LocalDate.now(), "Department with Spaces", LocalDate.now().plusDays(1)),
                        200),//TODO
                Arguments.of(new PassportNonRFPostDTO(UUID.randomUUID().toString(), String.format("%06d", random.nextInt(999999)), LocalDate.now(), "Department", "Countryname"),
                        new PassportNonRFPostDTO(UUID.randomUUID().toString(), String.format("%06d", random.nextInt(999999)), LocalDate.now(), "Department", "Country Name with spaces"),
                        200)//TODO
        );
    }


    @ParameterizedTest(name = "Put (* ^ ω ^)")
    @MethodSource
    void putPassport(BasePassportPostDTO passportPostDTO, BasePassportPostDTO passportPut, int expectedHttpStatus) {

        Person person = primitivePerson();
        person = personService.createEntity(person);
        passportPostDTO = passportPostDTO.withPersonId(person.id());
        String passportId = passportService.createEntity(passportPostDTO.toPassport()).getPassportID();

        ValidatableResponse response = RestAssured.with().body(passportPut).pathParam("passportId", passportId).put("/passport/{passportId}").then();
        BasePassportOutDTO passportOut = response.statusCode(expectedHttpStatus)
                .extract().response().body().as(BasePassportOutDTO.class);
        Assertions.assertEquals(passportPostDTO, postDTOFromOutDTO(passportOut));
        Assertions.assertEquals(passportPostDTO, postDTOFromOutDTO(BasePassportOutDTO.fromPassport(passportService.getPassport(passportOut.getPassportID()))));
        Assertions.assertTrue(passportOut.getPassportID() != null && !passportOut.getPassportID().isEmpty());
    }

    @Test
    @DisplayName("Patch Unexisting Passport (* ^ ω ^)")
    void patchUnexistingPassport() {
        String passportId = UUID.randomUUID().toString();
        Random random = new Random();
        PassportRFPatchDTO passportRFPatchDTO = new PassportRFPatchDTO(UUID.randomUUID().toString(), String.format("%04d", random.nextInt(9999)), String.format("%06d", random.nextInt(999999)), LocalDate.now(), "Department", String.format("%03d-%03d", random.nextInt(999), random.nextInt(999)));
        RestAssured.with().body(passportRFPatchDTO).pathParam("passportId", passportId)
                .put("/passport/{passportId}").then().statusCode(404);
        //TODO null body verification
    }

    @Test
    @DisplayName("Put (* ^ ω ^)")
    void patchPassportWithUnexistingPerson() {
        String passportId = UUID.randomUUID().toString();
        Random random = new Random();
        PassportRFPatchDTO passportRFPatchDTO = new PassportRFPatchDTO(UUID.randomUUID().toString(), String.format("%04d", random.nextInt(9999)), String.format("%06d", random.nextInt(999999)), LocalDate.now(), "Department", String.format("%03d-%03d", random.nextInt(999), random.nextInt(999)));
        RestAssured.with().body(passportRFPatchDTO).pathParam("passportId", passportId)
                .put("/passport/{passportId}").then().statusCode(404);
        //TODO null body verification
    }

    @Test
    @DisplayName("Delete (@ * ω *)")
    void deleteUnexistingPassport() {
        RestAssured.with().pathParam("passportId", UUID.randomUUID().toString()).delete("/passport/{passportId}").then()
                .statusCode(HttpStatus.NOT_FOUND.value());
        //TODO entity not found exception
    }

    private BasePassportPostDTO postDTOFromOutDTO(BasePassportOutDTO passportOutDTO) {
        switch (passportOutDTO.getPassportType()) {
            case PASSPORT_RF -> {
                PassportRFOutDTO passportRFOutDTO = (PassportRFOutDTO) passportOutDTO;
                return new PassportRFPostDTO(
                        passportRFOutDTO.getPersonId(), passportRFOutDTO.getSeries(), passportRFOutDTO.getNumber(),
                        passportRFOutDTO.getGivenDate(), passportRFOutDTO.getGivenDepartment(), passportRFOutDTO.getGivenDepartmentCode());
            }
            case PASSPORT_RF_FOREIGN -> {
                PassportRFForeignOutDTO passportRFForeignOutDTO = (PassportRFForeignOutDTO) passportOutDTO;
                return new PassportRFForeignPostDTO(passportRFForeignOutDTO.getPersonId(),
                        passportRFForeignOutDTO.getNumber(), passportRFForeignOutDTO.getGivenDate(), passportRFForeignOutDTO.getGivenDepartment(), passportRFForeignOutDTO.getExpirationDate());
            }
            case PASSPORT_NON_RF -> {
                PassportNonRFOutDTO passportNonRFOutDTO = (PassportNonRFOutDTO) passportOutDTO;
                return new PassportNonRFPostDTO(passportNonRFOutDTO.getPersonId(),
                        passportNonRFOutDTO.getNumber(),
                        passportNonRFOutDTO.getGivenDate(), passportNonRFOutDTO.getGivenDepartment(), passportNonRFOutDTO.getCountry());
            }
        }
        return null;
    }

    private static Person primitivePerson() {
        return new Person(null, "Name", "Surname", "Patronymic", "PlaceOfBirth", LocalDate.of(2000, 1, 1), null, 1);
    }
}