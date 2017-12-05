package ie.mid.identityengine.model;

import ie.mid.identityengine.enums.RequestStatus;
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
    @Column(name = "user_id")
    private String userId;
    @Column(name = "party_id")
    private String partyId;
    @Column(name = "data")
    private String data;
    @Column(name = "status")
    private String status;
    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    private Date createdAt;
    @UpdateTimestamp
    @Temporal(TemporalType.DATE)
    private Date updatedAt;

    public Submission() {
    }

    public Submission(String userId, String data) {
        this.userId = userId;
        this.data = data;
        this.status = RequestStatus.SUBMITTED.toString();
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
}
