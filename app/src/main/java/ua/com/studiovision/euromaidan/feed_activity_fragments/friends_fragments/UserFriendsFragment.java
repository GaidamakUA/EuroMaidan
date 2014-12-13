package ua.com.studiovision.euromaidan.feed_activity_fragments.friends_fragments;

import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import ua.com.studiovision.euromaidan.R;

@EFragment(R.layout.fragment_user_friends)
public class UserFriendsFragment extends Fragment{
    @ViewById(R.id.user_friends_recycler_view)
    RecyclerView userFriendsRecyclerView;

    @AfterViews
    void init(){
        userFriendsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        userFriendsRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}
