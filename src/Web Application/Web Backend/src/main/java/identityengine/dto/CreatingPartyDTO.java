package identityengine.dto;

public class CreatingPartyDTO extends PartyDTO {

    private String privateKey;

    public CreatingPartyDTO() {
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}
