package ua.com.studiovision.euromaidan.network.json_protocol.search;

import android.util.Log;

import ua.com.studiovision.euromaidan.network.json_protocol.AbstractRequest;
import ua.com.studiovision.euromaidan.network.json_protocol.AbstractResponse;

/**
 * Created by gaidamak on 08.12.14.
 */
public class SearchProtocol {
    public static class SearchUsersRequest implements AbstractRequest<SearchUsersRequest> {
        private static final String TAG = "SearchUsersRequest";
        public String key = "search";
        // all,people,publics,audios,videos
        public SearchCategory content;
        // ай-ди по которым вибирать данные
        public long[] ids;
        // количество результатов найденных ранее(при поиске)!
        public Integer count; //'5', / null
        public SearchFilters filters;

        public SearchUsersRequest(long[] ids, Integer count, String search_query, SearchCategory content) {
            Log.v(TAG, "SearchUsersRequest(" + "ids=" + ids + ", count=" + count + ", search_query=" + search_query + ")");
            if (ids != null && ids.length > 0) {
                int length = ids.length;
                this.ids = ids;
            }
            this.count = count;
            this.filters = new SearchFilters(search_query);
            this.content = content;
        }

        public SearchUsersRequest(long[] ids, Integer count, String search_query) {
            Log.v(TAG, "SearchUsersRequest(" + "ids=" + ids + ", count=" + count + ", search_query=" + search_query + ")");
            if (ids != null && ids.length > 0) {
                int length = ids.length;
                this.ids = ids;
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
            public UsersResponse users;
            public GroupsResponse publics;
            public MusicResponse audios;
            public VideosResponse videos;
        }

        public class UsersResponse extends InfiniteScrollResponse {
            public User[] users;
        }

        public class GroupsResponse extends InfiniteScrollResponse {
            public Public[] groups;
        }

        public class MusicResponse extends InfiniteScrollResponse {
            public MyAudio[] users;
            public MyAudio[] publics;
            public AudioIds audios_ids;

            public class AudioIds {
                public long[] users;
                public long[] publics;
            }
        }

        public class VideosResponse extends InfiniteScrollResponse {
            public MyVideo[] videos;
        }
    }
}
