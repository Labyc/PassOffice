package app.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;

@Data
@AllArgsConstructor
public class Passport {

    //private char[] series = new char[4];
    //private char[] number = new char[6];
    @Digits(integer = 4, fraction = 0, message = "Invalid Passport series")
    private String series;
    @Digits(integer = 6, fraction = 0, message = "Invalid Passport number")
    private String number;
    @Size(min = 2, message = "Name must be at least 2 chars")
    private String name;
    @NotBlank
    private String surname;
    @NotBlank
    private String patronymic;
    @NotBlank
    private String placeOfBirth;
    private Date dateOfBirth;
    private Date givenDate;
    //private char[] givenDepartmentCode = new char[6];
    @Digits(integer = 6, fraction = 0, message = "Invalid Department code")
    private String givenDepartmentCode;
    private List<Passport> previousPassports;
    private PersonRegistration registrationData;

    public static Passport createRandomPassport(){
        Random random = new Random();
        byte[] charArray = new byte[6];

        String series = String.valueOf(random.nextInt(1000, 9999));
        String number = String.valueOf(random.nextInt(100000, 999999));
        random.nextBytes(charArray);
        String name = String.valueOf(charArray);
        random.nextBytes(charArray);
        String surname = String.valueOf(charArray);
        random.nextBytes(charArray);
        String patronymic = String.valueOf(charArray);
        random.nextBytes(charArray);
        String placeOfBirth = String.valueOf(charArray);
        Date birthDate = new Date(random.nextInt(122), random.nextInt(1, 12), random.nextInt(1,29));
        Date givenDate = new Date(random.nextInt(122), random.nextInt(1, 12), random.nextInt(1,29));
        random.nextBytes(charArray);
        String givenDepartmentCode = String.valueOf(charArray);

        return new Passport(series, number, name, surname, patronymic, placeOfBirth, birthDate, givenDate, givenDepartmentCode, new ArrayList<>(), null);
    }

}
