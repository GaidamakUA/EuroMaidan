package ua.com.studiovision.euromaidan;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import java.util.HashMap;

import ua.com.studiovision.euromaidan.feed_activity_fragments.FeedFragment_;
import ua.com.studiovision.euromaidan.feed_activity_fragments.SettingsFragment;
import ua.com.studiovision.euromaidan.feed_activity_fragments.SettingsFragment_;

@EActivity (R.layout.activity_feed)
public class FeedActivity extends Activity {
    private static final String TAG = "FeedActivity";

    HashMap<Integer, Fragment> fragments = new HashMap<Integer, Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fragments.put(R.id.news_textview, new FeedFragment_());
        fragments.put(R.id.settings_textview, new SettingsFragment_());

        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            FeedFragment_ feedFragment = new FeedFragment_();
            getFragmentManager().beginTransaction().add(R.id.fragment_holder, feedFragment).commit();
        }
    }

    @Click({R.id.news_textview, R.id.settings_textview})
    void onSettingsClick(View view) {
        Log.v(TAG, "FeedActivity.onSettingsClick(" + ")");
        replace(fragments.get(view.getId()));
    }

    private void replace(Fragment fragment) {
        if (fragment instanceof SettingsFragment)
            fragment = new SettingsFragment_();
        getFragmentManager().beginTransaction().replace(R.id.fragment_holder, fragment).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.openDrawer(Gravity.START);
        }
        return true;
    }
}
