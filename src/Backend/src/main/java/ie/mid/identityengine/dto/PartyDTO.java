package ie.mid.identityengine.dto;

public class PartyDTO {

    private String id;
    private String name;
    private String status;
    private String keyId;
    private String publicKey;

    public PartyDTO() {
        //Implemented for mapping
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PartyDTO{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append(", keyId='").append(keyId).append('\'');
        sb.append(", publicKey='").append(publicKey).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
