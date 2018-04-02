package ie.mid.identityengine.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "identity_type")
public class IdentityType {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;
    @Column(name = "party_id", nullable = false)
    private String partyId;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "coverImg", nullable = false)
    private String coverImg;
    @Column(name = "iconImg", nullable = false)
    private String iconImg;
    @Column(name = "fields", nullable = false)
    private String fields;
    @Column(name = "version_number", nullable = false)
    private int versionNumber;
    @Column(name = "status", nullable = false)
    private String status;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public IdentityType() {
    }

    public IdentityType(String partyId, String fields) {
        this.partyId = partyId;
        this.fields = fields;
        this.versionNumber = 1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getFields() {
        return fields;
    }

    public String [] getFieldsArray(){
        return fields.split(",");
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public int getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getIconImg() {
        return iconImg;
    }

    public void setIconImg(String iconImg) {
        this.iconImg = iconImg;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("IdentityType{");
        sb.append("id='").append(id).append('\'');
        sb.append(", partyId='").append(partyId).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", coverImg='").append(coverImg).append('\'');
        sb.append(", iconImg='").append(iconImg).append('\'');
        sb.append(", fields='").append(fields).append('\'');
        sb.append(", versionNumber=").append(versionNumber);
        sb.append(", status='").append(status).append('\'');
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append('}');
        return sb.toString();
    }
}
