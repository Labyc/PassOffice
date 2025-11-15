package app.models.passport;

import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
public enum PassportType implements Serializable {
    PASSPORT_RF("PassportRF"),
    PASSPORT_RF_FOREIGN("PassportRFForeign"),
    PASSPORT_NON_RF("PassportNonRF");

    private final String name;

    public static PassportType getTypeByName(String typeName) {
        for (PassportType type : PassportType.values()) {
            if (type.name.equals(typeName))
                return type;
        }
        throw new EnumConstantNotPresentException(PassportType.class, typeName);
    }

}
