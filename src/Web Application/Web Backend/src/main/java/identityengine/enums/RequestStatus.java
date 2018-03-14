package identityengine.enums;

public enum RequestStatus {
    SUBMITTED("SUBMITTED"),
    PENDING("PENDING"),
    ACCEPTED("ACCEPTED"),
    DROPPED("DROPPED"),
    REJECTED("REJECTED"),
    RESCINDED("RESCINDED");

    private String status;

    RequestStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
