package ie.mid.identityengine.dto;

public class SubmissionDTO {

    private String id;
    private String userName;
    private String userId;
    private String partyName;
    private String partyId;
    private String certId;
    private String data;
    private String status;
    private String date;

    public SubmissionDTO() {
        //Implemented for mapping
    }

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getCertId() {
        return certId;
    }

    public void setCertId(String certId) {
        this.certId = certId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SubmissionDTO{");
        sb.append("id='").append(id).append('\'');
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", partyName='").append(partyName).append('\'');
        sb.append(", partyId='").append(partyId).append('\'');
        sb.append(", certId='").append(certId).append('\'');
        sb.append(", data='").append(data).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append(", date='").append(date).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
