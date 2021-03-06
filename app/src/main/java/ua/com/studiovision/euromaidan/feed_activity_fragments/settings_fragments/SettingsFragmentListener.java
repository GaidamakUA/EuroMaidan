package ua.com.studiovision.euromaidan.feed_activity_fragments.settings_fragments;

import ua.com.studiovision.euromaidan.network.json_protocol.settings.SettingsParams;

public interface SettingsFragmentListener {
    void sendProfileDataToServer(SettingsParams settingsParams);

    // synchronized
    void requestProfileDataFromServer();
}
