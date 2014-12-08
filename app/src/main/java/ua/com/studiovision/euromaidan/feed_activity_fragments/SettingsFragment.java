package ua.com.studiovision.euromaidan.feed_activity_fragments;

import android.app.ActionBar;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
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
import ua.com.studiovision.euromaidan.feed_activity_fragments.settings_fragments.ChangePasswordFragment_;
import ua.com.studiovision.euromaidan.feed_activity_fragments.settings_fragments.SettingsFragmentListener;
import ua.com.studiovision.euromaidan.feed_activity_fragments.settings_fragments.UserDetailsFragment_;
import ua.com.studiovision.euromaidan.feed_activity_fragments.settings_fragments.UserEducationPlacesFragment_;
import ua.com.studiovision.euromaidan.feed_activity_fragments.settings_fragments.UserPictureFragment_;

@EFragment(R.layout.fragment_settings)
public class SettingsFragment extends Fragment {
    @ViewById(R.id.view_pager)
    ViewPager viewPager;

    PagerSlidingTabStrip pagerSlidingTabStrip;

    private final static String TAG = "Settings fragment";
    private final static int PAGE_COUNT = 4;

    private List<Fragment> fragments = new ArrayList<Fragment>();
    private Set<Fragment> disposableFragment = new HashSet<Fragment>();

    private FragmentPagerAdapter fragmentPagerAdapter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onStart() {
        super.onStart();
        // XXX may be dangerous
        ((SettingsFragmentListener) getActivity()).requestProfileDataFromServer();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.v(TAG, "SettingsFragment.onDetach(" + ")");
        for (Fragment fragment : disposableFragment) {
            Log.v(TAG, "fragment=" + fragment);
            fragmentPagerAdapter.destroyItem(null, 0, fragment);
        }
    }

    @AfterViews
    void initPager() {
        Log.v(TAG, "SettingsFragment.initPager(" + (fragmentPagerAdapter == null) + ")");
        Log.v(TAG, "this=" + this);
        fragments.add(new UserDetailsFragment_());
        fragments.add(new UserPictureFragment_());
        fragments.add(new UserEducationPlacesFragment_());
        fragments.add(new ChangePasswordFragment_());

        fragmentPagerAdapter = new SettingsFragmentPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(fragmentPagerAdapter);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        pagerSlidingTabStrip = (PagerSlidingTabStrip) inflater.inflate(R.layout.pager_tab_layout, null).findViewById(R.id.pager_tabs);
        pagerSlidingTabStrip.setShouldExpand(true);
        pagerSlidingTabStrip.setUnderlineHeight((int) getResources().getDimension(R.dimen.invisibleUnderline));
        pagerSlidingTabStrip.setIndicatorColorResource(android.R.color.white);
        pagerSlidingTabStrip.setIndicatorHeight((int) getResources().getDimension(R.dimen.slidingIndicatorHeight));
        pagerSlidingTabStrip.setDividerColorResource(android.R.color.transparent);
        pagerSlidingTabStrip.setViewPager(viewPager);

        ActionBar actionBar = getActivity().getActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(pagerSlidingTabStrip);
        

    }

    private class SettingsFragmentPagerAdapter extends FragmentPagerAdapter implements PagerSlidingTabStrip.IconTabProvider {

        protected final int[] ICONS = new int[]{
                R.drawable.settings_profile_icon,
                R.drawable.settings_picture_icon,
                R.drawable.settings_education_icon,
                R.drawable.settings_password_icon
        };

        public SettingsFragmentPagerAdapter(FragmentManager fm) {
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
        public int getPageIconResId(int i) {
            return ICONS[i];
        }
    }
}
