package ie.mid.identityengine.dto;

import ie.mid.identityengine.model.Field;

import java.util.List;

public class IdentityTypeDTO {

    private String id;
    private String partyId;
    private String name;
    private String iconImg;
    private String coverImg;
    private List<Field> fields;
    private int versionNumber;
    private String status;

    public IdentityTypeDTO() {
        //Implemented for mapping
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getIconImg() {
        return iconImg;
    }

    public void setIconImg(String iconImg) {
        this.iconImg = iconImg;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("IdentityTypeDTO{");
        sb.append("id='").append(id).append('\'');
        sb.append(", partyId='").append(partyId).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", iconImg='").append(iconImg).append('\'');
        sb.append(", coverImg='").append(coverImg).append('\'');
        sb.append(", fields=").append(fields);
        sb.append(", versionNumber=").append(versionNumber);
        sb.append(", status='").append(status).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
