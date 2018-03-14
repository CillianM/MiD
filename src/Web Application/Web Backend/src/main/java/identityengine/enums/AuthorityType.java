package identityengine.enums;

public enum AuthorityType {
    USER("USER"),
    ADMIN("ADMIN"),
    PARTY("PARTY");

    private String authType;

    AuthorityType(String authType) {
        this.authType = authType;
    }

    @Override
    public String toString() {
        return authType;
    }
}
