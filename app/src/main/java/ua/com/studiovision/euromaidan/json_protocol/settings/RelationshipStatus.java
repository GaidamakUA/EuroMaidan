package ua.com.studiovision.euromaidan.json_protocol.settings;

import com.google.gson.annotations.SerializedName;

public enum RelationshipStatus {
    @SerializedName("0")
    NONE,
    @SerializedName("1")
    SINGLE,
    @SerializedName("2")
    DATING,
    @SerializedName("3")
    ENGAGED,
    @SerializedName("4")
    MARRIED,
    @SerializedName("5")
    IN_LOVE,
    @SerializedName("6")
    COMPLICATED,
    @SerializedName("7")
    IN_ACTIVE_SEARCH
}