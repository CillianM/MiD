package ie.mid.identityengine.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "submission")
public class Submission {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;
    @Column(name = "user_id", nullable = false)
    private String userId;
    @Column(name = "party_id", nullable = false)
    private String partyId;
    @Column(name = "certificate_id")
    private String certificateId;
    @Column(name = "submission_hash", nullable = false, length = 450)
    private String submissionHash;
    @Column(name = "data_key", nullable = false, length = 450)
    private String dataKey;
    @Column(name = "data", nullable = false)
    private String data;
    @Column(name = "status", nullable = false)
    private String status;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public Submission() {
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(String certificateId) {
        this.certificateId = certificateId;
    }

    public String getSubmissionHash() {
        return submissionHash;
    }

    public void setSubmissionHash(String submissionHash) {
        this.submissionHash = submissionHash;
    }

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Submission{");
        sb.append("id='").append(id).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", partyId='").append(partyId).append('\'');
        sb.append(", certificateId='").append(certificateId).append('\'');
        sb.append(", submissionHash='").append(submissionHash).append('\'');
        sb.append(", data='").append(data).append('\'');
        sb.append(", dataKey='").append(dataKey).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append('}');
        return sb.toString();
    }
}
