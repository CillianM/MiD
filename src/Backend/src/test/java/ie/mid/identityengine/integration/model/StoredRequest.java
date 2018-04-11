package ie.mid.identityengine.integration.model;

public class StoredRequest {
    private String id;
    private String identityTypeId;
    private String senderId;
    private String receiverId;
    private String fieldsRequested;
    private String certificateId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdentityTypeId() {
        return identityTypeId;
    }

    public void setIdentityTypeId(String identityTypeId) {
        this.identityTypeId = identityTypeId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getFieldsRequested() {
        return fieldsRequested;
    }

    public void setFieldsRequested(String fieldsRequested) {
        this.fieldsRequested = fieldsRequested;
    }

    public String getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(String certificateId) {
        this.certificateId = certificateId;
    }
}
