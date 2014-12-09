package ua.com.studiovision.euromaidan.network.json_protocol.user_search;

import android.util.Log;

import ua.com.studiovision.euromaidan.network.json_protocol.AbstractRequest;
import ua.com.studiovision.euromaidan.network.json_protocol.AbstractResponse;

/**
 * Created by gaidamak on 08.12.14.
 */
public class SearchUsersProtocol {
    public static class SearchUsersRequest implements AbstractRequest<SearchUsersRequest> {
        private static final String TAG = "SearchUsersRequest";
        public String key = "search"; // all,people,publics,audios,videos
        public String content = "people";
        // ай-ди по которым вибирать данные
        public int[][] ids; //[[58],[59],[23]]; // null
        // количество результатов найденных ранее(при поиске)!
        public Integer count; //'5', / null
        public SearchFilters filters;

        public SearchUsersRequest(int[] ids, Integer count, String search_query) {
            Log.v(TAG, "SearchUsersRequest(" + "ids=" + ids + ", count=" + count + ", search_query=" + search_query + ")");
            if (ids != null && ids.length > 0) {
                int length = ids.length;
                this.ids = new int[length][1];
                for (int i = 0; i < length; i++) {
                    this.ids[i][0] = ids[0];
                }
            }
            this.count = count;
            this.filters = new SearchFilters(search_query);
        }

        public class SearchFilters {
            // т.к. других фильтров пока нет, то структура такая,
            // в будущем будут в фильтры добавляться новые фильры
            String text_main;

            public SearchFilters(String textMain) {
                this.text_main = textMain;
            }
        }
    }

    public static class SearchUsersResponse extends AbstractResponse<SearchUsersResponse> {
        public SearchByUserResult result;

        public class SearchByUserResult {
            public User[] users;
            public Integer count;
            public int[][] ids;
        }
    }
}
