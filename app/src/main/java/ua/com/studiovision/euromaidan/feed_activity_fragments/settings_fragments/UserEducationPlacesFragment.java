package ua.com.studiovision.euromaidan.feed_activity_fragments.settings_fragments;

import android.app.Fragment;
import android.os.Bundle;
import org.androidannotations.annotations.EFragment;
import ua.com.studiovision.euromaidan.R;
import ua.com.studiovision.euromaidan.feed_activity_fragments.SettingsFragment;

@EFragment(R.layout.fragment_user_education_places)
public class UserEducationPlacesFragment extends Fragment {

    public static UserEducationPlacesFragment_ newInstance() {
        UserEducationPlacesFragment_ pageFragment = new UserEducationPlacesFragment_();
        Bundle arguments = new Bundle();
        arguments.putString(SettingsFragment.PAGE_NAME, "Education");
        pageFragment.setArguments(arguments);
        return pageFragment;
    }
}
