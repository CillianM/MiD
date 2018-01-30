package ie.mid.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Cillian on 30/01/2018.
 */

public class Field implements Parcelable {
    private String name;
    private String type;

    public Field() {

    }

    public Field(String name, String type) {
        this.name = name;
        this.type = type;
    }

    protected Field(Parcel in) {
        name = in.readString();
        type = in.readString();
    }

    public static final Creator<Field> CREATOR = new Creator<Field>() {
        @Override
        public Field createFromParcel(Parcel in) {
            return new Field(in);
        }

        @Override
        public Field[] newArray(int size) {
            return new Field[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(type);
    }
}
