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
import ua.com.studiovision.euromaidan.audio_player.AudioActivity_;
import ua.com.studiovision.euromaidan.network.json_protocol.search.MyAudio;
import ua.com.studiovision.euromaidan.network.provider.audios.AudiosColumns;
import ua.com.studiovision.euromaidan.network.provider.audios.AudiosCursor;
import ua.com.studiovision.euromaidan.network.provider.audios.AudiosSelection;
import ua.com.studiovision.euromaidan.search_fragments.adapters.AudioSearchAdapter;
import ua.com.studiovision.euromaidan.search_fragments.adapters.SearchByAudiosOnScrollListener;

@EFragment(R.layout.fragment_audio_search)
public class AudioSearchFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    @ViewById(R.id.searchRecyclerView)
    RecyclerView searchRecyclerView;
    AudioSearchAdapter audioSearchAdapter;
    AudiosSelection filter;

    AudiosCursor mCursor;

    private final String TAG = "AudioSearchFragment";

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
        audioSearchAdapter = new AudioSearchAdapter(null,getActivity().getBaseContext(), new OnAudioClickListener() {
            @Override
            public void onAudioClicked(int initPosition) {
                Log.v(TAG, "onAudioClicked(" + "itemId=" + initPosition + ")");
                mCursor.moveToPosition(-1);
                long[] audioIds = new long[mCursor.getCount()];
                MyAudio[] audios = new MyAudio[mCursor.getCount()];
                int counter = 0;
                while (mCursor.moveToNext()) {
                    MyAudio audio = new MyAudio();
                    audio.author = mCursor.getAuthor();
                    audio.name = mCursor.getName();
                    audio.duration = mCursor.getDuration();
                    audio.url = mCursor.getAudioUrl();
                    audios[counter++] = audio;
                }
                AudioActivity_.intent(getActivity()).audiosTemp(audios).initialPosition(initPosition).start();
            }
        });
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        searchRecyclerView.setItemAnimator(new DefaultItemAnimator());
        searchRecyclerView.setAdapter(audioSearchAdapter);
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
                AudiosSelection selection = new AudiosSelection();
                if (query.length() > 0) {
                    filter = selection.nameLowercaseLike("%" + editable.toString().toLowerCase() + "%")
                            .or().authorLowercaseLike("%" + editable.toString().toLowerCase() + "%");
                } else {
                    // XXX looking for -1 just to find nothing and clear list
                    filter = selection.id(-1l);
                }
                getLoaderManager().restartLoader(0, null, AudioSearchFragment.this);
            }
        });
        searchRecyclerView.setOnScrollListener(new SearchByAudiosOnScrollListener((SearchActivityCallbacks)AudioSearchFragment.this.getActivity()));
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Log.v(TAG, "onCreateLoader(" + "i=" + i + ", bundle=" + bundle + ")");
        if (filter == null)
            return null;
        CursorLoader cursorLoader = new CursorLoader(
                getActivity().getBaseContext(),
                AudiosColumns.CONTENT_URI,
                AudiosColumns.ALL_COLUMNS,
                filter.sel(), filter.args(), null);
        Log.v(TAG, "FILTER = " + filter);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Log.v(TAG, "onLoadFinished(" + "loader=" + loader + ", cursor=" + cursor + ")");
        audioSearchAdapter.changeCursor(new AudiosCursor(cursor));
        this.mCursor = new AudiosCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        audioSearchAdapter.changeCursor(null);
    }

    public interface OnAudioClickListener {
        void onAudioClicked(int itemId);
    }
}
