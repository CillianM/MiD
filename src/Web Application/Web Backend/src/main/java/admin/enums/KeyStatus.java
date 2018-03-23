package admin.enums;

public enum KeyStatus {
    ACTIVE("ACTIVE"),
    UPGRADED("UPGRADED"),
    COMPROMISED("COMPROMISED"),
    EXPIRED("EXPIRED"),
    DELETED("DELETED");

    private String status;

    KeyStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
