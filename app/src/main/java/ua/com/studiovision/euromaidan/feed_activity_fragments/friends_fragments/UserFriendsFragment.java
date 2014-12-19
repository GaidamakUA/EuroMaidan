package ua.com.studiovision.euromaidan.feed_activity_fragments.friends_fragments;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import ua.com.studiovision.euromaidan.R;
import ua.com.studiovision.euromaidan.SharedPrefs_;
import ua.com.studiovision.euromaidan.feed_activity_fragments.friends_fragments.adapters.UserFriendsAdapter;
import ua.com.studiovision.euromaidan.network.json_protocol.friends.FriendsContent;
import ua.com.studiovision.euromaidan.network.provider.friends.FriendsColumns;
import ua.com.studiovision.euromaidan.network.provider.friends.FriendsCursor;

@EFragment(R.layout.fragment_user_friends)
public class UserFriendsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    @ViewById(R.id.user_friends_recycler_view)
    RecyclerView userFriendsRecyclerView;

    @Pref
    SharedPrefs_ mSharedPrefs;

    private static final String TAG = "UserFriendsFragment";
    UserFriendsAdapter userFriendsAdapter;
    FriendsFragmentCallbacks callbacks;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callbacks = (FriendsFragmentCallbacks) UserFriendsFragment.this.getActivity();
    }


    @Override
    public void onDetach() {
        super.onDetach();
        Log.v(TAG,"Loading users...");
        Log.v(TAG, "onDetach(" + ")");
        getActivity().getLoaderManager().destroyLoader(0);
    }

    @Override
    public void onResume() {
        super.onResume();
        callbacks.loadFriends(mSharedPrefs.getUserId().get(), FriendsContent.FRIENDS);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().getLoaderManager().initLoader(0, null, this);
    }

    @AfterViews
    void init() {
        userFriendsAdapter = new UserFriendsAdapter(null, getActivity().getBaseContext(), callbacks,mSharedPrefs.getUserId().get());
        userFriendsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        userFriendsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        userFriendsRecyclerView.setAdapter(userFriendsAdapter);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Log.v(TAG, "onCreateLoader(" + "i=" + i + ", bundle=" + bundle + ")");
        return new CursorLoader(
                getActivity().getBaseContext(),
                FriendsColumns.CONTENT_URI,
                FriendsColumns.ALL_COLUMNS,
                null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Log.v(TAG, "onLoadFinished(" + "loader=" + loader + ", cursor=" + cursor.getCount() + ")");
        userFriendsAdapter.changeCursor(new FriendsCursor(cursor));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        userFriendsAdapter.changeCursor(null);
    }
}