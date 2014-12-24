package ua.com.studiovision.euromaidan.network.json_protocol.search;

import ua.com.studiovision.euromaidan.network.json_protocol.AbstractRequest;
import ua.com.studiovision.euromaidan.network.json_protocol.AbstractResponse;

/**
 * Created by gaidamak on 08.12.14.
 */
public final class SearchProtocol {
    private SearchProtocol() {
    }

    public static class SearchUsersRequest implements AbstractRequest<SearchUsersRequest> {
        private static final String TAG = "SearchUsersRequest";
        public String key = "search";
        // all,people,publics,audios,videos
        public SearchCategory content;
        // ай-ди по которым вибирать данные
        public long[] ids;
        public AudioIds audios_ids;
        // количество результатов найденных ранее(при поиске)!
        public Integer count; //'5', / null
        public SearchFilters filters;

        public SearchUsersRequest(SearchCategory content, long[] ids,
                                  long[] music_from_users, long[] music_from_publics,
                                  Integer count, String searchQuery) {
            this.content = content;
            this.ids = ids;
            this.audios_ids = new AudioIds(music_from_users, music_from_publics);
            this.count = count;
            this.filters = new SearchFilters(searchQuery);
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
        }

        public class VideosResponse extends InfiniteScrollResponse {
            public MyVideo[] videos;
        }
    }

    public static class AudioIds {
        public long[] users;
        public long[] publics;

        public AudioIds(long[] users, long[] publics) {
            this.users = users;
            this.publics = publics;
        }
    }
}
