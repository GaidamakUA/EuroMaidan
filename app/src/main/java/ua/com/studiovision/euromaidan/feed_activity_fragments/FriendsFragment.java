package ua.com.studiovision.euromaidan.feed_activity_fragments;

import android.app.ActionBar;
import android.app.Activity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;

import com.astuetz.PagerSlidingTabStrip;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ua.com.studiovision.euromaidan.R;
import ua.com.studiovision.euromaidan.feed_activity_fragments.friends_fragments.FollowersFragment;
import ua.com.studiovision.euromaidan.feed_activity_fragments.friends_fragments.FollowersFragment_;
import ua.com.studiovision.euromaidan.feed_activity_fragments.friends_fragments.FriendsRequestsFragment_;
import ua.com.studiovision.euromaidan.feed_activity_fragments.friends_fragments.UserFriendsFragment_;

@EFragment(R.layout.fragment_friends)
public class FriendsFragment extends Fragment {
    @ViewById(R.id.view_pager)
    ViewPager viewPager;

    PagerSlidingTabStrip pagerSlidingTabStrip;

    private static final String TAG = "FriendsFragment";
    private static final int PAGE_COUNT = 3;

    private List<Fragment> fragments = new ArrayList<>();
    private Set<Fragment> disposableFragment = new HashSet<>();

    private FragmentPagerAdapter fragmentPagerAdapter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.v(TAG, "FriendsFragment.onDetach(" + ")");
        for (Fragment fragment : disposableFragment) {
            Log.v(TAG, "fragment=" + fragment);
            fragmentPagerAdapter.destroyItem(null, 0, fragment);
        }
    }

    @AfterViews
    void initPager() {
        Log.v(TAG, "FriendsFragment.initPager(" + (fragmentPagerAdapter == null) + ")");
        Log.v(TAG, "this=" + this);
        fragments.add(new UserFriendsFragment_());
        fragments.add(new FollowersFragment_());
        fragments.add(new FriendsRequestsFragment_());

        fragmentPagerAdapter = new FriendsFragmentPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(fragmentPagerAdapter);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        pagerSlidingTabStrip = (PagerSlidingTabStrip) inflater.inflate(R.layout.pager_tab_layout, null).findViewById(R.id.pager_tabs);
        pagerSlidingTabStrip.setShouldExpand(true);
        pagerSlidingTabStrip.setUnderlineHeight((int) getResources().getDimension(R.dimen.invisibleUnderline));
        pagerSlidingTabStrip.setIndicatorColorResource(android.R.color.white);
        pagerSlidingTabStrip.setIndicatorHeight((int) getResources().getDimension(R.dimen.slidingIndicatorHeight));
        pagerSlidingTabStrip.setDividerColorResource(android.R.color.transparent);
        pagerSlidingTabStrip.setViewPager(viewPager);
        pagerSlidingTabStrip.setAllCaps(false);
        pagerSlidingTabStrip.setTextColor(getResources().getColor(android.R.color.white));

        ActionBar actionBar = getActivity().getActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(pagerSlidingTabStrip);


    }

    private class FriendsFragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {

        protected final String[] TITLES = new String[]{
                "Друзья",
                "Подписчики",
                "Заявки"
        };

        public FriendsFragmentPagerAdapter(FragmentManager fm) {
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
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }
    }

}
