package ua.com.studiovision.euromaidan.network.json_protocol.settings;

import android.os.Parcel;
import android.os.Parcelable;

public class SettingsParams implements Parcelable {
    public static final String TAG = SettingsParams.class.getName();
    public String name;
    public String surname;
    public Genders gender;
    public RelationshipStatus family_status;
    public Long family_person_id;
    public String dob; // date of birth
    public String native_city;

    public SettingsParams(String name, String surname, Genders gender,
                          RelationshipStatus family_status, Long family_person_id,
                          String native_city, String dob) {
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.family_status = family_status;
        this.family_person_id = family_person_id;
        this.native_city = native_city;
        this.dob = dob;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.surname);
        dest.writeInt(this.gender == null ? -1 : this.gender.ordinal());
        dest.writeInt(this.family_status == null ? -1 : this.family_status.ordinal());
        dest.writeValue(this.family_person_id);
        dest.writeString(this.native_city);
        dest.writeString(this.dob);
    }

    private SettingsParams(Parcel in) {
        this.name = in.readString();
        this.surname = in.readString();
        int tmpGender = in.readInt();
        this.gender = tmpGender == -1 ? null : Genders.values()[tmpGender];
        int tmpFamily_status = in.readInt();
        this.family_status = tmpFamily_status == -1 ? null : RelationshipStatus.values()[tmpFamily_status];
        this.family_person_id = (Long) in.readValue(Long.class.getClassLoader());
        this.native_city = in.readString();
        this.dob = in.readString();
    }

    public static final Parcelable.Creator<SettingsParams> CREATOR = new Parcelable.Creator<SettingsParams>() {
        public SettingsParams createFromParcel(Parcel source) {
            return new SettingsParams(source);
        }

        public SettingsParams[] newArray(int size) {
            return new SettingsParams[size];
        }
    };
}