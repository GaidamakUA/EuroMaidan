package ua.com.studiovision.euromaidan.network.process_strategies;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import ua.com.studiovision.euromaidan.network.json_protocol.DialogsProtocol;
import ua.com.studiovision.euromaidan.network.json_protocol.friends.FriendsContent;
import ua.com.studiovision.euromaidan.network.json_protocol.friends.GetFriendsProtocol;
import ua.com.studiovision.euromaidan.network.json_protocol.search.User;
import ua.com.studiovision.euromaidan.network.provider.applicant.ApplicantColumns;
import ua.com.studiovision.euromaidan.network.provider.applicant.ApplicantContentValues;
import ua.com.studiovision.euromaidan.network.provider.followers.FollowersColumns;
import ua.com.studiovision.euromaidan.network.provider.followers.FollowersContentValues;
import ua.com.studiovision.euromaidan.network.provider.friends.FriendsColumns;
import ua.com.studiovision.euromaidan.network.provider.friends.FriendsContentValues;

public class GetDialogsStrategy extends AbstractProcessResponseStrategy
        <DialogsProtocol.GetDialogsRequest, DialogsProtocol.GetDialogsResponse> {

    public static final String TAG = "GetDialogsStrategy";

    public GetDialogsStrategy(Context context, String token) {
        super(context, DialogsProtocol.GetDialogsResponse.class);
        request = new DialogsProtocol.GetDialogsRequest(token);
    }

    @Override
    protected void onResponse(DialogsProtocol.GetDialogsResponse response) {
        Log.v(TAG, "onResponse(" + "response=" + response + ")");
    }
}
