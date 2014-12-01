package ua.com.studiovision.euromaidan.feed_activity_fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
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
    @ViewById(R.id.viewpager)
    ViewPager viewPager;

    private final static String TAG = "Settings fragment";
    public final static String PAGE_NAME = "page_name";
    private final static int PAGE_COUNT = 4;

    private PagerAdapter mPagerAdapter;
    private FragmentPagerAdapter fragmentPagerAdapter;

    @AfterViews
    void initPager() {
//        mPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());
//        mPager.setAdapter(mPagerAdapter);
        fragmentPagerAdapter = new SettingsFragmentPagerAdapter(getFragmentManager());
        viewPager.setAdapter(fragmentPagerAdapter);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                Log.v(TAG, "onPageSelected, position = " + i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private static class ScreenSlidePagerAdapter extends FragmentPagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int i) {
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

    private static class SettingsFragmentPagerAdapter extends FragmentPagerAdapter{

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
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getItem(position).getArguments().getString(PAGE_NAME);
        }
    }
}
