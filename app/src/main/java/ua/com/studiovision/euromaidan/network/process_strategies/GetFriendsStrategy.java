package ua.com.studiovision.euromaidan.network.process_strategies;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;

import ua.com.studiovision.euromaidan.network.json_protocol.AbstractGetArrayProtocol;
import ua.com.studiovision.euromaidan.network.json_protocol.friends.FriendsContent;
import ua.com.studiovision.euromaidan.network.json_protocol.friends.GetFriendsProtocol;
import ua.com.studiovision.euromaidan.network.json_protocol.search.User;

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
        for (User user : response.content) {
            
        }
    }

}
