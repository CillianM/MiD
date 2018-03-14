package identityengine.enums;

public enum NotificationType {
    REQUEST("REQUEST"),
    CERTIFICATE_UPDATE("CERTIFICATE_UPDATE"),
    APPLICATION_UPDATE("APPLICATION_UPDATE");

    private String type;

    NotificationType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
