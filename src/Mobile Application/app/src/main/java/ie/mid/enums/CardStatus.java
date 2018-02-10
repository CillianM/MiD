package ie.mid.enums;

/**
 * Created by cillian on 01/11/2017.
 */

public enum CardStatus {
    ACCEPTED,
    REJECTED,
    PENDING,
    DELETED,
    UPDATED,
    NOT_VERIFIED;

    public static String enumToString(String enumString) {
        switch (enumString) {
            case "ACCEPTED":
                return "Accepted";
            case "REJECTED":
                return "Rejected";
            case "DELETED":
                return "Deleted";
            case "UPDATED":
                return "Updated";
            case "PENDING":
                return "Pending";
            case "NOT_VERIFIED":
                return "Not Verified";
            default:
                return null;
        }
    }
}
