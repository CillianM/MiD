package ie.mid.identityengine.dto;

public class TokenDTO {

    private String id;
    private String userId;
    private String token;
    private String keyStatus;

    public TokenDTO(){}

    public TokenDTO(String id, String userId, String token, String keyStatus) {
        this.id = id;
        this.userId = userId;
        this.token = token;
        this.keyStatus = keyStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getKeyStatus() {
        return keyStatus;
    }

    public void setKeyStatus(String keyStatus) {
        this.keyStatus = keyStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
