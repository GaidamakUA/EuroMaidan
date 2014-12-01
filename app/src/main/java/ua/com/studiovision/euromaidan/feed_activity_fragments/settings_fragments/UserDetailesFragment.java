package ua.com.studiovision.euromaidan.feed_activity_fragments.settings_fragments;

import android.app.Fragment;
import android.os.Bundle;
import org.androidannotations.annotations.EFragment;
import ua.com.studiovision.euromaidan.R;
import ua.com.studiovision.euromaidan.feed_activity_fragments.SettingsFragment;

@EFragment(R.layout.fragment_user_detailes)
public class UserDetailesFragment extends Fragment {

    public static UserDetailesFragment_ newInstance() {
        UserDetailesFragment_ pageFragment = new UserDetailesFragment_();
        Bundle arguments = new Bundle();
        arguments.putString(SettingsFragment.PAGE_NAME, "Details");
        pageFragment.setArguments(arguments);
        return pageFragment;
    }
}
