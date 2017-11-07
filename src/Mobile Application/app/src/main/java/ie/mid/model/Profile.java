package ie.mid.model;

/**
 * Created by Cillian on 27/10/2017.
 */

public class Profile {
    private String id;
    private String name;
    private String hash;
    private String salt;

    public Profile(String id, String name, String hash, String salt) {
        this.id = id;
        this.name = name;
        this.hash = hash;
        this.salt = salt;
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
}
