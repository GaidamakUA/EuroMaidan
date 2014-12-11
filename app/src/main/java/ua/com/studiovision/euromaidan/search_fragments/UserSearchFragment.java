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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import ua.com.studiovision.euromaidan.R;
import ua.com.studiovision.euromaidan.activities.SearchActivity;
import ua.com.studiovision.euromaidan.network.json_protocol.search.SearchCategory;
import ua.com.studiovision.euromaidan.network.provider.users.UsersColumns;
import ua.com.studiovision.euromaidan.network.provider.users.UsersCursor;
import ua.com.studiovision.euromaidan.network.provider.users.UsersSelection;
import ua.com.studiovision.euromaidan.search_fragments.adapters.SearchOnScrollListener;
import ua.com.studiovision.euromaidan.search_fragments.adapters.UserSearchAdapter;

@EFragment(R.layout.fragment_search)
public class UserSearchFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    @ViewById(R.id.searchRecyclerView)
    RecyclerView searchRecyclerView;
    UserSearchAdapter userSearchAdapter;
    UsersSelection filter;

    private final String TAG = "UserSearchFragment";

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        getActivity().getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.v(TAG, "onDetach(" + ")");
    }

    @AfterViews
    void init() {
        userSearchAdapter = new UserSearchAdapter(null,getActivity().getBaseContext());
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        searchRecyclerView.setItemAnimator(new DefaultItemAnimator());
        searchRecyclerView.setAdapter(userSearchAdapter);
        ((SearchActivity) getActivity()).searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String query = editable.toString();
                UsersSelection selection = new UsersSelection();
                if (query.length() > 0) {
                    filter = selection.userNameLowercaseLike("%" + editable.toString().toLowerCase() + "%");
                } else {
                    // XXX looking for -1 just to find nothing and clear list
                    filter = selection.id(-1l);
                }
                getLoaderManager().restartLoader(0, null, UserSearchFragment.this);
            }
        });
        searchRecyclerView.setOnScrollListener(new SearchOnScrollListener((SearchActivityCallbacks)UserSearchFragment.this.getActivity(), SearchCategory.PEOPLE));
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Log.v(TAG, "onCreateLoader(" + "i=" + i + ", bundle=" + bundle + ")");
        if (filter == null)
            return null;
        CursorLoader cursorLoader = new CursorLoader(
                getActivity().getBaseContext(),
                UsersColumns.CONTENT_URI,
                UsersColumns.ALL_COLUMNS,
                filter.sel(), filter.args(), null);
        Log.v(TAG, "FILTER = " + filter);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Log.v(TAG, "onLoadFinished(" + "loader=" + loader + ", cursor=" + cursor + ")");
        userSearchAdapter.changeCursor(new UsersCursor(cursor));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        userSearchAdapter.changeCursor(null);
    }
}
