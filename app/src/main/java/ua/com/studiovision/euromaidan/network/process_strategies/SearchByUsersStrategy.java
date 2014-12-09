package ua.com.studiovision.euromaidan.network.process_strategies;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import ua.com.studiovision.euromaidan.AppProtocol;
import ua.com.studiovision.euromaidan.SearchActivity;
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
        request = new SearchUsersProtocol.SearchUsersRequest(bundle.getIntArray(SearchActivity.USER_IDS),
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
        if (response.result.ids != null || response.result.ids.length > 0) {
            int[] availableIds = new int[response.result.ids.length];
            for (int i = availableIds.length - 1; i >= 0; i--) {
                availableIds[i] = response.result.ids[i][0];
            }
            bundle.putIntArray("available_ids", availableIds);
        }
        bundle.putInt("count", response.result.count);

        Message msg = Message.obtain();
        msg.what = AppProtocol.SEARCH_BY_USERS_RESPONSE;
        msg.setData(bundle);
        callbacks.sendMessageToActivity(msg);
    }
}
