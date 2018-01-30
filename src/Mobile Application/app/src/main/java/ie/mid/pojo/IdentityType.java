package ie.mid.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import ie.mid.model.Field;

public class IdentityType implements Parcelable {

    private String id;
    private String partyId;
    private String name;
    private String iconImg;
    private String coverImg;
    private List<Field> fields;
    private int versionNumber;
    private String status;

    public IdentityType() {
    }

    protected IdentityType(Parcel in) {
        id = in.readString();
        partyId = in.readString();
        name = in.readString();
        iconImg = in.readString();
        coverImg = in.readString();
        fields = in.createTypedArrayList(Field.CREATOR);
        versionNumber = in.readInt();
        status = in.readString();
    }

    public static final Creator<IdentityType> CREATOR = new Creator<IdentityType>() {
        @Override
        public IdentityType createFromParcel(Parcel in) {
            return new IdentityType(in);
        }

        @Override
        public IdentityType[] newArray(int size) {
            return new IdentityType[size];
        }
    };

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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(partyId);
        parcel.writeString(name);
        parcel.writeString(iconImg);
        parcel.writeString(coverImg);
        parcel.writeTypedList(fields);
        parcel.writeInt(versionNumber);
        parcel.writeString(status);
    }
}
