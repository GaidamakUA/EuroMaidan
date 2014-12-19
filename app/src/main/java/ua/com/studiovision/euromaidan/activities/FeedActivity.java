package ua.com.studiovision.euromaidan.activities;

import android.app.ActionBar;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.softevol.activity_service_communication.ActivityServiceCommunicationFragmentActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.ArrayList;
import java.util.HashMap;

import ua.com.studiovision.euromaidan.AppProtocol;
import ua.com.studiovision.euromaidan.ImageProcessor;
import ua.com.studiovision.euromaidan.NavDrawerItem;
import ua.com.studiovision.euromaidan.NavDrawerListAdapter;
import ua.com.studiovision.euromaidan.R;
import ua.com.studiovision.euromaidan.SharedPrefs_;
import ua.com.studiovision.euromaidan.feed_activity_fragments.FeedFragment_;
import ua.com.studiovision.euromaidan.feed_activity_fragments.FriendsFragment_;
import ua.com.studiovision.euromaidan.feed_activity_fragments.SettingsFragment_;
import ua.com.studiovision.euromaidan.feed_activity_fragments.friends_fragments.FriendsFragmentCallbacks;
import ua.com.studiovision.euromaidan.feed_activity_fragments.settings_fragments.SettingsFragmentListener;
import ua.com.studiovision.euromaidan.network.MainService_;
import ua.com.studiovision.euromaidan.network.json_protocol.friends.FriendsContent;
import ua.com.studiovision.euromaidan.network.json_protocol.friends.GetFriendsProtocol;
import ua.com.studiovision.euromaidan.network.json_protocol.settings.GetSettingProtocol;
import ua.com.studiovision.euromaidan.network.json_protocol.settings.SetSettingProtocol;
import ua.com.studiovision.euromaidan.network.json_protocol.settings.SettingsParams;

