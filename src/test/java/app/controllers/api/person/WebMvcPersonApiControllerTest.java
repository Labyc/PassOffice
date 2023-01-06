package app.controllers.api.person;

import app.PersonProcessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.UUID;
import java.util.stream.Stream;

@WebMvcTest(PersonApiController.class)
public class WebMvcPersonApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonProcessor personProcessorMock;

    private static Stream<PersonInDTO> createPerson() {
        return Stream.of(
                new PersonInDTO("Name", "Surname", null, "Place of birth", LocalDate.of(1995, 3, 2), null),
                new PersonInDTO("Namee", "Surnamee", "Patronymicc", "PlaceOfBirthh", LocalDate.of(2022, 1, 1), LocalDate.of(2022, 1, 2)),
                new PersonInDTO("Name two", "Surname", null, "Place of birth", LocalDate.of(2002, 10, 1), null),
                new PersonInDTO("Name three", "Surname two", null, "Place of birth", LocalDate.of(2000, 1, 1), null),
                new PersonInDTO("Name", "Surname three", null, "Place of birth", LocalDate.of(2000, 1, 1), null)
        );

    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("(* ^ ω ^)")// ╯°□°）╯
    public void createPerson(PersonInDTO personInDTO) throws Exception {
        String id = UUID.randomUUID().toString();
        Mockito.when(personProcessorMock.createEntity(personInDTO.toPerson())).thenReturn(personInDTO.toPerson(id, 0));

        this.mockMvc.perform(MockMvcRequestBuilders.post("/person").content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(personInDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()..string(new ObjectMapper().writeValueAsString(personInDTO.toPerson(id, 0))));

    }
}
