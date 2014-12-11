package ua.com.studiovision.euromaidan.network.process_strategies;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import java.util.ArrayList;

import ua.com.studiovision.euromaidan.AppProtocol;
import ua.com.studiovision.euromaidan.activities.SearchActivity;
import ua.com.studiovision.euromaidan.network.json_protocol.user_search.InfiniteScrollResponse;
import ua.com.studiovision.euromaidan.network.json_protocol.user_search.SearchUsersProtocol;
import ua.com.studiovision.euromaidan.network.json_protocol.user_search.User;
import ua.com.studiovision.euromaidan.network.provider.users.UsersContentValues;

/**
 * Created by gaidamak on 08.12.14.
 */
public class SearchStrategy extends AbstractProcessResponseStrategy
        <SearchUsersProtocol.SearchUsersRequest, SearchUsersProtocol.SearchUsersResponse> {
    private static final String TAG = "SearchByUsersStrategy";
    StrategyCallbacks callbacks;

    public SearchStrategy(Context context, Message message, StrategyCallbacks callbacks) {
        super(context, SearchUsersProtocol.SearchUsersResponse.class);
        Bundle bundle = message.getData();
        Log.v(TAG, "SearchActivity.SEARCH_QUERY=" + bundle.getString(SearchActivity.SEARCH_QUERY));
        request = new SearchUsersProtocol.SearchUsersRequest(bundle.getIntegerArrayList(SearchActivity.USER_IDS),
                bundle.getInt(SearchActivity.USERS_COUNT), bundle.getString(SearchActivity.SEARCH_QUERY));
        this.callbacks = callbacks;
    }

    @Override
    protected void onResponse(SearchUsersProtocol.SearchUsersResponse response) {
        // handle response here
        if (response.result.users == null || response.result.users.users.length < 1) {
            return;
        }
        // uncommon
        UsersContentValues contentValues = new UsersContentValues();
        for (User user : response.result.users.users) {
            contentValues.putUserId(user.id)
                    .putUserName(user.name)
                    .putUserSurname(user.surname)
                    .putAvatar(user.avatar);
            // common
            contentValues.insert(context.getContentResolver());
        }
        // Generalizable
        Bundle bundle = new Bundle();
        addStuffToBundle(bundle, response.result.users, SearchActivity.USER_IDS,
                SearchActivity.USERS_COUNT);

        Message msg = Message.obtain();
        msg.what = AppProtocol.SEARCH_BY_USERS_RESPONSE;
        msg.setData(bundle);
        callbacks.sendMessageToActivity(msg);
    }

    private void addStuffToBundle(Bundle bundle, InfiniteScrollResponse infiniteScrollResponse,
                                  String id_key, String count_key) {
        if (infiniteScrollResponse.ids != null && infiniteScrollResponse.ids.length > 0) {
            ArrayList<Integer> availableIds = new ArrayList<Integer>(infiniteScrollResponse.ids.length);
            for (int i = infiniteScrollResponse.ids.length - 1; i >= 0; i--) {
                availableIds.add(infiniteScrollResponse.ids[i][0]);
            }
            bundle.putIntegerArrayList(id_key, availableIds);
        }
        int count = infiniteScrollResponse.count == null ? 0 : infiniteScrollResponse.count;
        bundle.putInt(count_key, count);
    }
}
