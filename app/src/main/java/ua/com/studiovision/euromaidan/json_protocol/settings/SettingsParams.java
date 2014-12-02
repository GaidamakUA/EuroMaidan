package ua.com.studiovision.euromaidan.json_protocol.settings;

import android.os.Parcel;
import android.os.Parcelable;

import ua.com.studiovision.euromaidan.json_protocol.education_places.SendEducationPlaceProtocol;

public class SettingsParams implements Parcelable {
    public String name;
    public String surname;
    public Genders gender;
    public RelationshipStatus family_status;
    public Long family_person_id;
    public String family_person_name;
    public String family_person_surname;
    public String native_city;
    public String dob; // date of birth
    public String avatar;
    public SendEducationPlaceProtocol.EducationPlace[] schools;
    public SendEducationPlaceProtocol.EducationPlace[] colleges;

    public SettingsParams(String name, String surname, Genders gender, RelationshipStatus family_status, Long family_person_id, String family_person_name, String family_person_surname, String native_city, String dob, String avatar, SendEducationPlaceProtocol.EducationPlace[] schools, SendEducationPlaceProtocol.EducationPlace[] colleges) {
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.family_status = family_status;
        this.family_person_id = family_person_id;
        this.family_person_name = family_person_name;
        this.family_person_surname = family_person_surname;
        this.native_city = native_city;
        this.dob = dob;
        this.avatar = avatar;
        this.schools = schools;
        this.colleges = colleges;
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
        dest.writeString(this.family_person_name);
        dest.writeString(this.family_person_surname);
        dest.writeString(this.native_city);
        dest.writeString(this.dob);
        dest.writeString(this.avatar);
//        dest.writeParcelable(this.schools, flags);
//        dest.writeParcelable(this.colleges, flags);
    }

    private SettingsParams(Parcel in) {
        this.name = in.readString();
        this.surname = in.readString();
        int tmpGender = in.readInt();
        this.gender = tmpGender == -1 ? null : Genders.values()[tmpGender];
        int tmpFamily_status = in.readInt();
        this.family_status = tmpFamily_status == -1 ? null : RelationshipStatus.values()[tmpFamily_status];
        this.family_person_id = (Long) in.readValue(Long.class.getClassLoader());
        this.family_person_name = in.readString();
        this.family_person_surname = in.readString();
        this.native_city = in.readString();
        this.dob = in.readString();
        this.avatar = in.readString();
//        this.schools = in.readParcelable(SendEducationPlaceProtocol.EducationPlace[].class.getClassLoader());
//        this.colleges = in.readParcelable(SendEducationPlaceProtocol.EducationPlace[].class.getClassLoader());
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