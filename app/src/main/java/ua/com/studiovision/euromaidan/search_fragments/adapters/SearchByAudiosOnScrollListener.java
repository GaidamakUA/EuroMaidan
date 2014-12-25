package ua.com.studiovision.euromaidan.search_fragments.adapters;

import android.support.v7.widget.RecyclerView;

import ua.com.studiovision.euromaidan.search_fragments.SearchActivityCallbacks;

public class SearchByAudiosOnScrollListener extends RecyclerView.OnScrollListener {

    private static final String TAG = "SearchOnScrollListener";

    private SearchActivityCallbacks callbacks;

    public SearchByAudiosOnScrollListener(SearchActivityCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        switch (newState) {
            case RecyclerView.SCROLL_STATE_SETTLING:
                callbacks.loadMoreAudio();
                break;
            case RecyclerView.SCROLL_STATE_IDLE:
                break;
            case RecyclerView.SCROLL_STATE_DRAGGING:
                break;
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
    }
}
