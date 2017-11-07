package ie.mid.model;

/**
 * Created by Cillian on 27/10/2017.
 */

public class AvailableCard {

    private String id;
    private String title;
    private String coverImgUrl;
    private String iconImgUrl;
    private String versionNumber;

    public AvailableCard(String title, String iconImgUrl) {
        this.title = title;
        this.iconImgUrl = iconImgUrl;
    }

    public AvailableCard(String id, String title, String iconImgUrl, String coverImgUrl,String versionNumber) {
        this.id = id;
        this.title = title;
        this.iconImgUrl = iconImgUrl;
        this.versionNumber = versionNumber;
        this.coverImgUrl = coverImgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIconImgUrl() {
        return iconImgUrl;
    }

    public void setIconImgUrl(String iconImgUrl) {
        this.iconImgUrl = iconImgUrl;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getCoverImgUrl() {
        return coverImgUrl;
    }

    public void setCoverImgUrl(String coverImgUrl) {
        this.coverImgUrl = coverImgUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
