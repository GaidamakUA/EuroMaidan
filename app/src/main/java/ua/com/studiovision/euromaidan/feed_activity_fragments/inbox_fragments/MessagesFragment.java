package ua.com.studiovision.euromaidan.feed_activity_fragments.inbox_fragments;

import android.app.ActionBar;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ToggleButton;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import ua.com.studiovision.euromaidan.R;

@EFragment(R.layout.fragment_messages)
public class MessagesFragment extends Fragment {
    @ViewById(R.id.messages_recycler_view)
    RecyclerView messagesRecyclerView;

    private static final String TAG = "MessagesFragment";

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.v(TAG, "OnAttach(" + ")");
    }

    @Override
    public void onResume() {
        super.onResume();
        ActionBar actionBar = getActivity().getActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowCustomEnabled(true);
//        actionBar.setCustomView(pagerSlidingTabStrip);
    }

    @AfterViews
    void init(){
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        messagesRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}
