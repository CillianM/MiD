package ie.mid.identityengine.dto;

public class InformationRequestDTO {

    private String senderId;
    private String recipientId;
    private String indentityTypeId;
    private String identityTypeFields;
    private String identityTypeValues;
    private String certificateId;
    private String status;

    public InformationRequestDTO() {
        //Implemented for mapping
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(String certificateId) {
        this.certificateId = certificateId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("InformationRequestDTO{");
        sb.append("senderId='").append(senderId).append('\'');
        sb.append(", recipientId='").append(recipientId).append('\'');
        sb.append(", indentityTypeId='").append(indentityTypeId).append('\'');
        sb.append(", identityTypeFields='").append(identityTypeFields).append('\'');
        sb.append(", identityTypeValues='").append(identityTypeValues).append('\'');
        sb.append(", certificateId='").append(certificateId).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
