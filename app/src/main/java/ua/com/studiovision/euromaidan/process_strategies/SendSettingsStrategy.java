package ua.com.studiovision.euromaidan.process_strategies;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;

import ua.com.studiovision.euromaidan.json_protocol.settings.SetSettingProtocol;
import ua.com.studiovision.euromaidan.json_protocol.settings.SettingsParams;

public class SendSettingsStrategy extends AbstractProcessResponseStrategy
        <SetSettingProtocol.SetSettingsRequest, SetSettingProtocol.SetSettingsResponse> {

    private static final String TAG = "SendSettingsStrategy";

    public SendSettingsStrategy(Context context, Message msg) {
        super(context, SetSettingProtocol.SetSettingsResponse.class);
        Bundle bundle = msg.getData();
        String token = bundle.getString(SetSettingProtocol.TOKEN);
        SettingsParams settingsParams =
                bundle.getParcelable(SetSettingProtocol.SETTINGS_PARAMS);
        request = new SetSettingProtocol.SetSettingsRequest(token, settingsParams);
    }
}
