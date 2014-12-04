package ua.com.studiovision.euromaidan;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.softevol.activity_service_communication.ActivityServiceCommunicationFragmentActivity;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.HashMap;

import ua.com.studiovision.euromaidan.feed_activity_fragments.FeedFragment_;
import ua.com.studiovision.euromaidan.feed_activity_fragments.SettingsFragment_;
import ua.com.studiovision.euromaidan.feed_activity_fragments.settings_fragments.SettingsFragmentListener;
import ua.com.studiovision.euromaidan.json_protocol.settings.SetSettingProtocol;
import ua.com.studiovision.euromaidan.json_protocol.settings.SettingsParams;

@EActivity(R.layout.activity_feed)
public class FeedActivity extends ActivityServiceCommunicationFragmentActivity
        implements SettingsFragmentListener {
    private static final String TAG = "FeedActivity";

    @Pref
    SharedPrefs_ preferences;

    HashMap<Integer, Fragment> fragments = new HashMap<Integer, Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fragments.put(R.id.news_textview, new FeedFragment_());
        fragments.put(R.id.settings_textview, new SettingsFragment_());

        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            FeedFragment_ feedFragment = new FeedFragment_();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_holder, feedFragment).commit();
        }

        mServiceClass = MainService_.class;
    }

    @Click({R.id.news_textview, R.id.settings_textview})
    void onSettingsClick(View view) {
        Log.v(TAG, "FeedActivity.onSettingsClick(" + ")");
        replace(fragments.get(view.getId()));
    }

    private void replace(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, fragment).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.openDrawer(Gravity.START);
        }
        return true;
    }

    @Override
    public void sendProfileDataToServer(SettingsParams settingsParams) {
        Log.v(TAG, "FeedActivity.sendProfileDataToServer(" + "settingsParams=" + settingsParams + ")");
        Bundle bundle = new Bundle();
        bundle.putString(SetSettingProtocol.TOKEN, preferences.getToken().get());
        bundle.putParcelable(SetSettingProtocol.SETTINGS_PARAMS, settingsParams);
        Message message = Message.obtain();
        message.setData(bundle);
        message.what = AppProtocol.SEND_PROFILE;
        sendMessage(message);
    }

    @Override
    protected void handleMessage(Message msg) {
        switch (msg.what) {
            case AppProtocol.ON_SERVICE_CONNECTED:
                Log.v(TAG, "Service connected");
                break;
        }
    }
}
