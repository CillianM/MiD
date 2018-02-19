package ie.mid.identityengine.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "request")
public class Request {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;
    @Column(name = "sender", nullable = false)
    private String senderId;
    @Column(name = "recipient", nullable = false)
    private String recipientId;
    @Column(name = "type_id")
    private String indentityTypeId;
    @Column(name = "type_fields", nullable = false)
    private String identityTypeFields;
    @Column(name = "user_response")
    private String userResponse;
    @Column(name = "status", nullable = false)
    private String status;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public Request() {
        //Implemented for mapping
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
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

    public String getIndentityTypeId() {
        return indentityTypeId;
    }

    public void setIndentityTypeId(String indentityTypeId) {
        this.indentityTypeId = indentityTypeId;
    }

    public String getIdentityTypeFields() {
        return identityTypeFields;
    }

    public void setIdentityTypeFields(String identityTypeFields) {
        this.identityTypeFields = identityTypeFields;
    }

    public String getUserResponse() {
        return userResponse;
    }

    public void setUserResponse(String userResponse) {
        this.userResponse = userResponse;
    }
}
