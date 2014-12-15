package ua.com.studiovision.euromaidan.network.json_protocol.friends;

import com.google.gson.annotations.SerializedName;

public enum FriendsContent {
    @SerializedName("friends")
    FRIENDS,
    @SerializedName("followers")
    FOLLOWERS,
    @SerializedName("friends_requests")
    FRIENDS_REQUESTS
}
