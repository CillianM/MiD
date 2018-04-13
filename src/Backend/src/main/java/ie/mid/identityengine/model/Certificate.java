package ie.mid.identityengine.model;

public class Certificate {
    private String $class;
    private String certId;
    private String submissionHash;
    private String dateCreated;
    private String status;
    private String trustee;
    private String owner;

    public Certificate() {
    }

    public String get$class() {
        return $class;
    }

    public void set$class(String $class) {
        this.$class = $class;
    }

    public String getCertId() {
        return certId;
    }

    public void setCertId(String certId) {
        this.certId = certId;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getTrustee() {
        return trustee;
    }

    public void setTrustee(String trustee) {
        this.trustee = trustee;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubmissionHash() {
        return submissionHash;
    }

    public void setSubmissionHash(String submissionHash) {
        this.submissionHash = submissionHash;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Certificate{");
        sb.append("$class='").append($class).append('\'');
        sb.append(", certId='").append(certId).append('\'');
        sb.append(", submissionHash='").append(submissionHash).append('\'');
        sb.append(", dateCreated='").append(dateCreated).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append(", trustee='").append(trustee).append('\'');
        sb.append(", owner='").append(owner).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
