package ua.com.studiovision.euromaidan.network.process_strategies;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;

import ua.com.studiovision.euromaidan.network.json_protocol.AbstractGetArrayProtocol;
import ua.com.studiovision.euromaidan.network.json_protocol.friends.FriendsContent;
import ua.com.studiovision.euromaidan.network.json_protocol.friends.GetFriendsProtocol;
import ua.com.studiovision.euromaidan.network.json_protocol.search.User;
import ua.com.studiovision.euromaidan.network.provider.friends.FriendsContentValues;

public class GetFriendsStrategy extends AbstractProcessResponseStrategy
        <GetFriendsProtocol.GetFriendsRequest, GetFriendsProtocol.GetFriendsResponse> {

    public static final String TAG = "GetFriendsStrategy";


    public GetFriendsStrategy(Context context, Message msg) {
        super(context, GetFriendsProtocol.GetFriendsResponse.class);
        Bundle bundle = msg.getData();
        long idUser = bundle.getLong("id_user");
        FriendsContent content = (FriendsContent)bundle.getSerializable("content");
        request = new GetFriendsProtocol.GetFriendsRequest(idUser,content);
    }

    @Override
    protected void onResponse(GetFriendsProtocol.GetFriendsResponse response) {
        FriendsContentValues contentValues;
        for (User user : response.content) {
            contentValues = new FriendsContentValues();
            contentValues.putFriendAvatar(user.avatar);
            contentValues.putFriendId(user.id);
            contentValues.putFriendName(user.name);
            contentValues.putFriendSurname(user.surname);
        }
    }

}
