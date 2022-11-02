package app.models.personRegistration;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@Data
public class PersonRegistration {
    private String registrationAddress;
    private LocalDate registrationDate;
    private String registrationDepartment;
    private List<PersonRegistration> previousRegistrations;
}
