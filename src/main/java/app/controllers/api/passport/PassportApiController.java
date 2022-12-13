package app.controllers.api.passport;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/passport")
public class PassportApiController {
/*

    private final DataStorage dataStorage;

    public PassportApiController(DataStorage dataStorage) {
        this.dataStorage = Objects.requireNonNull(dataStorage);
    }


    @Deprecated
    @GetMapping("/passports")
    public ResponseEntity<?> getPassports(){
        return new ResponseEntity<>(dataStorage, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> postPassport(@RequestBody Map<String, String> passportMap){
        if (PassportType.valueOf(passportMap.get("passportType")).equals(PassportType.RF_PASSPORT)){
            //passportMap.toString();
            PassportRFInDTO passportRF = (PassportRFInDTO) passportMap;
            dataStorage.addPassport(passportRF);
        }
        /*if (dataStorage.findExistingPassport(passport.getPassportId())!=null){ //TODO some more validations
            return new ResponseEntity<>("Passport with the same series and number is already exists" ,HttpStatus.NOT_ACCEPTABLE); //TODO normal exception (in Json format)???
        }
        dataStorage.addPassport(passport);*//*
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }*/

}
