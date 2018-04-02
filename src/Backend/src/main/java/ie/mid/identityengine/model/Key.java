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
    @Column(name = "user_id", nullable = false)
    private String userId;
    @Column(name = "public_key", nullable = false, length = 450)
    private String publicKey;
    @Column(name = "token", nullable = false)
    private String token;
    @Column(name = "status", nullable = false)
    private String status;
    @Temporal(TemporalType.TIMESTAMP)
    private Date validUntil;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public Key() {
    }

    public Key(String owner, String publicKey) {
        this.userId = owner;
        this.publicKey = publicKey;
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

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
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

    public Date getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Date validUntil) {
        this.validUntil = validUntil;
    }

    public void setToken(String token){this.token = token;}

    public String getToken(){return token;}

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Key{");
        sb.append("id='").append(id).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", publicKey='").append(publicKey).append('\'');
        sb.append(", token='").append(token).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append(", validUntil=").append(validUntil);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append('}');
        return sb.toString();
    }
}