@EActivity(R.layout.activity_feed)
public class FeedActivity extends ActivityServiceCommunicationFragmentActivity
        implements SettingsFragmentListener, FriendsFragmentCallbacks {
    private static final String TAG = "FeedActivity";

    @ViewById(R.id.avatar)
    ImageView avatar;
    @ViewById(R.id.drawer_layout)
    DrawerLayout drawer;
    @ViewById(R.id.list_slidermenu)
    ListView drawerList;
    @ViewById(R.id.background)
    ImageView background;
    @ViewById(R.id.search)
    EditText search;

    @Pref
    SharedPrefs_ preferences;

    HashMap<Integer, Fragment> fragments = new HashMap<Integer, Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mServiceClass = MainService_.class;

        fragments.put(0, new FeedFragment_());
        fragments.put(2, new FriendsFragment_());
        fragments.put(7, new SettingsFragment_());

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_holder, fragments.get(0)).commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        ActionBar actionBar = getActionBar();
        assert actionBar != null;
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_USE_LOGO);
        actionBar.setLogo(R.drawable.menu_icon);
        actionBar.setHomeButtonEnabled(true);
    }

    private void replace(Fragment fragment) {
        if (!fragment.isAdded()) {
            Log.v(TAG, "Try to add fragment " + fragment.toString() + "with state: " + fragment.isAdded());
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_holder, fragment);
            Log.v(TAG, "Backstack count = " + getSupportFragmentManager().getBackStackEntryCount());
            if (getSupportFragmentManager().getBackStackEntryCount() == 0 && !(fragment instanceof FeedFragment_)) {
                transaction.addToBackStack(fragment.getClass().getName());
                Log.v(TAG, "Added to backstack");
            }
            transaction.commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home && drawer.isDrawerOpen(Gravity.START)) {
            drawer.closeDrawer(Gravity.START);
        } else {
            drawer.openDrawer(Gravity.START);
        }
        return true;
    }

    @Override
    public void sendProfileDataToServer(SettingsParams settingsParams) {
        Bundle bundle = new Bundle();
        bundle.putString(SetSettingProtocol.TOKEN, preferences.getToken().get());
        bundle.putParcelable(SetSettingProtocol.SETTINGS_PARAMS, settingsParams);
        Message message = Message.obtain();
        message.setData(bundle);
        message.what = AppProtocol.SEND_PROFILE;
        sendMessage(message);
    }

    @Override
    public void requestProfileDataFromServer() {
        Log.v(TAG, "FeedActivity.requestProfileDataFromServer(" + ")");
        Bundle bundle = new Bundle();
        bundle.putString(GetSettingProtocol.TOKEN, preferences.getToken().get());
        Message message = Message.obtain();
        message.setData(bundle);
        message.what = AppProtocol.REQUEST_USER_SETTINGS;
        sendMessage(message);
    }

    @Override
    public void pullProfileData() {

    }

    @Override
    protected void handleMessage(Message msg) {
        switch (msg.what) {
            case AppProtocol.ON_SERVICE_CONNECTED:
                Log.v(TAG, "Service connected");
                break;
            case AppProtocol.RESPONSE_USER_SETTINGS:
                Log.v(TAG, "Response:" + msg.getData());
                break;
        }
    }

    @SuppressWarnings("deprecation")
    @AfterViews
    void init() {
        avatar.setImageBitmap(ImageProcessor.getRoundedCornersImage(BitmapFactory.decodeResource(getResources(), R.drawable.fail_avatar)));
        Bitmap overlay = Bitmap.createBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.fail_background_user));
        final RenderScript rs = RenderScript.create(getApplicationContext());
        final Allocation input = Allocation.createFromBitmapResource(rs, getResources(), R.drawable.fail_background_user, Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
        final Allocation output = Allocation.createTyped(rs, input.getType());
        final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        script.setRadius(5.f);
        script.setInput(input);
        script.forEach(output);
        output.copyTo(overlay);
        rs.finish();

        ShapeDrawable blackCover = new ShapeDrawable();
        blackCover.getPaint().setColor(getResources().getColor(R.color.black_50_opacity));

        Drawable[] layers = {new BitmapDrawable(getResources(), overlay), blackCover};
        LayerDrawable layerDrawable = new LayerDrawable(layers);

        background.setBackgroundDrawable(layerDrawable);

        String[] navMenuTitles = getResources().getStringArray(R.array.drawer_items);
        TypedArray navMenuIcons = getResources().obtainTypedArray(R.array.drawer_icons);

        ArrayList<NavDrawerItem> navDrawerItems = new ArrayList<>();

        for (int i = 0; i < navMenuTitles.length; i++) {
            //------only for preview---------
            if (i == 1) {
                navDrawerItems.add(new NavDrawerItem(navMenuTitles[i], navMenuIcons.getResourceId(i, -1), true, "13"));
                continue;
            }
            if (i == 2) {
                navDrawerItems.add(new NavDrawerItem(navMenuTitles[i], navMenuIcons.getResourceId(i, -1), true, "2"));
                continue;
            }
            //----------end preview----------
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[i], navMenuIcons.getResourceId(i, -1)));
        }

        // Recycle the typed array
        navMenuIcons.recycle();

        // setting the nav drawer list adapter
        NavDrawerListAdapter adapter = new NavDrawerListAdapter(getApplicationContext(), navDrawerItems);
        drawerList.setAdapter(adapter);
        drawerList.setOnItemClickListener(new SlideMenuClickListener());
    }

    @Click(R.id.search)
    void searchClick() {
        SearchActivity_.intent(FeedActivity.this).start();
        drawer.postDelayed(new Runnable() {
            @Override
            public void run() {
                drawer.closeDrawer(Gravity.START);
            }
        }, 250);
    }

    @Override
    public void loadFriends(long userId, FriendsContent content) {
        Log.v(TAG, "FeedActivity.loadFriends(" + ")");
        Bundle bundle = new Bundle();
        bundle.putLong(GetFriendsProtocol.ID_USER, userId);
        bundle.putSerializable(GetFriendsProtocol.CONTENT, content);
        Message message = Message.obtain();
        message.setData(bundle);
        message.what = AppProtocol.GET_FRIENDS;
        sendMessage(message);
    }

    @Override
    public void deleteFriend(long userId) {
        Log.v(TAG, "deleteFriend(" + "userId=" + userId + ")");
        Bundle data = new Bundle();
        data.putLong(SearchActivity.FRIEND_ID, userId);
        data.putString(FirstRunActivity.TOKEN, preferences.getToken().get());
        Message msg = Message.obtain();
        msg.what = AppProtocol.DELETE_FRIEND;
        msg.setData(data);
        sendMessage(msg);
    }

    @Override
    public void addFriend(long userId) {
        Log.v(TAG, "addFriend(" + "userId=" + userId + ")");
        Bundle data = new Bundle();
        data.putLong(SearchActivity.FRIEND_ID, userId);
        data.putString(FirstRunActivity.TOKEN, preferences.getToken().get());
        Message msg = Message.obtain();
        msg.what = AppProtocol.ADD_FRIEND;
        msg.setData(data);
        sendMessage(msg);
    }

    private class SlideMenuClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            drawer.closeDrawer(Gravity.START);
            if (fragments.containsKey(position))
                if (getSupportFragmentManager().getBackStackEntryCount()==1 && position==0)
                    getSupportFragmentManager().popBackStack();
                else
                    replace(fragments.get(position));
        }
    }
}
