package ua.com.studiovision.euromaidan.feed_activity_fragments.settings_fragments;

import android.app.Fragment;
import org.androidannotations.annotations.EFragment;
import ua.com.studiovision.euromaidan.R;

@EFragment(R.layout.fragment_change_password)
public class ChangePasswordFragment extends Fragment {

    public static ChangePasswordFragment_ newInstance() {
        return new ChangePasswordFragment_();
    }
}
