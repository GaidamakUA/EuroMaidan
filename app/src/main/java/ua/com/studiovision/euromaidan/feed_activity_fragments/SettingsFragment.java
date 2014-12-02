package ua.com.studiovision.euromaidan.feed_activity_fragments;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import com.astuetz.PagerSlidingTabStrip;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import ua.com.studiovision.euromaidan.R;
import ua.com.studiovision.euromaidan.feed_activity_fragments.settings_fragments.ChangePasswordFragment_;
import ua.com.studiovision.euromaidan.feed_activity_fragments.settings_fragments.UserDetailsFragment_;
import ua.com.studiovision.euromaidan.feed_activity_fragments.settings_fragments.UserEducationPlacesFragment_;
import ua.com.studiovision.euromaidan.feed_activity_fragments.settings_fragments.UserPictureFragment_;

import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.fragment_settings)
public class SettingsFragment extends Fragment {
    @ViewById(R.id.view_pager)
    ViewPager viewPager;

    PagerSlidingTabStrip pagerSlidingTabStrip;

    private final static String TAG = "Settings fragment";
    private final static int PAGE_COUNT = 4;

    private static List<Fragment> fragments = new ArrayList<Fragment>();

    private FragmentStatePagerAdapter fragmentPagerAdapter;

    @Override
    public void onStart() {
        super.onStart();
        getActivity().getActionBar().show();
    }

    @Override
    public void onStop() {
        super.onStop();
        fragmentPagerAdapter.saveState();
    }

    @AfterViews
    void initPager() {
        fragments.add(new UserDetailsFragment_());
        fragments.add(new UserPictureFragment_());
        fragments.add(new UserEducationPlacesFragment_());
        fragments.add(new ChangePasswordFragment_());

        fragmentPagerAdapter = new SettingsFragmentPagerAdapter(getFragmentManager());
        viewPager.setAdapter(fragmentPagerAdapter);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        pagerSlidingTabStrip = (PagerSlidingTabStrip) inflater.inflate(R.layout.pager_tab_layout, null).findViewById(R.id.pager_tabs);
        pagerSlidingTabStrip.setShouldExpand(true);
        pagerSlidingTabStrip.setUnderlineHeight((int) getResources().getDimension(R.dimen.invisibleUnderline));
        pagerSlidingTabStrip.setIndicatorColorResource(R.color.light_blue);
        pagerSlidingTabStrip.setIndicatorHeight((int) getResources().getDimension(R.dimen.slidingIndicatorHeight));
        pagerSlidingTabStrip.setDividerColorResource(android.R.color.transparent);
        pagerSlidingTabStrip.setViewPager(viewPager);
        ActionBar actionBar = getActivity().getActionBar();

        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_USE_LOGO);
        actionBar.setCustomView(pagerSlidingTabStrip);
        actionBar.setLogo(R.drawable.menu_icon);
        actionBar.setHomeButtonEnabled(true);
    }

    private static class SettingsFragmentPagerAdapter extends FragmentStatePagerAdapter implements PagerSlidingTabStrip.IconTabProvider {

        protected static final int[] ICONS = new int[]{
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
            Log.v(TAG,"Getting item in position "+i);
            return fragments.get(i);
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
