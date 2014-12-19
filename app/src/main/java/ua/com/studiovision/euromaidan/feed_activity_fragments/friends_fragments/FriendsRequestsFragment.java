package ua.com.studiovision.euromaidan.feed_activity_fragments.friends_fragments;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import ua.com.studiovision.euromaidan.R;
import ua.com.studiovision.euromaidan.SharedPrefs_;
import ua.com.studiovision.euromaidan.feed_activity_fragments.friends_fragments.adapters.FriendsRequestsAdapter;
import ua.com.studiovision.euromaidan.network.json_protocol.friends.FriendsContent;
import ua.com.studiovision.euromaidan.network.provider.applicant.ApplicantColumns;
import ua.com.studiovision.euromaidan.network.provider.applicant.ApplicantCursor;

@EFragment(R.layout.fragment_friends_requests)
public class FriendsRequestsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    @ViewById(R.id.friends_requests_recycler_view)
    RecyclerView friendsRequestsRecyclerView;

    @Pref
    SharedPrefs_ mSharedPrefs;

    private static final String TAG = "FriendsRequestsFragment";
    FriendsRequestsAdapter friendsRequestsAdapter;
    FriendsFragmentCallbacks callbacks;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        getActivity().getLoaderManager().initLoader(2, null, this);
        callbacks = (FriendsFragmentCallbacks) FriendsRequestsFragment.this.getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.v(TAG, "onDetach(" + ")");
        getActivity().getLoaderManager().destroyLoader(2);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v(TAG,"Loading requests...");
        callbacks.loadFriends(mSharedPrefs.getUserId().get(), FriendsContent.FRIENDS_REQUESTS);
    }

    @AfterViews
    void init() {
        friendsRequestsAdapter = new FriendsRequestsAdapter(null, getActivity().getBaseContext(), callbacks, mSharedPrefs.getUserId().get());
        friendsRequestsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        friendsRequestsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        friendsRequestsRecyclerView.setAdapter(friendsRequestsAdapter);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Log.v(TAG, "onCreateLoader(" + "i=" + i + ", bundle=" + bundle + ")");
        return new CursorLoader(
                getActivity().getBaseContext(),
                ApplicantColumns.CONTENT_URI,
                ApplicantColumns.ALL_COLUMNS,
                null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Log.v(TAG, "onLoadFinished(" + "loader=" + loader + ", cursor=" + cursor.getCount() + ")");
        friendsRequestsAdapter.changeCursor(new ApplicantCursor(cursor));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        friendsRequestsAdapter.changeCursor(null);
    }
}
