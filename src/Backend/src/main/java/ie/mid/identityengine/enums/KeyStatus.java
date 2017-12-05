package ie.mid.identityengine.enums;

public enum KeyStatus {
    ACTIVE("ACTIVE"),
    UPGRADED("UPGRADED"),
    COMPROMISED("COMPROMISED"),
    EXPIRED("EXPIRED"),
    DELETED("DELETED");

    private String keyStatus;

    KeyStatus(String keyStatus) {
        this.keyStatus = keyStatus;
    }

    @Override
    public String toString() {
        return keyStatus;
    }
}
