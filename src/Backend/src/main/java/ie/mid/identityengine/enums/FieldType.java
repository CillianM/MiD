package ie.mid.identityengine.enums;

public enum FieldType {
    KEY("KEY"),
    EXPIRY("EXPIRY"),
    FIRSTNAME("FIRSTNAME"),
    SURNAME("SURNAME"),
    ADDRESS("ADDRESS"),
    BIRTHDAY("BIRTHDAY");

    private String fieldType;

    FieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public static FieldType findFieldType(String fieldTypeString){
        for(FieldType fieldType : values()){
            if( fieldType.fieldType.equals(fieldTypeString)){
                return fieldType;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return fieldType;
    }
}
