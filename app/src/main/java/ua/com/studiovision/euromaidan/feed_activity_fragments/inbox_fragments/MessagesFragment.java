package ua.com.studiovision.euromaidan.feed_activity_fragments.inbox_fragments;

import android.app.ActionBar;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;

import org.androidannotations.annotations.EFragment;

import ua.com.studiovision.euromaidan.R;

@EFragment(R.layout.fragment_messages)
public class MessagesFragment extends Fragment {

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
}
