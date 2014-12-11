package ua.com.studiovision.euromaidan.network.process_strategies;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;

import ua.com.studiovision.euromaidan.AppProtocol;
import ua.com.studiovision.euromaidan.activities.FeedActivity;
import ua.com.studiovision.euromaidan.activities.SearchActivity;
import ua.com.studiovision.euromaidan.network.json_protocol.user_search.SearchUsersProtocol;
import ua.com.studiovision.euromaidan.network.json_protocol.user_search.User;
import ua.com.studiovision.euromaidan.network.provider.users.UsersContentValues;

/**
 * Created by gaidamak on 08.12.14.
 */
public class SearchByUsersStrategy extends AbstractProcessResponseStrategy
        <SearchUsersProtocol.SearchUsersRequest, SearchUsersProtocol.SearchUsersResponse> {
    private static final String TAG = "SearchByUsersStrategy";
    StrategyCallbacks callbacks;

    public SearchByUsersStrategy(Context context, Message message, StrategyCallbacks callbacks) {
        super(context, SearchUsersProtocol.SearchUsersResponse.class);
        Bundle bundle = message.getData();
        Log.v(TAG, "SearchActivity.SEARCH_QUERY=" + bundle.getString(SearchActivity.SEARCH_QUERY));
        request = new SearchUsersProtocol.SearchUsersRequest(bundle.getIntegerArrayList(SearchActivity.USER_IDS),
                bundle.getInt(SearchActivity.COUNT), bundle.getString(SearchActivity.SEARCH_QUERY));
        this.callbacks = callbacks;
    }

    @Override
    protected void onResponse(SearchUsersProtocol.SearchUsersResponse response) {
        // handle response here
        if (response.result.users == null || response.result.users.length < 1) {
            return;
        }
        UsersContentValues contentValues = new UsersContentValues();
        for (User user : response.result.users) {
            contentValues.putUserId(user.id)
                    .putUserName(user.name)
                    .putUserSurname(user.surname)
                    .putAvatar(user.avatar);
            contentValues.insert(context.getContentResolver());
        }
        Bundle bundle = new Bundle();
        if (response.result.ids != null && response.result.ids.length > 0) {
//            int[] availableIds = new int[response.result.ids.length];
//            for (int i = availableIds.length - 1; i >= 0; i--) {
//                availableIds[i] = response.result.ids[i][0];
//            }
            ArrayList<Integer> availableIds = new ArrayList<Integer>(response.result.ids.length);
            for (int i = response.result.ids.length - 1; i >= 0; i--) {
                availableIds.add(response.result.ids[i][0]);
            }
            bundle.putIntegerArrayList(SearchActivity.USER_IDS, availableIds);
        }
        int count = response.result.count == null? 0 : response.result.count;
        bundle.putInt(SearchActivity.COUNT, count);

        Message msg = Message.obtain();
        msg.what = AppProtocol.SEARCH_BY_USERS_RESPONSE;
        msg.setData(bundle);
        callbacks.sendMessageToActivity(msg);
    }
}
