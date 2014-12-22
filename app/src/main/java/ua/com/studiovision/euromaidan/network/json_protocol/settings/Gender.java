package ua.com.studiovision.euromaidan.network.json_protocol.settings;

import com.google.gson.annotations.SerializedName;

public enum Gender {
    @SerializedName("0")
    UNKNOWN,
    @SerializedName("1")
    MALE,
    @SerializedName("2")
    FEMALE
}
