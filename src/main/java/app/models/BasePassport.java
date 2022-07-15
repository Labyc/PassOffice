package app.models;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@Data
public abstract class BasePassport {

    private String passportId;
    @NotNull
    private String personId;
    private Date givenDate;
    @NotNull
    private String givenDepartment;
    private List<String> previousPassportsIds;
    private boolean isActive;

   /* @Deprecated
    public static BasePassport createRandomPassport(){
        Random random = new Random();
        byte[] charArray = new byte[6];

        String passportId = String.valueOf(random.nextInt(1, 99999));
        String personId = String.valueOf(random.nextInt(1, 99999));
        String number = String.valueOf(random.nextInt(100000, 999999));
        Date givenDate = new Date(random.nextInt(122), random.nextInt(1, 12), random.nextInt(1,29));
        random.nextBytes(charArray);
        String givenDepartmentCode = String.valueOf(charArray);

        return new BasePassport(passportId, number, personId, givenDate, givenDepartmentCode, new ArrayList<>(), true);
    }*/

}
