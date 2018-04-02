package ie.mid.identityengine.dto;

public class RequestDTO {

    private String id;
    private String senderName;
    private String senderId;
    private String recipientName;
    private String recipientId;
    private String status;
    private String certificateId;
    private String indentityTypeId;
    private String identityTypeFields;
    private String identityTypeValues;
    private String createdAt;

    public RequestDTO() {
        //Implemented for mapping
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIndentityTypeId() {
        return indentityTypeId;
    }

    public void setIndentityTypeId(String indentityTypeId) {
        this.indentityTypeId = indentityTypeId;
    }

    public String getIdentityTypeFields() {
        return identityTypeFields;
    }

    public void setIdentityTypeFields(String identityTypeFields) {
        this.identityTypeFields = identityTypeFields;
    }

    public String getIdentityTypeValues() {
        return identityTypeValues;
    }

    public void setIdentityTypeValues(String identityTypeValues) {
        this.identityTypeValues = identityTypeValues;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(String certificateId) {
        this.certificateId = certificateId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RequestDTO{");
        sb.append("id='").append(id).append('\'');
        sb.append(", senderName='").append(senderName).append('\'');
        sb.append(", senderId='").append(senderId).append('\'');
        sb.append(", recipientName='").append(recipientName).append('\'');
        sb.append(", recipientId='").append(recipientId).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append(", certificateId='").append(certificateId).append('\'');
        sb.append(", indentityTypeId='").append(indentityTypeId).append('\'');
        sb.append(", identityTypeFields='").append(identityTypeFields).append('\'');
        sb.append(", identityTypeValues='").append(identityTypeValues).append('\'');
        sb.append(", createdAt='").append(createdAt).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
