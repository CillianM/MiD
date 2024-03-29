package ie.mid.identityengine.enums;

import java.util.ArrayList;
import java.util.List;

public enum FieldType {
    KEY("KEY"),
    EXPIRY("EXPIRY"),
    FIRSTNAME("FIRSTNAME"),
    SURNAME("SURNAME"),
    ADDRESS("ADDRESS"),
    BIRTHDAY("BIRTHDAY");

    private String type;

    FieldType(String type) {
        this.type = type;
    }

    public static FieldType findFieldType(String fieldTypeString){
        for(FieldType fieldType : values()){
            if (fieldType.type.equals(fieldTypeString)) {
                return fieldType;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return type;
    }

    public static List<String> getFieldTypeList() {
        List<String> fieldTypes = new ArrayList<>();
        for(FieldType fieldType : values()){
            fieldTypes.add(fieldType.type);
        }
        return fieldTypes;
    }
}
