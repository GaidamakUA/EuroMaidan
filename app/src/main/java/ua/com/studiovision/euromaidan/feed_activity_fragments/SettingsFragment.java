package ua.com.studiovision.euromaidan.feed_activity_fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import ua.com.studiovision.euromaidan.R;

@EFragment(R.layout.fragment_settings)
public class SettingsFragment extends Fragment {
    @ViewById(R.id.viewpager)
    ViewPager viewPager;

    private PagerAdapter mPagerAdapter;

    @AfterViews
    void initPager() {
        mPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());
//        mPager.setAdapter(mPagerAdapter);
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
}
