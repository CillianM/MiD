package ie.mid.identityengine.dto;

public class UserDTO {

    private String id;
    private String nickname;
    private String fcmToken;
    private String status;
    private String keyId;
    private String publicKey;

    public UserDTO() {
        //Implemented for mapping
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("UserDTO{");
        sb.append("id='").append(id).append('\'');
        sb.append(", nickname='").append(nickname).append('\'');
        sb.append(", fcmToken='").append(fcmToken).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append(", keyId='").append(keyId).append('\'');
        sb.append(", publicKey='").append(publicKey).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
