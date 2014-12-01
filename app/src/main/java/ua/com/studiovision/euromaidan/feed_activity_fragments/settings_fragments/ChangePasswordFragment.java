package ua.com.studiovision.euromaidan.feed_activity_fragments.settings_fragments;

import android.app.Fragment;

import android.os.Bundle;
import org.androidannotations.annotations.EFragment;

import ua.com.studiovision.euromaidan.R;
import ua.com.studiovision.euromaidan.feed_activity_fragments.SettingsFragment;

@EFragment(R.layout.fragment_change_password)
public class ChangePasswordFragment extends Fragment {

    public static ChangePasswordFragment_ newInstance() {
        ChangePasswordFragment_ pageFragment = new ChangePasswordFragment_();
        Bundle arguments = new Bundle();
        arguments.putString(SettingsFragment.PAGE_NAME, "Password");
        pageFragment.setArguments(arguments);
        return pageFragment;
    }
}
