package ie.mid.enums;

/**
 * Created by Cillian on 22/10/2017.
 */

public enum DataType {
    KEY("KEY"),
    EXPIRY("EXPIRY"),
    FIRSTNAME("FIRSTNAME"),
    SURNAME("SURNAME"),
    ADDRESS("ADDRESS"),
    BIRTHDAY("BIRTHDAY");

    private String dataType;

    DataType(String dataType) {
        this.dataType = dataType;
    }

    public static DataType findDataType(String dataTypeString) {
        for (DataType dataType : values()) {
            if (dataType.dataType.equals(dataTypeString)) {
                return dataType;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return dataType;
    }
}
