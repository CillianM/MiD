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
    @Column(name = "party_id")
    private String partyId;
    @Column(name = "fields")
    private String fields;
    @Column(name = "version_number")
    private int versionNumber;
    @Column(name = "status")
    private String status;
    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    private Date createdAt;
    @UpdateTimestamp
    @Temporal(TemporalType.DATE)
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
}
