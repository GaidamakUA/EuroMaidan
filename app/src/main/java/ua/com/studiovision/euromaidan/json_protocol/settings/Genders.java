package ua.com.studiovision.euromaidan.json_protocol.settings;

import com.google.gson.annotations.SerializedName;

public enum Genders {
    @SerializedName("0")
    UNKNOWN,
    @SerializedName("1")
    MALE,
    @SerializedName("2")
    FEMALE
}
