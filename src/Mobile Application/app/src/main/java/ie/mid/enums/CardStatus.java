package ie.mid.enums;

/**
 * Created by cillian on 01/11/2017.
 */

public enum CardStatus {
    ACCEPTED,
    SUBMITTED,
    REJECTED,
    PENDING,
    DELETED,
    RESCINDED,
    UPDATED,
    NOT_VERIFIED;

    public static String enumToString(String enumString) {
        switch (enumString) {
            case "ACCEPTED":
                return "Accepted";
            case "REJECTED":
                return "Rejected";
            case "DELETED":
                return "Identity Type Upgraded Or Deleted";
            case "UPDATED":
                return "Updated";
            case "SUBMITTED":
                return "Submitted";
            case "RESCINDED":
                return "Rescinded";
            case "PENDING":
                return "Pending";
            case "NOT_VERIFIED":
                return "Not Verified, Click To Verify";
            default:
                return null;
        }
    }
}
