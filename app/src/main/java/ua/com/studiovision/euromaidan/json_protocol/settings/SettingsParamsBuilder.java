package ua.com.studiovision.euromaidan.json_protocol.settings;

import ua.com.studiovision.euromaidan.json_protocol.education_places.EducationPlace;

public class SettingsParamsBuilder {
    private String name;
    private String surname;
    private Genders gender;
    private RelationshipStatus family_status;
    private Long family_person_id;
    private String family_person_name;
    private String family_person_surname;
    private String native_city;
    private String dob;
    private String avatar;
    private EducationPlace[] schools;
    private EducationPlace[] colleges;

    public SettingsParamsBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public SettingsParamsBuilder setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public SettingsParamsBuilder setGender(Genders gender) {
        this.gender = gender;
        return this;
    }

    public SettingsParamsBuilder setFamily_status(RelationshipStatus family_status) {
        this.family_status = family_status;
        return this;
    }

    public SettingsParamsBuilder setFamily_person_id(Long family_person_id) {
        this.family_person_id = family_person_id;
        return this;
    }

    public SettingsParamsBuilder setFamily_person_name(String family_person_name) {
        this.family_person_name = family_person_name;
        return this;
    }

    public SettingsParamsBuilder setFamily_person_surname(String family_person_surname) {
        this.family_person_surname = family_person_surname;
        return this;
    }

    public SettingsParamsBuilder setNative_city(String native_city) {
        this.native_city = native_city;
        return this;
    }

    public SettingsParamsBuilder setDob(String dob) {
        this.dob = dob;
        return this;
    }

    public SettingsParamsBuilder setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public SettingsParamsBuilder setSchools(EducationPlace[] schools) {
        this.schools = schools;
        return this;
    }

    public SettingsParamsBuilder setColleges(EducationPlace[] colleges) {
        this.colleges = colleges;
        return this;
    }

    public SettingsParams createRequestParams() {
        return new SettingsParams(name, surname, gender, family_status, family_person_id, family_person_name, family_person_surname, native_city, dob, avatar, schools, colleges);
    }
}