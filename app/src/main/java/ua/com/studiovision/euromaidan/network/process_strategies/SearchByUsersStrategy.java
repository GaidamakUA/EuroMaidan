package ua.com.studiovision.euromaidan.network.process_strategies;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;

import ua.com.studiovision.euromaidan.network.json_protocol.user_search.SearchUsersProtocol;
import ua.com.studiovision.euromaidan.network.json_protocol.user_search.User;
import ua.com.studiovision.euromaidan.network.provider.users.UsersContentValues;

/**
 * Created by gaidamak on 08.12.14.
 */
public class SearchByUsersStrategy extends AbstractProcessResponseStrategy
        <SearchUsersProtocol.SearchUsersRequest, SearchUsersProtocol.SearchUsersResponse> {
    public SearchByUsersStrategy(Context context, Message message) {
        super(context, SearchUsersProtocol.SearchUsersResponse.class);
        Bundle bundle = message.getData();
        request = new SearchUsersProtocol.SearchUsersRequest(bundle.getIntArray("user_ids"),
                bundle.getInt("count"), bundle.getString("search_query"));
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
    }
}
