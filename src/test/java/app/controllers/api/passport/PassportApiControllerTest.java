package app.controllers.api.passport;

import app.BaseTest;
import app.controllers.api.passport.dtos.output.BasePassportOutDTO;
import app.controllers.api.passport.dtos.output.PassportNonRFOutDTO;
import app.controllers.api.passport.dtos.output.PassportRFForeignOutDTO;
import app.controllers.api.passport.dtos.output.PassportRFOutDTO;
import app.controllers.api.passport.dtos.patch.PassportNonRFPatchDTO;
import app.controllers.api.passport.dtos.patch.PassportRFForeignPatchDTO;
import app.controllers.api.passport.dtos.patch.PassportRFPatchDTO;
import app.controllers.api.passport.dtos.post.BasePassportPostDTO;
import app.controllers.api.passport.dtos.post.PassportNonRFPostDTO;
import app.controllers.api.passport.dtos.post.PassportRFForeignPostDTO;
import app.controllers.api.passport.dtos.post.PassportRFPostDTO;
import app.models.passport.BasePassport;
import app.models.passport.PassportNonRF;
import app.models.passport.PassportRF;
import app.models.passport.PassportRFForeign;
import app.models.person.Person;
import app.services.PassportService;
import app.services.PersonService;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Assertions;
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
class PassportApiControllerTest extends BaseTest {
    @Autowired
    PassportService passportService;
    @Autowired
    PersonService personService;

    @Test
    void getPassports() {
    }

    private static Stream<BasePassportPostDTO> postPassport() {
        Random random = new Random();
        return Stream.of(
                new PassportRFPostDTO(UUID.randomUUID().toString(), String.format("%04d", random.nextInt(9999)), String.format("%06d", random.nextInt(999999)), LocalDate.now(), "Department", String.format("%03d-%03d", random.nextInt(999), random.nextInt(999))),
                new PassportRFPostDTO(UUID.randomUUID().toString(), String.format("%04d", random.nextInt(9999)), String.format("%06d", random.nextInt(999999)), LocalDate.now(), "Department with spaces", String.format("%03d-%03d", random.nextInt(999), random.nextInt(999))),
                new PassportRFForeignPostDTO(UUID.randomUUID().toString(), String.format("%06d", random.nextInt(999999)), LocalDate.now(), "Department", LocalDate.now().plusYears(10)),
                new PassportRFForeignPostDTO(UUID.randomUUID().toString(), String.format("%06d", random.nextInt(999999)), LocalDate.now(), "Department with Spaces", LocalDate.now().plusDays(1)),
                new PassportNonRFPostDTO(UUID.randomUUID().toString(), String.format("%06d", random.nextInt(999999)), LocalDate.now(), "Department", "Countryname"),
                new PassportNonRFPostDTO(UUID.randomUUID().toString(), String.format("%06d", random.nextInt(999999)), LocalDate.now(), "Department", "Country Name with spaces")
        );
    }


    @ParameterizedTest(name = "Create (* ^ ω ^)")
    @MethodSource
    void postPassport(BasePassportPostDTO passportPostDTO) {

        passportPostDTO = passportPostDTO.withPersonId(personService.createEntity(primitivePerson()).id());

        ValidatableResponse response = RestAssured.with().body(passportPostDTO).post("/passport").then();
        BasePassportOutDTO passportOut = response.statusCode(HttpStatus.CREATED.value())
                .extract().response().body().as(BasePassportOutDTO.class);
        Assertions.assertEquals(passportPostDTO, postDTOFromOutDTO(passportOut));
        Assertions.assertEquals(passportPostDTO, postDTOFromOutDTO(BasePassportOutDTO.fromPassport(passportService.getPassport(passportOut.getPassportID()))));
        Assertions.assertTrue(passportOut.getPassportID() != null && !passportOut.getPassportID().isEmpty());
    }

