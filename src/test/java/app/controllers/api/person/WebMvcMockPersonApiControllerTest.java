package app.controllers.api.person;

import app.services.PersonService;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.UUID;
import java.util.stream.Stream;

@WebMvcTest(PersonApiController.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class WebMvcMockPersonApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MappingJackson2HttpMessageConverter converter;

    @MockBean
    private PersonService personServiceMock;

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
    public void createPerson(PersonInDTO personInDTO) throws Exception {
        String id = UUID.randomUUID().toString();
        Mockito.when(personServiceMock.createEntity(personInDTO.toPerson())).thenReturn(personInDTO.toPerson(id, 0));

        this.mockMvc.perform(MockMvcRequestBuilders.post("/person")
                        .content(converter.getObjectMapper().writeValueAsString(personInDTO)).contentType(ContentType.JSON.toString()))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(
                        converter.getObjectMapper().writeValueAsString(PersonOutDTO.fromPerson(personInDTO.toPerson(id, 0)))));
    }
}
