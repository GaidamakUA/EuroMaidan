package ua.com.studiovision.euromaidan.network.json_protocol.settings;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

public class GetSettingsResponseContent implements Parcelable {
    public String user_name;
    public String user_surname;
    public Gender user_gender;
    public RelationshipStatus family_status;
    public String family_person_id;
    public String family_person_name;
    public String family_person_surname;
    public String native_city;
    public String date_of_birth;
    public String avatar;
    public String[] schools;
    public String[] colleges;

    public GetSettingsResponseContent(String user_name, String user_surname, Gender user_gender,
                                      RelationshipStatus family_status, String family_person_id,
                                      String family_person_name, String family_person_surname,
                                      String native_city, String date_of_birth, String avatar, String[] schools,
                                      String[] colleges) {
        this.user_name = user_name;
        this.user_surname = user_surname;
        this.user_gender = user_gender;
        this.family_status = family_status;
        this.family_person_id = family_person_id;
        this.family_person_name = family_person_name;
        this.family_person_surname = family_person_surname;
        this.native_city = native_city;
        this.date_of_birth = date_of_birth;
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
        dest.writeValue(this.user_gender);
        dest.writeValue(this.family_status);
        dest.writeString(this.family_person_id);
        dest.writeString(this.family_person_name);
        dest.writeString(this.family_person_surname);
        dest.writeString(this.native_city);
        dest.writeString(this.date_of_birth);
        dest.writeString(this.avatar);
        dest.writeStringArray(this.schools);
        dest.writeStringArray(this.colleges);
    }

    private GetSettingsResponseContent(Parcel in) {
        this.user_name = in.readString();
        this.user_surname = in.readString();
        this.user_gender = (Gender) in.readValue(Gender.class.getClassLoader());
        this.family_status = (RelationshipStatus) in.readValue(RelationshipStatus.class.getClassLoader());
        this.family_person_id = in.readString();
        this.family_person_name = in.readString();
        this.family_person_surname = in.readString();
        this.native_city = in.readString();
        this.date_of_birth = in.readString();
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

    @Override
    public String toString() {
        return "GetSettingsResponseContent{" +
                "user_name='" + user_name + '\'' +
                ", user_surname='" + user_surname + '\'' +
                ", user_gender='" + user_gender + '\'' +
                ", family_status='" + family_status + '\'' +
                ", family_person_id='" + family_person_id + '\'' +
                ", family_person_name='" + family_person_name + '\'' +
                ", family_person_surname='" + family_person_surname + '\'' +
                ", native_city='" + native_city + '\'' +
                ", date_of_birth='" + date_of_birth + '\'' +
                ", avatar='" + avatar + '\'' +
                ", schools=" + Arrays.toString(schools) +
                ", colleges=" + Arrays.toString(colleges) +
                '}';
    }

}