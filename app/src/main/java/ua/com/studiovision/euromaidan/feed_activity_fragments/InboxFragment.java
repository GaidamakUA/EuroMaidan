package ua.com.studiovision.euromaidan.feed_activity_fragments;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import ua.com.studiovision.euromaidan.R;

@EFragment(R.layout.fragment_inbox)
public class InboxFragment extends Fragment {
    @ViewById(R.id.view_pager)
    ViewPager viewPager;

}
