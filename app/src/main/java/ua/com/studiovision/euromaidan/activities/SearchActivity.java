package ua.com.studiovision.euromaidan.activities;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Message;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.softevol.activity_service_communication.ActivityServiceCommunicationActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ua.com.studiovision.euromaidan.AppProtocol;
import ua.com.studiovision.euromaidan.R;
import ua.com.studiovision.euromaidan.SharedPrefs_;
import ua.com.studiovision.euromaidan.network.MainService_;
import ua.com.studiovision.euromaidan.network.json_protocol.search.SearchCategory;
import ua.com.studiovision.euromaidan.search_fragments.AudioSearchFragment_;
import ua.com.studiovision.euromaidan.search_fragments.NewsSearchFragment_;
import ua.com.studiovision.euromaidan.search_fragments.PublicSearchFragment_;
import ua.com.studiovision.euromaidan.search_fragments.SearchActivityCallbacks;
import ua.com.studiovision.euromaidan.search_fragments.UserSearchFragment_;
import ua.com.studiovision.euromaidan.search_fragments.VideoSearchFragment_;

@EActivity(R.layout.activity_search)
public class SearchActivity extends ActivityServiceCommunicationActivity implements SearchActivityCallbacks {
    @Pref
    SharedPrefs_ preferences;

    @ViewById(R.id.view_pager)
    ViewPager viewPager;
    @ViewById(R.id.searchSlidingStrip)
    PagerSlidingTabStrip searchCategoriesSlidingTabStrip;
    @ViewById(R.id.search_field_edit_text)
    public EditText searchEditText;

    private final String TAG = "SearchActivity";
    private final int PAGE_COUNT = 5;

    LinearLayout searchActionBar;
    private List<Fragment> fragments = new ArrayList<Fragment>();
    private Set<Fragment> disposableFragment = new HashSet<Fragment>();
    private FragmentPagerAdapter fragmentPagerAdapter;

    private HashSet<Long> mUnseenUserIds = new HashSet<Long>();
    private int mUserIdCount;
    private HashSet<AudioId> mUnseenAudioIds = new HashSet<AudioId>();
    private int mAudioIdCount;

    public static final String CONTENTS = "contents";
    public static final String USER_IDS = "user_ids";
    public static final String COUNT = "users_count";
//    public static final String GROUPS_IDS = "groups_ids";
//    public static final String GROUPS_COUNT = "groups_count";
    public static final String MUSIC_FROM_PUBLICS_IDS = "music_from_publics_ids";
    public static final String MUSIC_FROM_USERS_IDS = "music_from_users_ids";
    public static final String MUSIC_COUNT = "music_count";
    public static final String VIDEOS_IDS = "videos_ids";
    public static final String VIDEOS_COUNT = "videos_count";
    public static final String SEARCH_QUERY = "search_query";
    public static final String FRIEND_ID = "friend_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mServiceClass = MainService_.class;

        LayoutInflater inflater = getLayoutInflater();
        searchActionBar = (LinearLayout) inflater.inflate(R.layout.search_action_bar, null);

