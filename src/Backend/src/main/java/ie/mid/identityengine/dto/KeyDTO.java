package ie.mid.identityengine.dto;

public class KeyDTO {

    private String id;
    private String userId;
    private String publicKey;
    private String keyStatus;

    public KeyDTO() {
    }

    public KeyDTO(String id, String userId, String publicKey, String keyStatus) {
        this.id = id;
        this.userId = userId;
        this.publicKey = publicKey;
        this.keyStatus = keyStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
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
