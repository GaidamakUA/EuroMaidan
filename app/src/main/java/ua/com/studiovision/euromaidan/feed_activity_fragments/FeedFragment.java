package ua.com.studiovision.euromaidan.feed_activity_fragments;

import android.app.Fragment;
import org.androidannotations.annotations.EFragment;
import ua.com.studiovision.euromaidan.R;

@EFragment(R.layout.fragment_feed)
public class FeedFragment extends Fragment {
    @Override
    public void onStart() {
        super.onStart();
        getActivity().getActionBar().hide();
    }
}
