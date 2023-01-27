package app.controllers.api.passport;

import app.BaseTest;
import app.controllers.api.passport.dtos.input.BasePassportPostDTO;
import app.controllers.api.passport.dtos.input.PassportNonRFPostDTO;
import app.controllers.api.passport.dtos.input.PassportRFForeignPostDTO;
import app.controllers.api.passport.dtos.input.PassportRFPostDTO;
import app.controllers.api.passport.dtos.output.BasePassportOutDTO;
import app.controllers.api.passport.dtos.output.PassportNonRFOutDTO;
import app.controllers.api.passport.dtos.output.PassportRFForeignOutDTO;
import app.controllers.api.passport.dtos.output.PassportRFOutDTO;
import app.controllers.api.person.PersonInDTO;
import app.models.passport.PassportType;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.Collections;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class PassportApiControllerTest extends BaseTest {

    @Test
    void getPassports() {
    }

    private static Stream<BasePassportPostDTO> postPassport(){
        return Stream.of(new PassportRFPostDTO(PassportType.PASSPORT_RF, "", "0000", "000000", LocalDate.now(), "Department", "00000", Collections.EMPTY_LIST));
    }


    @ParameterizedTest(name = "Create (* ^ ω ^)")
    @MethodSource
    void postPassport(BasePassportPostDTO passportPostDTO) {
        ValidatableResponse response = RestAssured.with().body(passportPostDTO).post("/passport").then();
        BasePassportOutDTO basePassportOutDTO = response.statusCode(201)
                .extract().response().body().as(BasePassportOutDTO.class);

    }


   private BasePassportPostDTO postDTOFromOutDTO(BasePassportOutDTO passportOutDTO){
       switch (passportOutDTO.getPassportType()) {
           case PASSPORT_RF -> {
               PassportRFOutDTO passportRFOutDTO = (PassportRFOutDTO) passportOutDTO;
               return new PassportRFPostDTO(passportRFOutDTO.getPassportType(),
                       passportRFOutDTO.getPersonId(), passportRFOutDTO.getSeries(), passportRFOutDTO.getNumber(),
                       passportRFOutDTO.getGivenDate(), passportRFOutDTO.getGivenDepartment(), passportRFOutDTO.getGivenDepartmentCode(),
                       passportRFOutDTO.getOtherPassportIds());
           }
           case PASSPORT_RF_FOREIGN -> {
               PassportRFForeignOutDTO passportRFForeignOutDTO = (PassportRFForeignOutDTO) passportOutDTO;
               return new PassportRFForeignPostDTO(passportRFForeignOutDTO.getPassportType(), passportRFForeignOutDTO.getPersonId(),
                       passportRFForeignOutDTO.getNumber(), passportRFForeignOutDTO.getGivenDate(), passportRFForeignOutDTO.getGivenDepartment(),
                       passportRFForeignOutDTO.getOtherPassportIds());
           }
           case PASSPORT_NON_RF -> {
               PassportNonRFOutDTO passportNonRFOutDTO = (PassportNonRFOutDTO) passportOutDTO;
               return new PassportNonRFPostDTO(passportNonRFOutDTO.getPassportType(), passportNonRFOutDTO.getPersonId(), passportNonRFOutDTO.getNumber(),
                       passportNonRFOutDTO.getGivenDate(), passportNonRFOutDTO.getGivenDepartment(), passportNonRFOutDTO.getOtherPassportIds());
           }
       }
       return null;
   }
}