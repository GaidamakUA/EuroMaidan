package ua.com.studiovision.euromaidan.network.process_strategies;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;

import ua.com.studiovision.euromaidan.network.json_protocol.friends.AddFriendProtocol;
import ua.com.studiovision.euromaidan.network.json_protocol.settings.SetSettingProtocol;
import ua.com.studiovision.euromaidan.network.json_protocol.settings.SettingsParams;

public class AddFriendStrategy extends AbstractProcessResponseStrategy
        <AddFriendProtocol.AddFriendRequest, AddFriendProtocol.AddFriendResponse> {

    private static final String TAG = "SendSettingsStrategy";

    public AddFriendStrategy(Context context, Message msg) {
        super(context, AddFriendProtocol.AddFriendResponse.class);
        Bundle bundle = msg.getData();
        String token = bundle.getString(SetSettingProtocol.TOKEN);
        SettingsParams settingsParams =
                bundle.getParcelable(SetSettingProtocol.SETTINGS_PARAMS);
        request = new SetSettingProtocol.SetSettingsRequest(token, settingsParams);
    }
}
