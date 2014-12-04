package ua.com.studiovision.euromaidan.json_protocol.settings;

public class SettingsParamsBuilder {
    private String name;
    private String surname;
    private Genders gender;
    private RelationshipStatus family_status;
    private Long family_person_id;
    private String native_city;
    private String dob;

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

    public SettingsParamsBuilder setRelationshipStatus(RelationshipStatus family_status) {
        this.family_status = family_status;
        return this;
    }

    public SettingsParamsBuilder setFamilyPersonId(Long family_person_id) {
        this.family_person_id = family_person_id;
        return this;
    }

    public SettingsParamsBuilder setNativeCity(String native_city) {
        this.native_city = native_city;
        return this;
    }

    public SettingsParamsBuilder setDob(String dob) {
        this.dob = dob;
        return this;
    }

    public SettingsParams createRequestParams() {
        return new SettingsParams(name, surname, gender, family_status, family_person_id, native_city, dob);
    }
}