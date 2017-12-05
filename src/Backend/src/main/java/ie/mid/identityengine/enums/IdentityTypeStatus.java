package ie.mid.identityengine.enums;

public enum  IdentityTypeStatus {
    ACTIVE("ACTIVE"),
    UPGRADED("UPGRADED"),
    DELETED("DELETED");

    private String status;

    IdentityTypeStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
