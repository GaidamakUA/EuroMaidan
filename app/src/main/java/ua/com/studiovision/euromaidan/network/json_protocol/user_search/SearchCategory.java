package ua.com.studiovision.euromaidan.network.json_protocol.user_search;

import com.google.gson.annotations.SerializedName;

public enum SearchCategory {
    @SerializedName("all")
    ALL,
    @SerializedName("people")
    PEOPLE,
    @SerializedName("publics")
    PUBLICS,
    @SerializedName("audios")
    AUDIOS,
    @SerializedName("videos")
    VIDEOS
}
