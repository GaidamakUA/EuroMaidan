package ua.com.studiovision.euromaidan.process_strategies;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import ua.com.studiovision.euromaidan.json_protocol.settings.SetSettingProtocol;
import ua.com.studiovision.euromaidan.json_protocol.settings.SettingsParams;

public class SendSettingsStrategy extends AbstractProcessResponseStrategy<SetSettingProtocol.SetSettingsResponse> {

    private static final String TAG = "SendSettingsStrategy";

    public SendSettingsStrategy(Message msg) {
        Bundle bundle = msg.getData();
        String token = bundle.getString(SetSettingProtocol.TOKEN);
        SettingsParams settingsParams =
                bundle.getParcelable(SetSettingProtocol.SETTINGS_PARAMS);
        request = new SetSettingProtocol.SetSettingsRequest(token, settingsParams);
        responseClass = SetSettingProtocol.SetSettingsResponse.class;
    }

    @Override
    public void processResponse(SetSettingProtocol.SetSettingsResponse response) {
        if (!"success".equals(response.status)) {
            throw new RuntimeException("Not success");
        } else {
            Log.v(TAG, "SendSettingsStrategy");
        }
    }
}