    private static Stream<BasePassport> getPassport() {
        Random random = new Random();
        return Stream.of(
                new PassportRF(UUID.randomUUID().toString(), UUID.randomUUID().toString(), String.format("%04d", random.nextInt(9999)), String.format("%06d", random.nextInt(999999)), LocalDate.now(), "Department", String.format("%03d-%03d", random.nextInt(999), random.nextInt(999))),
                new PassportRFForeign(UUID.randomUUID().toString(), UUID.randomUUID().toString(), String.format("%06d", random.nextInt(999999)), LocalDate.now(), "Department", LocalDate.now().plusYears(10)),
                new PassportNonRF(UUID.randomUUID().toString(), UUID.randomUUID().toString(), String.format("%06d", random.nextInt(999999)), LocalDate.now(), "Department", "Country Name with spaces")
        );
    }


    @ParameterizedTest(name = "Get (@ o ω o)")
    @MethodSource
    void getPassport(BasePassport passport) {
        passport = passport.withPersonId(personService.createEntity(primitivePerson()).id());
        passport = passport.withPassportID(passportService.createEntity(passport).getPassportID());

        ValidatableResponse response = RestAssured.with().pathParam("passportId", passport.getPassportID()).get("/passport/{passportId}").then();
        BasePassportOutDTO passportOut = response.statusCode(HttpStatus.OK.value())
                .extract().response().body().as(BasePassportOutDTO.class);
        Assertions.assertEquals(BasePassportOutDTO.fromPassport(passport), passportOut);
    }

    private static Stream<Arguments> putPassport() {
        Random random = new Random();
        return Stream.of(
                Arguments.of(new PassportRFPostDTO(UUID.randomUUID().toString(), String.format("%04d", random.nextInt(9999)), String.format("%06d", random.nextInt(999999)), LocalDate.now(), "Department", String.format("%03d-%03d", random.nextInt(999), random.nextInt(999))),
                        new PassportRFPostDTO(UUID.randomUUID().toString(), String.format("%04d", random.nextInt(9999)), String.format("%06d", random.nextInt(999999)), LocalDate.now(), "Department with spaces", String.format("%03d-%03d", random.nextInt(999), random.nextInt(999)))),
                Arguments.of(new PassportRFForeignPostDTO(UUID.randomUUID().toString(), String.format("%06d", random.nextInt(999999)), LocalDate.now(), "Department", LocalDate.now().plusYears(10)),
                        new PassportRFForeignPostDTO(UUID.randomUUID().toString(), String.format("%06d", random.nextInt(999999)), LocalDate.now(), "Department with Spaces", LocalDate.now().plusDays(1))),
                Arguments.of(new PassportNonRFPostDTO(UUID.randomUUID().toString(), String.format("%06d", random.nextInt(999999)), LocalDate.now(), "Department", "Countryname"),
                        new PassportNonRFPostDTO(UUID.randomUUID().toString(), String.format("%06d", random.nextInt(999999)), LocalDate.now(), "Department", "Country Name with spaces"))
        );
    }


    @ParameterizedTest(name = "Put (* ^ ω ^)")
    @MethodSource
    void putPassport(BasePassportPostDTO passportPostDTO, BasePassportPostDTO passportPut) {

        passportPostDTO = passportPostDTO.withPersonId(personService.createEntity(primitivePerson()).id());
        String passportId = passportService.createEntity(passportPostDTO.toPassport()).getPassportID();
        passportPut = passportPut.withPersonId(passportPostDTO.getPersonId());
        ValidatableResponse response = RestAssured.with().body(passportPut).pathParam("passportId", passportId).put("/passport/{passportId}").then();
        BasePassportOutDTO passportOut = response.statusCode(HttpStatus.OK.value())
                .extract().response().body().as(BasePassportOutDTO.class);

        Assertions.assertEquals(passportPut.toPassport().withPassportID(passportId), passportService.getPassport(passportId));
        Assertions.assertEquals(passportPut, postDTOFromOutDTO(passportOut));
        Assertions.assertTrue(passportOut.getPassportID() != null && !passportOut.getPassportID().isEmpty());
    }

