package ua.com.studiovision.euromaidan.network.json_protocol.search;

import com.google.gson.annotations.SerializedName;

public enum SearchCategory {
    @SerializedName("all")
    ALL,
    @SerializedName("people")
    PEOPLE,
    @SerializedName("publics")
    PUBLICS,
    @SerializedName("videos")
    VIDEOS,
    @SerializedName("audios")
    AUDIOS
}
