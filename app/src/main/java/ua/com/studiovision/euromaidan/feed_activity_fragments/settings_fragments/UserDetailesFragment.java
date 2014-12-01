package ua.com.studiovision.euromaidan.feed_activity_fragments.settings_fragments;

import android.app.Fragment;
import org.androidannotations.annotations.EFragment;
import ua.com.studiovision.euromaidan.R;

@EFragment(R.layout.fragment_user_detailes)
public class UserDetailesFragment extends Fragment {

    public static UserDetailesFragment_ newInstance() {
        return new UserDetailesFragment_();
    }
}
