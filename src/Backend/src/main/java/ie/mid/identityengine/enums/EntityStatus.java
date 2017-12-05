package ie.mid.identityengine.enums;

public enum EntityStatus {
    ACTIVE("ACTIVE"),
    DELETED("DELETED");

    private String entityStatus;

    EntityStatus(String entityStatus) {
        this.entityStatus = entityStatus;
    }

    @Override
    public String toString() {
        return entityStatus;
    }
}
