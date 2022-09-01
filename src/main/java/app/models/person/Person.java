package app.models.person;

import lombok.Getter;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.UUID;

public enum Person {
    ;

    private interface Id {
        @NotNull @Positive UUID getId();
    }

    private interface Name {
        @NotNull @Pattern(regexp = "\\p{Upper}\\w*") String getName();
    }

    private interface Surname {
        @NotNull @Pattern(regexp = "\\p{Upper}\\w*") String getSurname();
    }

    private interface Patronymic {
        @Pattern(regexp = "\\p{Upper}\\w*") String getPatronymic();
    }

    private interface PlaceOfBirth {
        @NotNull @Pattern(regexp = "\\p{Upper}.*") String getPlaceOfBirth();
    }

    private interface DateOfBirth {
        @NotNull
        @PastOrPresent
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        Date getDateOfBirth();
    }

    private interface DateOfDeath {
        @PastOrPresent
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        Date getDateOfBirth();
    }

    private interface Version {
        @NotNull
        @Positive
        int getVersion();
    }


    @Value                      //extension may be not good idea. made for: app/DataStorage.java:62
    public static class Request extends PatchRequest implements Name, Surname, Patronymic, PlaceOfBirth, DateOfBirth, DateOfDeath {
        /*private String name;
        private String surname;
        private String patronymic;
        private String placeOfBirth;
        private Date dateOfBirth;
        private Date dateOfDeath;*/
    }

    @Getter
    public static class PatchRequest {
        @Pattern(regexp = "\\p{Upper}\\w*")
        private String name;
        @Pattern(regexp = "\\p{Upper}\\w*")
        private String surname;
        @Pattern(regexp = "\\p{Upper}\\w*")
        private String patronymic;
        @Pattern(regexp = "\\p{Upper}.*")
        private String placeOfBirth;
        @PastOrPresent
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private Date dateOfBirth;
        @PastOrPresent
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private Date dateOfDeath;
    }

    @Getter
    public static class Storage implements Id, Name, Surname, Patronymic, PlaceOfBirth, DateOfBirth, DateOfDeath, Version {
        private UUID id;
        private String name;
        private String surname;
        private String patronymic;
        private String placeOfBirth;
        private Date dateOfBirth;
        private Date dateOfDeath;
        private int version;

        public Storage(Request requestPerson) {
            this.name = requestPerson.getName();
            this.surname = requestPerson.getSurname();
            this.patronymic = requestPerson.getPatronymic();
            this.placeOfBirth = requestPerson.getPlaceOfBirth();
            this.dateOfBirth = requestPerson.getDateOfBirth();
            this.dateOfDeath = requestPerson.getDateOfDeath();
            version = 0;
            id = UUID.randomUUID();
        }

        public void patch(Person.PatchRequest person){
            this.name = person.name == null ? this.name : person.name;
            this.surname = person.surname == null ? this.surname : person.surname;
            this.patronymic = person.patronymic == null ? this.patronymic : person.patronymic;
            this.placeOfBirth = person.placeOfBirth == null ? this.placeOfBirth : person.placeOfBirth;
            this.dateOfBirth = person.dateOfBirth ==null ? this.dateOfBirth : person.dateOfBirth;
            this.dateOfDeath = person.dateOfDeath == null ? this.dateOfDeath : person.dateOfDeath;
            version++;
        }
    }

    @Value
    public static class Response implements Id, Name, Surname, Patronymic, PlaceOfBirth, DateOfBirth, DateOfDeath {
        private UUID id;
        private String name;
        private String surname;
        private String patronymic;
        private String placeOfBirth;
        private Date dateOfBirth;
        private Date dateOfDeath;
        //version???

        public Response(Storage storagePerson) {
            this.id = storagePerson.id;
            this.name = storagePerson.name;
            this.surname = storagePerson.surname;
            this.patronymic = storagePerson.patronymic;
            this.placeOfBirth = storagePerson.placeOfBirth;
            this.dateOfBirth = storagePerson.dateOfBirth;
            this.dateOfDeath = storagePerson.dateOfDeath;
        }
    }


}
