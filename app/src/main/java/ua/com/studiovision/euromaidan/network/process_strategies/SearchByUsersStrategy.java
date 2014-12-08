package ua.com.studiovision.euromaidan.network.process_strategies;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;

import ua.com.studiovision.euromaidan.network.json_protocol.user_search.SearchUsersProtocol;

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
    }
}
