package ua.com.studiovision.euromaidan.feed_activity_fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import com.viewpagerindicator.IconPagerAdapter;
import com.viewpagerindicator.TabPageIndicator;
import com.viewpagerindicator.UnderlinePageIndicator;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import ua.com.studiovision.euromaidan.R;
import ua.com.studiovision.euromaidan.feed_activity_fragments.settings_fragments.ChangePasswordFragment;
import ua.com.studiovision.euromaidan.feed_activity_fragments.settings_fragments.UserDetailesFragment;
import ua.com.studiovision.euromaidan.feed_activity_fragments.settings_fragments.UserEducationPlacesFragment;
import ua.com.studiovision.euromaidan.feed_activity_fragments.settings_fragments.UserPictureFragment;

@EFragment(R.layout.fragment_settings)
public class SettingsFragment extends Fragment {
    @ViewById(R.id.view_pager)
    ViewPager viewPager;
    @ViewById(R.id.tab_indicator)
    TabPageIndicator tabPageIndicator;
    @ViewById(R.id.underline_indicator)
    UnderlinePageIndicator underlineIndicator;

    private final static String TAG = "Settings fragment";
    private final static int PAGE_COUNT = 4;

    private FragmentPagerAdapter fragmentPagerAdapter;

    @AfterViews
    void initPager() {
        fragmentPagerAdapter = new SettingsFragmentPagerAdapter(getFragmentManager());
        viewPager.setAdapter(fragmentPagerAdapter);
        tabPageIndicator.setViewPager(viewPager);
        underlineIndicator.setViewPager(viewPager);
    }

    private static class SettingsFragmentPagerAdapter extends FragmentPagerAdapter implements IconPagerAdapter{

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
            switch (i){
                case 0:
                    return UserDetailesFragment.newInstance();
                case 1:
                    return UserPictureFragment.newInstance();
                case 2:
                    return UserEducationPlacesFragment.newInstance();
                case 3:
                    return ChangePasswordFragment.newInstance();
            }
            return null;
        }

        @Override
        public int getIconResId(int i) {
            return ICONS[i];
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }
    }
}
