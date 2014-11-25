package ua.com.studiovision.euromaidan.firstrunfragments;

import android.app.Activity;
import android.app.Fragment;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

import ua.com.studiovision.euromaidan.R;

@EFragment (R.layout.fragment_school)
public class SchoolFragment extends Fragment {
    FirstRunFragmentListener firstRunFragmentListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        firstRunFragmentListener = (FirstRunFragmentListener) activity;
    }

    @Click(R.id.skip_button)
    void skip() {
        firstRunFragmentListener.changeFragment(this);
    }
}
