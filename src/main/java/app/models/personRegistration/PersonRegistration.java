package app.models.personRegistration;

import lombok.Data;

import java.util.Date;
import java.util.List;


@Data
public class PersonRegistration {
    private String registrationAddress;
    private Date registrationDate;
    private String registrationDepartment;
    private List<PersonRegistration> previousRegistrations;
}
