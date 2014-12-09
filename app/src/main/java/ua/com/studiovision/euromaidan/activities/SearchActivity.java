package ua.com.studiovision.euromaidan.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Message;
import android.support.v13.app.FragmentPagerAdapter;
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
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ua.com.studiovision.euromaidan.AppProtocol;
import ua.com.studiovision.euromaidan.R;
import ua.com.studiovision.euromaidan.activities.FeedActivity_;
import ua.com.studiovision.euromaidan.network.MainService_;
import ua.com.studiovision.euromaidan.network.provider.users.UsersSelection;
import ua.com.studiovision.euromaidan.search_fragments.AudioSearchFragment_;
import ua.com.studiovision.euromaidan.search_fragments.GroupSearchFragment_;
import ua.com.studiovision.euromaidan.search_fragments.NewsSearchFragment_;
import ua.com.studiovision.euromaidan.search_fragments.UserSearchFragment;
import ua.com.studiovision.euromaidan.search_fragments.UserSearchFragment_;
import ua.com.studiovision.euromaidan.search_fragments.VideoSearchFragment_;

@EActivity(R.layout.activity_search)
public class SearchActivity extends ActivityServiceCommunicationActivity {
    @ViewById(R.id.view_pager)
    ViewPager viewPager;
    @ViewById(R.id.searchSlidingStrip)
    PagerSlidingTabStrip searchCategoriesSlidingTabStrip;
    @ViewById(R.id.search_field_edit_text)
    public EditText searchEditText;

    private final String TAG = "Search activity";
    private final int PAGE_COUNT = 5;

    LinearLayout searchActionBar;
    private List<Fragment> fragments = new ArrayList<Fragment>();
    private Set<Fragment> disposableFragment = new HashSet<Fragment>();
    private FragmentPagerAdapter fragmentPagerAdapter;

    public static final String USER_IDS = "user_ids";
    public static final String COUNT = "count";
    public static final String SEARCH_QUERY = "search_query";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mServiceClass = MainService_.class;

        LayoutInflater inflater = getLayoutInflater();
        searchActionBar = (LinearLayout) inflater.inflate(R.layout.search_action_bar, null);

        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);

        ActionBar actionBar = getActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(searchActionBar, layoutParams);
        actionBar.setBackgroundDrawable(new ColorDrawable(R.color.grey_bg_search));
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
        fragments.add(new GroupSearchFragment_());
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
                Message msg = Message.obtain();
                msg.what = AppProtocol.SEARCH_BY_USERS;
                Bundle data = new Bundle();
                data.putString(SEARCH_QUERY, s.toString());
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
                Log.v(TAG, "search_by_users_response=" + msg.getData());
                break;
        }
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

}