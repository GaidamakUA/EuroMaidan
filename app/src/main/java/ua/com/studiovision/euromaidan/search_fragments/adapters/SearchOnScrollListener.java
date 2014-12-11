package ua.com.studiovision.euromaidan.search_fragments.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import ua.com.studiovision.euromaidan.search_fragments.SearchActivityCallbacks;

public class SearchOnScrollListener extends RecyclerView.OnScrollListener {

    private static final String TAG = "SearchOnScrollListener";

    private SearchActivityCallbacks callbacks;
    private SearchCategories category;

    public SearchOnScrollListener(SearchActivityCallbacks callbacks, SearchCategories category) {
        this.callbacks = callbacks;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        switch (newState) {
            case RecyclerView.SCROLL_STATE_SETTLING:
                Log.v(TAG, "SCROLL_STATE_SETTLING");
                callbacks.loadMoreUserIds();
                break;
            case RecyclerView.SCROLL_STATE_IDLE:
                Log.v(TAG, "SCROLL_STATE_IDLE");
                break;
            case RecyclerView.SCROLL_STATE_DRAGGING:
                Log.v(TAG, "SCROLL_STATE_DRAGGING");
                break;
            default:
                Log.v(TAG, "SCROLL_STATE_LOL");
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        Log.v(TAG, "onScrolled(" + "recyclerView=" + recyclerView + ", dx=" + dx + ", dy=" + dy + ")");
    }
}
