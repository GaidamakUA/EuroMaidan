package ua.com.studiovision.euromaidan.search_fragments.adapters;

import android.support.v7.widget.RecyclerView;

import ua.com.studiovision.euromaidan.network.json_protocol.search.SearchCategory;
import ua.com.studiovision.euromaidan.search_fragments.SearchActivityCallbacks;

public class SearchOnScrollListener extends RecyclerView.OnScrollListener {

    private static final String TAG = "SearchOnScrollListener";

    private SearchActivityCallbacks callbacks;
    private SearchCategory category;

    public SearchOnScrollListener(SearchActivityCallbacks callbacks, SearchCategory category) {
        this.callbacks = callbacks;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        switch (newState) {
            case RecyclerView.SCROLL_STATE_SETTLING:
                callbacks.loadMoreUserIds();
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
