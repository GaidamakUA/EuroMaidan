package ua.com.studiovision.euromaidan.feed_activity_fragments.settings_fragments;

import android.app.Fragment;

import android.os.Bundle;
import org.androidannotations.annotations.EFragment;

import ua.com.studiovision.euromaidan.R;
import ua.com.studiovision.euromaidan.feed_activity_fragments.SettingsFragment;

@EFragment(R.layout.fragment_user_picture)
public class UserPictureFragment extends Fragment {

    public static UserPictureFragment_ newInstance() {
        UserPictureFragment_ pageFragment = new UserPictureFragment_();
        Bundle arguments = new Bundle();
        arguments.putString(SettingsFragment.PAGE_NAME, "Picture");
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

}
