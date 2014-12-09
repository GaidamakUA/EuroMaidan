package ua.com.studiovision.euromaidan.search_fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import ua.com.studiovision.euromaidan.R;
import ua.com.studiovision.euromaidan.network.provider.users.UsersColumns;
import ua.com.studiovision.euromaidan.network.provider.users.UsersCursor;
import ua.com.studiovision.euromaidan.search_fragments.adapters.UserSearchAdapter;

@EFragment(R.layout.fragment_user_search)
public class UserSearchFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    @ViewById(R.id.searchRecyclerView)
    RecyclerView searchRecyclerView;
    UserSearchAdapter userSearchAdapter;
    EditText searchEditText;

    private final String TAG = "User search fragment";

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        getActivity().getLoaderManager().initLoader(0, null, this);
    }

    @AfterViews
    void init() {
        LinearLayout searchActionBar = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.search_action_bar, null);
        searchEditText = (EditText) searchActionBar.findViewById(R.id.search_field_edit_text);
        userSearchAdapter = new UserSearchAdapter(null);
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        searchRecyclerView.setItemAnimator(new DefaultItemAnimator());
        searchRecyclerView.setAdapter(userSearchAdapter);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        CursorLoader cursorLoader = new CursorLoader(
                getActivity().getBaseContext(),
                UsersColumns.CONTENT_URI,
                UsersColumns.ALL_COLUMNS,
                null, null, null);
        cursorLoader.setUpdateThrottle(2000);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        userSearchAdapter.changeCursor(new UsersCursor(cursor));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        userSearchAdapter.changeCursor(null);
    }
}