    private static Stream<Arguments> patchPassport() {
        Random random = new Random();
        return Stream.of(
                Arguments.of(new PassportRFPostDTO(UUID.randomUUID().toString(), String.format("%04d", random.nextInt(9999)), String.format("%06d", random.nextInt(999999)), LocalDate.now(), "Department", String.format("%03d-%03d", random.nextInt(999), random.nextInt(999))),
                        new PassportRFPatchDTO(null, null, null, null, null, null),
                        new PassportRFPostDTO(UUID.randomUUID().toString(), String.format("%04d", random.nextInt(9999)), String.format("%06d", random.nextInt(999999)), LocalDate.now(), "Department", String.format("%03d-%03d", random.nextInt(999), random.nextInt(999)))),
                Arguments.of(new PassportRFForeignPostDTO(UUID.randomUUID().toString(), String.format("%06d", random.nextInt(999999)), LocalDate.now(), "Department", LocalDate.now().plusYears(10)),
                        new PassportRFForeignPatchDTO(null, null, null, null, null),
                        new PassportRFForeignPostDTO(UUID.randomUUID().toString(), String.format("%06d", random.nextInt(999999)), LocalDate.now(), "Department", LocalDate.now().plusYears(10))),
                Arguments.of(new PassportNonRFPostDTO(UUID.randomUUID().toString(), String.format("%06d", random.nextInt(999999)), LocalDate.now(), "Department", "Countryname"),
                        new PassportNonRFPatchDTO(null, null, null, null, null),
                        new PassportNonRFPostDTO(UUID.randomUUID().toString(), String.format("%06d", random.nextInt(999999)), LocalDate.now(), "Department", "Countryname"))
        );
    }

    @ParameterizedTest(name = "Patch (* - ω -)")
    @MethodSource({"patchPassport"})
    void patchPassport(BasePassportPostDTO passportPostDTO, PassportRFPatchDTO passportPatch, BasePassportPostDTO verificationPassport) {
        passportPostDTO = passportPostDTO.withPersonId(personService.createEntity(primitivePerson()).id());
        String passportId = passportService.createEntity(passportPostDTO.toPassport()).getPassportID();

        ValidatableResponse response = RestAssured.with().body(passportPatch).pathParam("passportId", passportId).patch("/passport/{passportId}").then();
        BasePassportOutDTO passportOut = response.statusCode(HttpStatus.OK.value())
                .extract().response().body().as(BasePassportOutDTO.class);

        Assertions.assertEquals(passportPostDTO.toPassport().withPassportID(passportId), passportService.getPassport(passportId));
        Assertions.assertEquals(verificationPassport.withPersonId(passportPostDTO.getPersonId()), postDTOFromOutDTO(passportOut));
        Assertions.assertTrue(passportOut.getPassportID() != null && !passportOut.getPassportID().isEmpty());
    }

    private static Stream<BasePassport> deletePassport() {
        Random random = new Random();
        return Stream.of(
                new PassportRF(null, UUID.randomUUID().toString(), String.format("%04d", random.nextInt(9999)), String.format("%06d", random.nextInt(999999)), LocalDate.now(), "Department", String.format("%03d-%03d", random.nextInt(999), random.nextInt(999))),
                new PassportRFForeign(null, UUID.randomUUID().toString(), String.format("%06d", random.nextInt(999999)), LocalDate.now(), "Department", LocalDate.now().plusYears(10)),
                new PassportNonRF(null, UUID.randomUUID().toString(), String.format("%06d", random.nextInt(999999)), LocalDate.now(), "Department", "Countryname")
        );
    }

    @ParameterizedTest(name = "Delete (* - ω -)")
    @MethodSource
    void deletePassport(BasePassport passport) {
        String passportId = passportService.createEntity(passport.withPersonId(personService.createEntity(primitivePerson()).id())).getPassportID();
        RestAssured.with().pathParam("passportId", passportId).delete("/passport/{passportId}").then()
                .statusCode(HttpStatus.NO_CONTENT.value());
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