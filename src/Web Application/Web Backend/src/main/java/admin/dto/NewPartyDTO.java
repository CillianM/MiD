package admin.dto;

public class NewPartyDTO extends PartyDTO {

    private String partyToken;

    public String getPartyToken() {
        return partyToken;
    }

    public void setPartyToken(String partyToken) {
        this.partyToken = partyToken;
    }
}
