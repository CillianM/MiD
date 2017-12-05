package ie.mid.identityengine.enums;

public enum RequestStatus {
    SUBMITTED("SUBMITTED"),
    PENDING("PENDING"),
    ACCEPTED("ACCEPTED"),
    DROPPED("DROPPED"),
    REJECTED("REJECTED");

    private String status;

    RequestStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
