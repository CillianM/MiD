package ie.mid.identityengine.dto;

public class InformationRequestDTO {

    private String sender;
    private String recipient;
    private String key;
    private String desiredInformation;

    public InformationRequestDTO() {
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDesiredInformation() {
        return desiredInformation;
    }

    public void setDesiredInformation(String desiredInformation) {
        this.desiredInformation = desiredInformation;
    }
}
