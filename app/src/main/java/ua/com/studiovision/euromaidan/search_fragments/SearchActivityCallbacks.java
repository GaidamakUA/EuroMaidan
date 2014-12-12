package ua.com.studiovision.euromaidan.search_fragments;

public interface SearchActivityCallbacks {
    void loadMoreUserIds();
    void addFriend(long userId);
    void deleteFriend(long userId);
}
