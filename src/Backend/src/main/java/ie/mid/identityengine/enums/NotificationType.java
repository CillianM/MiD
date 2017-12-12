package ie.mid.identityengine.enums;

public enum NotificationType {
    REQUEST("REQUES"),
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
