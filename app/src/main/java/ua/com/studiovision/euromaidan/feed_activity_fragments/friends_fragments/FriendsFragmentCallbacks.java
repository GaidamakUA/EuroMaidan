package ua.com.studiovision.euromaidan.feed_activity_fragments.friends_fragments;

import android.support.v4.app.LoaderManager;

import ua.com.studiovision.euromaidan.network.json_protocol.friends.FriendsContent;

public interface FriendsFragmentCallbacks {
    void loadFriends(long userId, FriendsContent content);
    void toFriends(long userId);
    void toSubscribers(long userId);
}
