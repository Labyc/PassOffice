package passportOfficeTests;

import app.controllers.api.PassportAPIController;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;
import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
//@SpringBootTest(classes = {TestConfiguration.class})
@WebMvcTest(controllers = {PassportAPIController.class})
@ContextConfiguration(classes = TestConfiguration.class)
public class PassportOfficeBaseTest {

    private final MockMvc mockMvc;

    @Autowired
    PassportOfficeBaseTest(MockMvc mockMvc){
        this.mockMvc = Objects.requireNonNull( mockMvc );
    }
    @Test
    public void someTest() throws Exception {
        log.info("log");
        URI uri = new URI("http://192.168.37.200:8090/api/passports");
        /*URL url = new URL("http://192.168.37.200:8090/api/passports");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        int status = con.getResponseCode();
        log.info("status:'{}'",status);*/
        mockMvc.perform(get(uri))
                .andExpect(status().isOk());
    }
}
