package ua.com.studiovision.euromaidan.search_fragments;

import ua.com.studiovision.euromaidan.UserActionsCallbacks;

public interface SearchActivityCallbacks extends UserActionsCallbacks {
    void loadMoreUsers();
    void loadMoreAudio();
    void playAudio();
}
