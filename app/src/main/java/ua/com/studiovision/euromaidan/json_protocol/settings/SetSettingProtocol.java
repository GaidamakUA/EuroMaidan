package ua.com.studiovision.euromaidan.json_protocol.settings;

import ua.com.studiovision.euromaidan.json_protocol.AbstractRequest;
import ua.com.studiovision.euromaidan.json_protocol.AbstractResponse;

public class SetSettingProtocol {
    private SetSettingProtocol() {
    }

    public static class SetSettingsRequest implements AbstractRequest<SetSettingsRequest> {
        public String key = "user_get_settings";
        public String content = "profile"; // profile,avatar,site,educational
        public String token;
        public SettingsParams settingsParams;

        public SetSettingsRequest(String token, SettingsParams settingsParams) {
            this.token = token;
            this.settingsParams = settingsParams;
        }
    }

    public static class SetSettingsResponse implements AbstractResponse<SetSettingsResponse> {
        public String status;
        public String message;
    }
}
