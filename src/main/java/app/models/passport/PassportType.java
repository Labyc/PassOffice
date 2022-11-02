package app.models.passport;

import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
public enum PassportType implements Serializable {
    RF_PASSPORT("RFPassport"),
    FOREIGN_RF_PASSPORT("ForeignRFPassport"),
    NON_RF_PASSPORT("NonRFPassport");

    private final String name;
    public static PassportType getTypeByName(String typeName){
        for (PassportType type: PassportType.values()){
            if (type.name.equals(typeName))
                return type;
        }
        throw new EnumConstantNotPresentException(PassportType.class, typeName);
    }
}
