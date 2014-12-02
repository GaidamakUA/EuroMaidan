package ua.com.studiovision.euromaidan.feed_activity_fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.astuetz.PagerSlidingTabStrip;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import ua.com.studiovision.euromaidan.R;

@EFragment(R.layout.fragment_settings)
public class SettingsFragment extends Fragment {
    @ViewById(R.id.view_pager)
    ViewPager viewPager;
    @ViewById(R.id.pager_tabs)
    PagerSlidingTabStrip pagerSlidingTabStrip;

    private final static String TAG = "Settings fragment";
    private final static int PAGE_COUNT = 4;

    private FragmentPagerAdapter fragmentPagerAdapter;

    @AfterViews
    void initPager() {
        fragmentPagerAdapter = new SettingsFragmentPagerAdapter(getFragmentManager());
        viewPager.setAdapter(fragmentPagerAdapter);
        pagerSlidingTabStrip.setShouldExpand(true);
        pagerSlidingTabStrip.setUnderlineHeight((int) getResources().getDimension(R.dimen.invisibleUnderline));
        pagerSlidingTabStrip.setIndicatorColorResource(R.color.light_blue);
        pagerSlidingTabStrip.setIndicatorHeight((int) getResources().getDimension(R.dimen.slidingIndicatorHeight));
        pagerSlidingTabStrip.setDividerColorResource(android.R.color.transparent);
        pagerSlidingTabStrip.setViewPager(viewPager);
    }

    private static class SettingsFragmentPagerAdapter extends FragmentPagerAdapter implements PagerSlidingTabStrip.IconTabProvider{

        protected static final int[] ICONS = new int[] {
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
            return null;
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
