package ua.com.studiovision.euromaidan.feed_activity_fragments.settings_fragments;

import android.support.v4.app.Fragment;
import android.util.Log;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

import ua.com.studiovision.euromaidan.R;
import ua.com.studiovision.euromaidan.json_protocol.settings.Genders;
import ua.com.studiovision.euromaidan.json_protocol.settings.RelationshipStatus;
import ua.com.studiovision.euromaidan.json_protocol.settings.SettingsParamsBuilder;

@EFragment(R.layout.fragment_user_detailes)
public class UserDetailsFragment extends Fragment {
    private static final String TAG = "UserDetailsFragment";

    @Click(R.id.save_button)
    void save() {
        Log.v(TAG, "UserDetailsFragment.save(" + ")");
        SettingsParamsBuilder builder = new SettingsParamsBuilder();
        builder.setName("Name")
                .setSurname("Surname")
                .setGender(Genders.UNKNOWN)
                .setRelationshipStatus(RelationshipStatus.COMPLICATED)
                .setNativeCity("Native city")
                .setDob("1970-01-01");
        ((SettingsFragmentListener) getActivity())
                .sendProfileDataToServer(builder.createRequestParams());
    }
}
