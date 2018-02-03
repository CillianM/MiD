package ie.mid.identityengine.enums;

public enum EntityStatus {
    ACTIVE("ACTIVE"),
    DELETED("DELETED");

    private String status;

    EntityStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
