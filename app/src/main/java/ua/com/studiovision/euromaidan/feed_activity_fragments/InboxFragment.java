package ua.com.studiovision.euromaidan.feed_activity_fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.HashMap;

import ua.com.studiovision.euromaidan.R;
import ua.com.studiovision.euromaidan.feed_activity_fragments.inbox_fragments.DialogsFragment_;
import ua.com.studiovision.euromaidan.feed_activity_fragments.inbox_fragments.MessagesFragment_;

@EFragment(R.layout.fragment_inbox)
public class InboxFragment extends Fragment {

    HashMap<Integer, Fragment> fragments = new HashMap<Integer, Fragment>();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        fragments.put(0,new DialogsFragment_());
        fragments.put(1,new MessagesFragment_());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.inbox_view_holder,fragments.get(0));
        transaction.addToBackStack(fragments.getClass().getName());
        transaction.commit();
    }
}
