package ie.mid.identityengine.integration.model;

public class StoredSubmission {
    private String id;
    private String userId;
    private String partyId;
    private String submissionHash;
    private String data;
    private String dataKey;
    private String certId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCertId() {
        return certId;
    }

    public void setCertId(String certId) {
        this.certId = certId;
    }

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

    public String getSubmissionHash() {
        return submissionHash;
    }

    public void setSubmissionHash(String submissionHash) {
        this.submissionHash = submissionHash;
    }
}
