package ie.mid.identityengine.dto;

public class UserResponseDTO {

    private String sender;
    private String recipient;
    private String desiredInformation;

    public UserResponseDTO() {
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

    public String getDesiredInformation() {
        return desiredInformation;
    }

    public void setDesiredInformation(String desiredInformation) {
        this.desiredInformation = desiredInformation;
    }
}