        ActionBar.LayoutParams layoutParams =
                new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                        ActionBar.LayoutParams.WRAP_CONTENT);

        ActionBar actionBar = getActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(searchActionBar, layoutParams);
        actionBar.setBackgroundDrawable(new ColorDrawable(R.color.darker_grey));
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
    }

    @AfterViews
    void init() {
        TextView cancel = (TextView) searchActionBar.findViewById(R.id.cancel_textview);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        fragments.add(new NewsSearchFragment_());
        fragments.add(new UserSearchFragment_());
        fragments.add(new PublicSearchFragment_());
        fragments.add(new VideoSearchFragment_());
        fragments.add(new AudioSearchFragment_());

        fragmentPagerAdapter = new SearchFragmentPagerAdapter(getFragmentManager());
        viewPager.setAdapter(fragmentPagerAdapter);

        searchCategoriesSlidingTabStrip.setShouldExpand(true);
        searchCategoriesSlidingTabStrip.setUnderlineHeight((int) getResources().getDimension(R.dimen.invisibleUnderline));
        searchCategoriesSlidingTabStrip.setIndicatorColorResource(android.R.color.white);
        searchCategoriesSlidingTabStrip.setIndicatorHeight((int) getResources().getDimension(R.dimen.slidingIndicatorHeight));
        searchCategoriesSlidingTabStrip.setDividerColorResource(android.R.color.transparent);
        searchCategoriesSlidingTabStrip.setViewPager(viewPager);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mUnseenUserIds.clear();

                Message msg = Message.obtain();
                msg.what = AppProtocol.SEARCH;
                Bundle data = new Bundle();
                data.putString(SEARCH_QUERY, s.toString());
                int pos = viewPager.getCurrentItem();
                Log.v(TAG, "Position set to -> " + pos);
                data.putSerializable(CONTENTS, SearchCategory.values()[viewPager.getCurrentItem()]);
                msg.setData(data);
                sendMessage(msg);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "SearchActivity.onDestroy(" + ")");
        for (Fragment fragment : disposableFragment) {
            Log.v(TAG, "fragment=" + fragment);
            fragmentPagerAdapter.destroyItem(null, 0, fragment);
        }
    }

    @Override
    protected void handleMessage(Message msg) {
        switch (msg.what) {
            case AppProtocol.ON_SERVICE_CONNECTED:
                Log.v(TAG, "Service connected");
                break;
            case AppProtocol.SEARCH_BY_USERS_RESPONSE:
                Bundle data = msg.getData();
                Log.v(TAG, "search_by_users_response=" + data);
                if (mUnseenUserIds.isEmpty() && data.getLongArray(USER_IDS) != null) {
                    for (long element : data.getLongArray(USER_IDS)) {
                        mUnseenUserIds.add(element);
                    }
                    mUserIdCount = data.getInt(COUNT);
                }
                if (mUnseenAudioIds.isEmpty()) {
                    if (data.getLongArray(MUSIC_FROM_PUBLICS_IDS) != null) {
                        Log.v(TAG, "Publics=" + Arrays.toString(data.getLongArray(MUSIC_FROM_PUBLICS_IDS)));
                        for (long l : data.getLongArray(MUSIC_FROM_PUBLICS_IDS)) {
                            mUnseenAudioIds.add(new AudioId(l, IdType.PUBLICS_AUDIO));
                        }
                    }
                    if (data.getLongArray(MUSIC_FROM_USERS_IDS) != null) {
                        Log.v(TAG, "Users=" + Arrays.toString(data.getLongArray(MUSIC_FROM_USERS_IDS)));
                        for (long l : data.getLongArray(MUSIC_FROM_USERS_IDS)) {
                            mUnseenAudioIds.add(new AudioId(l, IdType.USERS_AUDIO));
                        }
                    }
                    mAudioIdCount = data.getInt(MUSIC_COUNT);
                }
                break;
        }
    }

    @OptionsItem(android.R.id.home)
    void upNavigation() {
        Intent upIntent = NavUtils.getParentActivityIntent(this);
        startActivity(upIntent);
        finish();
    }

    @Override
    public void loadMoreUsers() {
        if (mUnseenUserIds.isEmpty()) return;
        Bundle data = new Bundle();
        int counter = mUnseenUserIds.size() > 10 ? 10 : mUnseenUserIds.size();
        long[] idsToRequest = new long[counter];
        for (Long unseenUserId : mUnseenUserIds) {
            if (--counter < 0) break;
            Log.v(TAG, "counter=" + counter);
            idsToRequest[counter] = unseenUserId;
        }
        for (long i : idsToRequest) {
            mUnseenUserIds.remove(i);
        }
        Log.v(TAG, "idsToRequest=" + Arrays.toString(idsToRequest));

        data.putLongArray(USER_IDS, idsToRequest);
        data.putInt(COUNT, mUserIdCount);
        data.putSerializable(CONTENTS, SearchCategory.PEOPLE);

        Message msg = Message.obtain();
        msg.what = AppProtocol.SEARCH;
        msg.setData(data);

        sendMessage(msg);
    }

    @Override
    public void loadMoreAudio() {
        if (mUnseenAudioIds.isEmpty()) return;
        int counter = mUnseenAudioIds.size() > 10 ? 10 : mUnseenAudioIds.size();
        ArrayList<Long> publicAudioIdList = new ArrayList<Long>(counter);
        ArrayList<Long> userAudioIdList = new ArrayList<Long>(counter);
        for (AudioId audioId : mUnseenAudioIds) {
            if (--counter < 0) break;
            switch (audioId.type) {
                case PUBLICS_AUDIO:
                    publicAudioIdList.add(audioId.id);
                    break;
                case USERS_AUDIO:
                    userAudioIdList.add(audioId.id);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown type of audio id:" + audioId.type);
            }
        }
        long[] userAudioIds = new long[userAudioIdList.size()];
        for (int i = userAudioIds.length - 1; i >= 0; i--) {
            userAudioIds[i] = userAudioIdList.get(i);
        }
        long[] publicsAudioIds = new long[publicAudioIdList.size()];
        for (int i = publicsAudioIds.length - 1; i >= 0; i--) {
            publicsAudioIds[i] = publicAudioIdList.get(i);
        }

        Bundle data = new Bundle();
        data.putSerializable(CONTENTS, SearchCategory.AUDIOS);
        Log.v(TAG, "PuclicsOut=" + Arrays.toString(publicsAudioIds));
        data.putLongArray(MUSIC_FROM_PUBLICS_IDS, publicsAudioIds);
        Log.v(TAG, "UsersOut=" + Arrays.toString(userAudioIds));
        data.putLongArray(MUSIC_FROM_USERS_IDS, userAudioIds);
        data.putInt(COUNT, mAudioIdCount);

        Message msg = Message.obtain();
        msg.what = AppProtocol.SEARCH;
        msg.setData(data);

        sendMessage(msg);
    }

    @Override
    public void addFriend(long userId) {
        Log.v(TAG, "addFriend(" + "userId=" + userId + ")");
        Bundle data = new Bundle();
        data.putLong(FRIEND_ID, userId);
        data.putString(FirstRunActivity.TOKEN, preferences.getToken().get());
        Message msg = Message.obtain();
        msg.what = AppProtocol.ADD_FRIEND;
        msg.setData(data);
        sendMessage(msg);
    }

    private class SearchFragmentPagerAdapter extends FragmentPagerAdapter implements PagerSlidingTabStrip.IconTabProvider {

        private int[] ICONS = new int[]{
                R.drawable.news_icon,
                R.drawable.settings_profile_icon,
                R.drawable.groups_icon,
                R.drawable.video_icon,
                R.drawable.audio_icon
        };

        private String[] TITLES = new String[]{
                "Общие",
                "Друзья",
                "Группы",
                "Видеозаписи",
                "Аудиозаписи"
        };

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        public SearchFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Log.v(TAG, "Getting item in position " + i + "; fragment=" + fragments.get(i));
            Fragment fragment = fragments.get(i);
            disposableFragment.add(fragment);
            return fragment;
        }

        @Override
        public int getPageIconResId(int i) {
            return ICONS[i];
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }
    }

    private class AudioId {
        long id;
        IdType type;

        private AudioId(long id, IdType type) {
            this.id = id;
            this.type = type;
        }
    }

    private enum IdType {
        USERS_AUDIO,
        PUBLICS_AUDIO
    }
}
