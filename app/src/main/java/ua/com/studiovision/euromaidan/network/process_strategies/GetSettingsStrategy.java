package ua.com.studiovision.euromaidan.network.process_strategies;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;

import ua.com.studiovision.euromaidan.AppProtocol;
import ua.com.studiovision.euromaidan.network.json_protocol.settings.GetSettingProtocol;
import ua.com.studiovision.euromaidan.network.json_protocol.settings.SetSettingProtocol;

public class GetSettingsStrategy extends AbstractProcessResponseStrategy
        <GetSettingProtocol.GetSettingsRequest, GetSettingProtocol.GetSettingsResponse> {

    private static final String TAG = "SendSettingsStrategy";
    private StrategyCallbacks callbacks;

    public GetSettingsStrategy(Context context, Message msg, StrategyCallbacks callbacks) {
        super(context, GetSettingProtocol.GetSettingsResponse.class);
        Bundle bundle = msg.getData();
        String token = bundle.getString(SetSettingProtocol.TOKEN);
        request = new GetSettingProtocol.GetSettingsRequest(token);
        this.callbacks = callbacks;
    }

    @Override
    protected void onResponse(GetSettingProtocol.GetSettingsResponse response) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(GetSettingProtocol.RESPONSE_OBJECT, response.content);
        Message msg = Message.obtain();
        msg.what = AppProtocol.RESPONSE_USER_SETTINGS;
        msg.setData(bundle);
        callbacks.sendMessageToActivity(msg);
    }
}
