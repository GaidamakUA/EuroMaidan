package ua.com.studiovision.euromaidan.json_protocol.settings;

import android.os.Parcel;
import android.os.Parcelable;

public class GetSettingsResponseContent implements Parcelable {
    public String user_name;
    public String user_surname;
    public String user_gender;
    public String family_status;
    public String family_person_id;
    public String family_person_name;
    public String family_person_surname;
    public String native_city;
    public String dob;
    public String avatar;
    public String[] schools;
    public String[] colleges;

    public GetSettingsResponseContent(String user_name, String user_surname, String user_gender,
                                      String family_status, String family_person_id,
                                      String family_person_name, String family_person_surname,
                                      String native_city, String dob, String avatar, String[] schools,
                                      String[] colleges) {
        this.user_name = user_name;
        this.user_surname = user_surname;
        this.user_gender = user_gender;
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
        dest.writeString(this.user_name);
        dest.writeString(this.user_surname);
        dest.writeString(this.user_gender);
        dest.writeString(this.family_status);
        dest.writeString(this.family_person_id);
        dest.writeString(this.family_person_name);
        dest.writeString(this.family_person_surname);
        dest.writeString(this.native_city);
        dest.writeString(this.dob);
        dest.writeString(this.avatar);
        dest.writeStringArray(this.schools);
        dest.writeStringArray(this.colleges);
    }

    private GetSettingsResponseContent(Parcel in) {
        this.user_name = in.readString();
        this.user_surname = in.readString();
        this.user_gender = in.readString();
        this.family_status = in.readString();
        this.family_person_id = in.readString();
        this.family_person_name = in.readString();
        this.family_person_surname = in.readString();
        this.native_city = in.readString();
        this.dob = in.readString();
        this.avatar = in.readString();
        this.schools = in.createStringArray();
        this.colleges = in.createStringArray();
    }

    public static final Parcelable.Creator<GetSettingsResponseContent> CREATOR =
            new Parcelable.Creator<GetSettingsResponseContent>() {
                public GetSettingsResponseContent createFromParcel(Parcel source) {
                    return new GetSettingsResponseContent(source);
                }

                public GetSettingsResponseContent[] newArray(int size) {
                    return new GetSettingsResponseContent[size];
                }
            };
}