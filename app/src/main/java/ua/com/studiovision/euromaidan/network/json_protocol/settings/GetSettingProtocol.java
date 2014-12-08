package ua.com.studiovision.euromaidan.network.json_protocol.settings;

import ua.com.studiovision.euromaidan.network.json_protocol.AbstractRequest;
import ua.com.studiovision.euromaidan.network.json_protocol.AbstractResponse;

public class GetSettingProtocol {
    public static final String TOKEN = "token";
    public static final String RESPONSE_OBJECT = "response_object";

    private GetSettingProtocol() {
    }

    public static class GetSettingsRequest implements AbstractRequest<GetSettingsRequest> {
        public String key = "user_get_settings";
        public String token;

        public GetSettingsRequest(String token) {
            this.token = token;
        }
    }

    public static class GetSettingsResponse extends AbstractResponse<GetSettingsResponse> {
        public GetSettingsResponseContent content;

        public GetSettingsResponse(GetSettingsResponseContent content) {
            this.content = content;
        }
    }
}
