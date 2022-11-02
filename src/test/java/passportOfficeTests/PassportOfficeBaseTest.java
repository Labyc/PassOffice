package passportOfficeTests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@Slf4j
@SpringBootTest(classes = {TestConfiguration.class})
@ContextConfiguration(classes = TestConfiguration.class)
public class PassportOfficeBaseTest {

    @BeforeAll
    static void setup() {
        log.info("setup started");
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://127.0.0.1")
                .setPort(8090)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        RestAssured.requestSpecification = requestSpec;
    }
}
