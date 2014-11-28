package ua.com.studiovision.euromaidan.first_run_fragments;

import android.app.Activity;
import android.app.Fragment;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

import ua.com.studiovision.euromaidan.R;

@EFragment (R.layout.fragment_university)
public class UniversityFragment extends Fragment {
    FirstRunFragmentListener firstRunFragmentListener;

    @Click(R.id.skip_button)
    void skip() {
        firstRunFragmentListener.changeFragment(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        firstRunFragmentListener = (FirstRunFragmentListener) activity;
    }
}
