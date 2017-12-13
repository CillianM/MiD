package ie.mid.model;

/**
 * Created by Cillian on 27/10/2017.
 */

public class Profile {
    private String id;
    private String name;
    private String hash;
    private String salt;
    private String serverId;
    private String publicKey;
    private String privateKey;
    private String imageUrl;

    public Profile() {
    }

    public Profile(String id, String name, String hash, String salt) {
        this.id = id;
        this.name = name;
        this.hash = hash;
        this.salt = salt;
    }

    public Profile(String id, String name, String imageUrl, String hash, String salt, String serverId, String publicKey, String privateKey) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.hash = hash;
        this.salt = salt;
        this.serverId = serverId;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
