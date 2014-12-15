package ua.com.studiovision.euromaidan.network.process_strategies;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;

import ua.com.studiovision.euromaidan.network.json_protocol.friends.AddFriendProtocol;
import ua.com.studiovision.euromaidan.network.json_protocol.friends.DeleteFriendProtocol;
import ua.com.studiovision.euromaidan.network.json_protocol.settings.SetSettingProtocol;

public class DeleteFriendStrategy extends AbstractProcessResponseStrategy
        <DeleteFriendProtocol.DeleteFriendRequest, DeleteFriendProtocol.DeleteFriendResponse> {

    private static final String TAG = "DeleteFriendStrategy";

    public DeleteFriendStrategy(Context context, Message msg) {
        super(context, DeleteFriendProtocol.DeleteFriendResponse.class);
        Bundle bundle = msg.getData();
        String token = bundle.getString(SetSettingProtocol.TOKEN);
        long friendId = bundle.getLong("friend_id");
        request = new DeleteFriendProtocol.DeleteFriendRequest(token, friendId);
    }
}
