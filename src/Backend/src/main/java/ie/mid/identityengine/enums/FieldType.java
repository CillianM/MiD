package ie.mid.identityengine.enums;

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
}
