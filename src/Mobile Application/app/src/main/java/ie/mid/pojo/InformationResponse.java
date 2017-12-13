package ie.mid.pojo;

public class InformationResponse {

    private String sender;
    private String recipient;
    private String information;
    private String certificateReference;

    public InformationResponse() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getCertificateReference() {
        return certificateReference;
    }

    public void setCertificateReference(String certificateReference) {
        this.certificateReference = certificateReference;
    }
}
