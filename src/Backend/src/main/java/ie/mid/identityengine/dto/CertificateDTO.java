package ie.mid.identityengine.dto;

public class CertificateDTO {

    private String id;
    private String ownerName;
    private String ownedBy;
    private String creatorName;
    private String createdBy;
    private String createdAt;
    private String status;

    public CertificateDTO() {
        //Implemented for mapping
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(String ownedBy) {
        this.ownedBy = ownedBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CertificateDTO{");
        sb.append("id='").append(id).append('\'');
        sb.append(", ownerName='").append(ownerName).append('\'');
        sb.append(", ownedBy='").append(ownedBy).append('\'');
        sb.append(", creatorName='").append(creatorName).append('\'');
        sb.append(", createdBy='").append(createdBy).append('\'');
        sb.append(", createdAt='").append(createdAt).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
