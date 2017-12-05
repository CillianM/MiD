package ie.mid.identityengine.model;

import ie.mid.identityengine.enums.KeyStatus;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_keys")
public class Key {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "public_key")
    private String key;
    @Column(name = "key_class")
    private String keyClass;
    @Column(name = "status")
    private String status;
    @Temporal(TemporalType.DATE)
    private Date validUntil;
    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    private Date createdAt;
    @UpdateTimestamp
    @Temporal(TemporalType.DATE)
    private Date updatedAt;

    public Key() {
    }

    public Key(String owner, String key) {
        this.userId = owner;
        this.status = KeyStatus.ACTIVE.toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getKeyClass() {
        return keyClass;
    }

    public void setKeyClass(String keyClass) {
        this.keyClass = keyClass;
    }

    public Date getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Date validUntil) {
        this.validUntil = validUntil;
    }
}
