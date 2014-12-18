package ua.com.studiovision.euromaidan.network.process_strategies;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;

import ua.com.studiovision.euromaidan.network.json_protocol.AbstractGetArrayProtocol;
import ua.com.studiovision.euromaidan.network.json_protocol.friends.FriendsContent;
import ua.com.studiovision.euromaidan.network.json_protocol.friends.GetFriendsProtocol;
import ua.com.studiovision.euromaidan.network.json_protocol.search.User;
import ua.com.studiovision.euromaidan.network.provider.applicant.ApplicantColumns;
import ua.com.studiovision.euromaidan.network.provider.applicant.ApplicantContentValues;
import ua.com.studiovision.euromaidan.network.provider.followers.FollowersColumns;
import ua.com.studiovision.euromaidan.network.provider.followers.FollowersContentValues;
import ua.com.studiovision.euromaidan.network.provider.friends.FriendsColumns;
import ua.com.studiovision.euromaidan.network.provider.friends.FriendsContentValues;

public class GetFriendsStrategy extends AbstractProcessResponseStrategy
        <GetFriendsProtocol.GetFriendsRequest, GetFriendsProtocol.GetFriendsResponse> {

    public static final String TAG = "GetFriendsStrategy";
    private FriendsContent friendsContent;

    public GetFriendsStrategy(Context context, Message msg) {
        super(context, GetFriendsProtocol.GetFriendsResponse.class);
        Bundle bundle = msg.getData();
        long idUser = bundle.getLong("id_user");
        friendsContent = (FriendsContent)bundle.getSerializable("content");
        request = new GetFriendsProtocol.GetFriendsRequest(idUser, friendsContent);
    }

    @Override
    protected void onResponse(GetFriendsProtocol.GetFriendsResponse response) {
        switch (friendsContent) {
            case FRIENDS:
                FriendsContentValues friendsContentValues;
                context.getContentResolver().delete(FriendsColumns.CONTENT_URI,null,null);
                for (User user : response.content) {
                    friendsContentValues = new FriendsContentValues();
                    friendsContentValues.putFriendAvatar(user.avatar);
                    friendsContentValues.putFriendId(user.id);
                    friendsContentValues.putFriendName(user.name);
                    friendsContentValues.putFriendSurname(user.surname);
                    friendsContentValues.insert(context.getContentResolver());
                }
                break;
            case FRIENDS_REQUESTS:
                ApplicantContentValues applicantContentValues;
                context.getContentResolver().delete(ApplicantColumns.CONTENT_URI,null,null);
                for (User user : response.content) {
                    applicantContentValues = new ApplicantContentValues();
                    applicantContentValues.putApplicantAvatar(user.avatar);
                    applicantContentValues.putApplicantId(user.id);
                    applicantContentValues.putApplicantName(user.name);
                    applicantContentValues.putApplicantSurname(user.surname);
                    applicantContentValues.insert(context.getContentResolver());
                }
                break;
            case FOLLOWERS:
                FollowersContentValues followerContentValues;
                context.getContentResolver().delete(FollowersColumns.CONTENT_URI,null,null);
                for (User user : response.content) {
                    followerContentValues = new FollowersContentValues();
                    followerContentValues.putFollowerAvatar(user.avatar);
                    followerContentValues.putFollowerId(user.id);
                    followerContentValues.putFollowerName(user.name);
                    followerContentValues.putFollowerSurname(user.surname);
                    followerContentValues.insert(context.getContentResolver());
                }
                break;
        }
    }
}
