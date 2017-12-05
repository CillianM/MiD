package ie.mid.identityengine.enums;

public enum KeyClass {
    USER("USER"),
    PARTY("PARTY");

    private String keyClass;

    KeyClass(String keyClass) {
        this.keyClass = keyClass;
    }

    @Override
    public String toString() {
        return keyClass;
    }
}
