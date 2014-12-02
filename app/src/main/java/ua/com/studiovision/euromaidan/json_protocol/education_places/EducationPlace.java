package ua.com.studiovision.euromaidan.json_protocol.education_places;

import android.os.Parcel;
import android.os.Parcelable;

public class EducationPlace implements Parcelable {
    public String country;
    public String city;
    public String name;

    public EducationPlace(String country, String city, String name) {
        this.country = country;
        this.city = city;
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.country);
        dest.writeString(this.city);
        dest.writeString(this.name);
    }

    private EducationPlace(Parcel in) {
        this.country = in.readString();
        this.city = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<EducationPlace> CREATOR = new Parcelable.Creator<EducationPlace>() {
        public EducationPlace createFromParcel(Parcel source) {
            return new EducationPlace(source);
        }

        public EducationPlace[] newArray(int size) {
            return new EducationPlace[size];
        }
    };
}
