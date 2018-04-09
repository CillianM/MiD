package ie.mid.identityengine.model;

public class IdentifyingParty {

    private String $class;
    private String partyId;

    public IdentifyingParty() {
    }

    public String get$class() {
        return $class;
    }

    public void set$class(String $class) {
        this.$class = $class;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("IdentifyingParty{");
        sb.append("$class='").append($class).append('\'');
        sb.append(", partyId='").append(partyId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
