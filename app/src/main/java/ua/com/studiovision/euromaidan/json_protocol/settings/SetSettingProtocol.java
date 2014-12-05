package ua.com.studiovision.euromaidan.json_protocol.settings;

import ua.com.studiovision.euromaidan.json_protocol.AbstractRequest;
import ua.com.studiovision.euromaidan.json_protocol.AbstractResponse;

public class SetSettingProtocol {
    public static final String TOKEN = "token";
    public static final String SETTINGS_PARAMS = "settings_params";

    private SetSettingProtocol() {
    }

    public static class SetSettingsRequest implements AbstractRequest<SetSettingsRequest> {
        public String key = "user_change_settings";
        public String content = "profile"; // profile,avatar,site,educational
        public String token;
        public SettingsParams params;

        public SetSettingsRequest(String token, SettingsParams settingsParams) {
            this.token = token;
            this.params = settingsParams;
        }
    }

    public static class SetSettingsResponse extends AbstractResponse<SetSettingsResponse> {
    }
}
