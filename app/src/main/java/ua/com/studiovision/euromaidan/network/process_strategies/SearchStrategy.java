package ua.com.studiovision.euromaidan.network.process_strategies;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import ua.com.studiovision.euromaidan.AppProtocol;
import ua.com.studiovision.euromaidan.activities.SearchActivity;
import ua.com.studiovision.euromaidan.network.json_protocol.search.InfiniteScrollResponse;
import ua.com.studiovision.euromaidan.network.json_protocol.search.MyAudio;
import ua.com.studiovision.euromaidan.network.json_protocol.search.MyVideo;
import ua.com.studiovision.euromaidan.network.json_protocol.search.SearchCategory;
import ua.com.studiovision.euromaidan.network.json_protocol.search.SearchProtocol;
import ua.com.studiovision.euromaidan.network.json_protocol.search.User;
import ua.com.studiovision.euromaidan.network.provider.audios.AudiosContentValues;
import ua.com.studiovision.euromaidan.network.provider.users.UsersContentValues;
import ua.com.studiovision.euromaidan.network.provider.videos.VideosContentValues;

/**
 * Created by gaidamak on 08.12.14.
 */
public class SearchStrategy extends AbstractProcessResponseStrategy
        <SearchProtocol.SearchUsersRequest, SearchProtocol.SearchUsersResponse> {
    private static final String TAG = "SearchStrategy";
    StrategyCallbacks callbacks;

    public SearchStrategy(Context context, Message message, StrategyCallbacks callbacks) {
        super(context, SearchProtocol.SearchUsersResponse.class);
        Bundle bundle = message.getData();
        Log.v(TAG, "SearchActivity.SEARCH_QUERY=" + bundle.getString(SearchActivity.SEARCH_QUERY));

        request = new SearchProtocol.SearchUsersRequest(bundle.getLongArray(SearchActivity.USER_IDS),
                bundle.getInt(SearchActivity.USERS_COUNT), bundle.getString(SearchActivity.SEARCH_QUERY),
                (SearchCategory) bundle.getSerializable(SearchActivity.CONTENTS));
        this.callbacks = callbacks;
    }

    @Override
    protected void onResponse(SearchProtocol.SearchUsersResponse response) {
        if (response.result == null) return;
        if (response.result.users != null) {
            UsersContentValues usersContentValues = new UsersContentValues();
            for (User user : response.result.users.users) {
                usersContentValues.putUserId(user.id)
                        .putUserName(user.name)
                        .putUserSurname(user.surname)
                        .putAvatar(user.avatar);
                usersContentValues.insert(context.getContentResolver());
            }
        }
        if (response.result.audios != null) {
            AudiosContentValues audioContentValues = new AudiosContentValues();
            for (MyAudio audio : response.result.audios.publics) {
                audioContentValues
                        .putName(audio.name)
                        .putAuthor(audio.author)
                        .putDuration(audio.duration)
                        .putAudioUrl(audio.url);
                audioContentValues.insert(context.getContentResolver());
            }
            for (MyAudio audio : response.result.audios.users) {
                audioContentValues
                        .putName(audio.name)
                        .putAuthor(audio.author)
                        .putDuration(audio.duration)
                        .putAudioUrl(audio.url);
                audioContentValues.insert(context.getContentResolver());
            }
        }
        if (response.result.videos != null) {
            VideosContentValues videosContentValues = new VideosContentValues();
            for (MyVideo video : response.result.videos.videos) {
                videosContentValues
                        .putName(video.name)
                        .putDuration(video.duration)
                        .putVideoUrl(video.url);
                videosContentValues.insert(context.getContentResolver());
            }
        }

        Bundle bundle = new Bundle();
        addStuffToBundle(bundle, response.result.users);
        addStuffToBundle(bundle, response.result.audios);
        addStuffToBundle(bundle, response.result.videos);

        Message msg = Message.obtain();
        msg.what = AppProtocol.SEARCH_BY_USERS_RESPONSE;
        msg.setData(bundle);
        callbacks.sendMessageToActivity(msg);
    }

    private void addStuffToBundle(Bundle bundle, InfiniteScrollResponse infiniteScrollResponse) {
        if (infiniteScrollResponse == null) return;
        String id_key = null;
        String count_key = null;
        if (infiniteScrollResponse instanceof SearchProtocol.SearchUsersResponse.UsersResponse) {
            id_key = SearchActivity.USER_IDS;
            count_key = SearchActivity.USERS_COUNT;
        } else if (infiniteScrollResponse instanceof SearchProtocol.SearchUsersResponse.MusicResponse) {
            // TODO rewrite after hack removed from server
            Log.v(TAG, "MUSIC");
            bundle.putLongArray(SearchActivity.MUSIC_FROM_PUBLICS_IDS,
                    ((SearchProtocol.SearchUsersResponse.MusicResponse) infiniteScrollResponse)
                            .audios_ids.publics);
            bundle.putLongArray(SearchActivity.MUSIC_FROM_USERS_IDS,
                    ((SearchProtocol.SearchUsersResponse.MusicResponse) infiniteScrollResponse)
                            .audios_ids.users);
            bundle.putInt(SearchActivity.MUSIC_COUNT, infiniteScrollResponse.count);
            return;
        } else if (infiniteScrollResponse instanceof SearchProtocol.SearchUsersResponse.VideosResponse) {
            id_key = SearchActivity.VIDEOS_IDS;
            count_key = SearchActivity.VIDEOS_COUNT;
        } else {
            throw new IllegalArgumentException("unexpected instance of InfiniteScrollResponse:"
                    + infiniteScrollResponse.getClass().getName());
        }
        if (infiniteScrollResponse.ids != null && infiniteScrollResponse.ids.length > 0) {
//            ArrayList<Integer> availableIds = new ArrayList<Integer>(infiniteScrollResponse.ids.length);
//            for (int i = infiniteScrollResponse.ids.length - 1; i >= 0; i--) {
//                availableIds.add(infiniteScrollResponse.ids[i][0]);
//            }
            bundle.putLongArray(id_key, infiniteScrollResponse.ids);
        }
        int count = infiniteScrollResponse.count == null ? 0 : infiniteScrollResponse.count;
        bundle.putInt(count_key, count);
    }
}
