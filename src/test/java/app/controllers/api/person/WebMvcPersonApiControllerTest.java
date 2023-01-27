package app.controllers.api.person;

import app.services.PersonService;
import app.repositories.PersonRepository;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.UUID;
import java.util.stream.Stream;

@WebMvcTest({PersonApiController.class, PersonService.class, PersonRepository.class})
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class WebMvcPersonApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MappingJackson2HttpMessageConverter converter;

    @Autowired
    private PersonService personService;

    private static Stream<PersonInDTO> testCreatePerson() {
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
    public void testCreatePerson(PersonInDTO personInDTO) throws Exception {
        String id = UUID.randomUUID().toString();

        this.mockMvc.perform(MockMvcRequestBuilders.post("/person")
                .content(converter.getObjectMapper().writeValueAsString(personInDTO)).contentType(ContentType.JSON.toString()))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(
                        converter.getObjectMapper().writeValueAsString(PersonOutDTO.fromPerson(personInDTO.toPerson(id, 0)))));
    }

    private static Stream<Arguments> testPutPersonByIdDP() {
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

    @ParameterizedTest(name = "Put ╯°□°）╯ with {2}")
    @MethodSource("testPutPersonByIdDP")
    public void testPutPersonById(PersonInDTO personPostRequest, PersonInDTO personPutRequest, String verification) throws Exception {

        String personId = personService.createEntity(personPostRequest.toPerson()).id();

        this.mockMvc.perform(MockMvcRequestBuilders.put("/person/{personId}", personId)
                .content(converter.getObjectMapper().writeValueAsString(personPutRequest)).contentType(ContentType.JSON.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(
                        converter.getObjectMapper().writeValueAsString(PersonOutDTO.fromPerson(personPutRequest.toPerson(personId, 0)))));
    }
}
